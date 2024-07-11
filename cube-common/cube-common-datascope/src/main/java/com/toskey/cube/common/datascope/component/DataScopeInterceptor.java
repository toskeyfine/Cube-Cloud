package com.toskey.cube.common.datascope.component;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.datascope.annotation.DataScope;
import com.toskey.cube.common.datascope.enums.DataScopeType;
import com.toskey.cube.common.security.principal.LoginUser;
import com.toskey.cube.common.security.util.SecurityUtils;
import com.toskey.cube.service.sas.interfaces.dto.RoleDTO;
import com.toskey.cube.service.sas.interfaces.service.RemoteRoleService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.*;

/**
 * DataScopeInterceptor
 *
 * @author toskey
 * @version 1.0.0
 */
@RequiredArgsConstructor
public class DataScopeInterceptor extends JsqlParserSupport implements InnerInterceptor {

    private static final String FIELD_DEPT = "dept_id";

    private static final String FIELD_USER = "create_by";

    private static final String EMPTY_WHERE_JOIN = " 1 = 1 ";

    private static final String ILLEGAL_WHERE_EXPRESSION = " 1 = 2 ";

    private final RemoteRoleService remoteRoleService;

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        return InnerInterceptor.super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
        mpBoundSql.sql(this.parserSingle(mpBoundSql.sql(), ms.getId()));
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        SelectBody selectBody = select.getSelectBody();
        if (selectBody instanceof PlainSelect plainSelect) {
            this.setWhere(plainSelect, (String) obj);
        } else if (selectBody instanceof SetOperationList setOperationList) {
            List<SelectBody> selectBodyList = setOperationList.getSelects();
            selectBodyList.forEach(s -> this.setWhere((PlainSelect) s, (String) obj));
        }
    }

    @SneakyThrows
    private void setWhere(PlainSelect select, String whereSegment) {
        Expression where = this.permissionProcess(select, whereSegment);
        Optional.of(where).ifPresent(select::setWhere);
    }

    private Expression permissionProcess(PlainSelect select, String whereSegment) throws ClassNotFoundException {
        Expression where = select.getWhere();
        if (where == null) {
            where = new HexValue(EMPTY_WHERE_JOIN);
        }
        String mainTableName = null;
        FromItem fromItem = select.getFromItem();
        if (fromItem instanceof Table table) {
            mainTableName = table.getName() != null ? table.getName() : table.getAlias().getName();
        } else {
            mainTableName = fromItem.getAlias().getName();
        }

        String className = whereSegment.substring(0, whereSegment.lastIndexOf("."));
        String methodName = whereSegment.substring(whereSegment.lastIndexOf(".") + 1);
        Method[] methods = Class.forName(className).getMethods();
        for (Method method : methods) {
            if (StringUtils.equals(methodName, method.getName())) {
                DataScope dataScope = method.getAnnotation(DataScope.class);
                if (dataScope == null) {
                    return where;
                }
                if (!dataScope.enabled()) {
                    return where;
                }
                LoginUser loginUser = SecurityUtils.getUser();
                RestResult<List<RoleDTO>> restResult = remoteRoleService.listByUserId(loginUser.getId());
                if (!restResult.isSuccess()) {

                }
                List<RoleDTO> roleList = restResult.getData();
                boolean scopeSelf = false;
                boolean scopeAll = false;
                Set<String> scopeDeptIds = new HashSet<>();
                if (null == roleList || roleList.isEmpty()) {
                    scopeSelf = true;
                } else {
                    for (RoleDTO role : roleList) {
                        DataScopeType dataScopeType = DataScopeType.of(role.getDataScopeType());
                        if (dataScopeType == DataScopeType.ALL) {
                            scopeAll = true;
                            break;
                        } else if (dataScopeType == DataScopeType.SELECTED_DEPT) {
                            scopeDeptIds.addAll(Arrays.asList(role.getDataScopeDeptIds()));
                        } else if (dataScopeType == DataScopeType.SELF) {
                            scopeSelf = true;
                        } else if (dataScopeType == DataScopeType.SELF_DEPT) {
                            scopeDeptIds.add(loginUser.getDept());
                        }
                    }
                }
                if (scopeAll) {
                    return where;
                }
                if (!scopeSelf && scopeDeptIds.isEmpty()) {
                    return new HexValue(ILLEGAL_WHERE_EXPRESSION);
                } else if (!scopeDeptIds.isEmpty()) {
                    InExpression in = new InExpression()
                            .withLeftExpression(new Column(mainTableName + "." + FIELD_DEPT))
                            .withRightExpression((Expression) scopeDeptIds.stream().map(StringValue::new).toList());
                    return new AndExpression(where, in);
                } else {
                    EqualsTo equals = new EqualsTo()
                            .withLeftExpression(new Column(mainTableName + "." + FIELD_USER))
                            .withRightExpression(new StringValue(loginUser.getId()));
                    return new AndExpression(where, equals);
                }
            }
        }
        return where;
    }
}
