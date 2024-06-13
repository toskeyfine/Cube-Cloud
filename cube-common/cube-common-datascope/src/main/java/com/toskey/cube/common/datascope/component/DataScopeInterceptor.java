package com.toskey.cube.common.datascope.component;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * DataScopeInterceptor
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 10:58
 */
@RequiredArgsConstructor
public class DataScopeInterceptor extends JsqlParserSupport implements InnerInterceptor {

    private static final String FIELD_DEPT = "dept_id";

    private static final String FIELD_USER = "create_by";

    private static final String EMPTY_WHERE_JOIN = " 1 = 1 ";

    private static final String NOT_VALID_WHERE = " 1 = 2 ";

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
        if (select instanceof PlainSelect plainSelect) {
            this.setWhere(plainSelect, (String) obj);
        } else if (select instanceof SetOperationList setOperationList) {
            List<Select> selectBodyList = setOperationList.getSelects();
            selectBodyList.forEach(s -> this.setWhere((PlainSelect) s, (String) obj));
        }
    }

    private void setWhere(PlainSelect select, String whereSegment) {
        Expression where = this.permissionProcess(select, whereSegment);
        Optional.of(where).ifPresent(select::setWhere);
    }

    private Expression permissionProcess(PlainSelect select, String whereSegment) {
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
        return where;
    }
}
