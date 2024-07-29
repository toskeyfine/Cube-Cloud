package com.toskey.cube.common.tenant.component;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.toskey.cube.common.tenant.annotation.TenantScope;
import lombok.SneakyThrows;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;
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
 * Mybatis-plus的租户拦截器
 *
 * @author toskey
 * @version 1.0.0
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
        SelectBody selectBody = select.getSelectBody();
        if (selectBody instanceof PlainSelect plainSelect) {
            this.setWhere(plainSelect, (String) obj);
        } else if (selectBody instanceof SetOperationList setOperationList) {
            List<SelectBody> selectBodyList = setOperationList.getSelects();
            selectBodyList.forEach(s -> this.setWhere((PlainSelect) s, (String) obj));
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
        String mainTableName = null;
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table table) {
            mainTableName = table.getName() != null ? table.getName() : table.getAlias().getName();
        } else {
            mainTableName = fromItem.getAlias().getName();
        }

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
                if (tenantContext == null || StringUtils.isBlank(tenantContext.getId())) {
                    return new HexValue(" 1 = 2 ");
                }

                EqualsTo equals = new EqualsTo();
                equals.setLeftExpression(new Column(mainTableName + "." + FIELD_TENANT));
                equals.setRightExpression(new StringValue(tenantContext.getId()));
                return new AndExpression(where, equals);
            }
        }
        return new HexValue(" 1 = 2 ");
    }

}
