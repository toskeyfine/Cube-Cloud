package com.toskey.common.tenant.component;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.toskey.common.tenant.annotation.TenantScope;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

/**
 * TenantInterceptor
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:00
 */
public class TenantMybatisInterceptor extends JsqlParserSupport implements InnerInterceptor {

    private final static String FIELD_TENANT = "tenant_id";

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
        PlainSelect plainSelect = select.getPlainSelect();
        if (plainSelect != null) {
            this.setWhere(plainSelect, (String) obj);
        } else {
            SetOperationList setOperationList = select.getSetOperationList();
            if (setOperationList != null) {
                List<Select> selectBodyList = setOperationList.getSelects();
                selectBodyList.forEach(s -> this.setWhere((PlainSelect) s, (String) obj));
            }
        }
    }

    @SneakyThrows
    private void setWhere(PlainSelect plainSelect, String whereSegment) {
        Expression where = this.permissionHandle(plainSelect, whereSegment);
        if (where != null) {
            plainSelect.setWhere(where);
        }
    }

    private Expression permissionHandle(PlainSelect plainSelect, String whereSegment) throws ClassNotFoundException {
        Expression where = plainSelect.getWhere();
        if (where == null) {
            where = new HexValue(" 1 = 1 ");
        }
        Table fromTable = (Table) plainSelect.getFromItem();
        Alias fromAlias = fromTable.getAlias();
        String mainTableName = fromAlias == null ? fromTable.getName() : fromAlias.getName();

        String className = whereSegment.substring(0, whereSegment.lastIndexOf("."));
        String methodName = whereSegment.substring(whereSegment.lastIndexOf(".") + 1);
        Method[] methods = Class.forName(className).getMethods();
        for (Method method : methods) {
            if (StringUtils.equals(method.getName(), methodName)) {
                if (method.getAnnotation(TenantScope.class) == null) {
                    return where;
                }
                TenantContext tenantContext = TenantContextHolder.getContext();
                // 增加租户ID检查，避免SQL注入
//                RestResult<Boolean> checkedResult = remoteTenantService.checkTenantId(tenantContext.getId());
//                if (!checkedResult.getData()) {
//                     检查不过的租户ID将不返回任何数据
//                    return new HexValue(" 1 = 2 ");
//                }

                EqualsTo equals = new EqualsTo();
                equals.setLeftExpression(new Column(mainTableName + "." + FIELD_TENANT));
//                equals.setRightExpression(new StringValue(currentTenantId));
                return new AndExpression(where, equals);
            }
        }
        return new HexValue(" 1 = 2 ");
    }

}
