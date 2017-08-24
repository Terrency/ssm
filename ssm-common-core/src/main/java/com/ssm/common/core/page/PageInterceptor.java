package com.ssm.common.core.page;

import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.PageImpl;
import com.ssm.common.base.page.Pageable;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class PageInterceptor implements Interceptor {

    private static final int MAPPED_STATEMENT_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;
    private static final int ROW_BOUNDS_INDEX = 2;
    private static final int RESULT_HANDLER_INDEX = 3;

    private final Dialect dialect;

    public PageInterceptor(Dialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        final Object parameter = args[PARAMETER_INDEX];
        if (parameter instanceof Pageable) {
            Pageable<?> pageable = (Pageable<?>) parameter;
            final MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
            final BoundSql boundSql = ms.getBoundSql(parameter);
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getCurrentPage();
            String limitSql = dialect.getLimitString(boundSql.getSql(), pageSize, currentPage);
            args[ROW_BOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
            args[MAPPED_STATEMENT_INDEX] = newMappedStatement(ms, boundSql, limitSql);
            List<?> itemList = (List<?>) invocation.proceed();
            int totalItems = pageable.isCountable() ? getCount(ms, boundSql) : 0;
            Page<?> page = new PageImpl<>(currentPage, pageSize, itemList, totalItems);
            List<Page<?>> list = new ArrayList<>(1);
            list.add(page);
            return list;
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private int getCount(MappedStatement ms, BoundSql boundSql) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String countSql = dialect.getCountString(boundSql.getSql());
            conn = ms.getConfiguration().getEnvironment().getDataSource().getConnection();
            pstmt = conn.prepareStatement(countSql);
            BoundSql countBoundSql = newBoundSql(ms, boundSql, countSql);
            ParameterHandler handler = new DefaultParameterHandler(ms, boundSql.getParameterObject(), countBoundSql);
            handler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private MappedStatement newMappedStatement(MappedStatement ms, BoundSql boundSql, String sql) {
        final BoundSql newBoundSql = newBoundSql(ms, boundSql, sql);
        return newMappedStatement(ms, new BoundSqlSqlSource(newBoundSql));
    }

    private BoundSql newBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        final BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    /**
     * @see org.apache.ibatis.builder.MapperBuilderAssistant
     */
    private MappedStatement newMappedStatement(MappedStatement ms, SqlSource sqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuilder keyProperties = new StringBuilder();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    static class BoundSqlSqlSource implements SqlSource {

        private final BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

}
