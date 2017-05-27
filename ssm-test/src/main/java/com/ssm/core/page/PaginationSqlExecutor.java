package com.ssm.core.page;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaginationSqlExecutor extends SqlExecutor {

    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    private final Dialect dialect;

    public PaginationSqlExecutor(Dialect dialect) {
        this.dialect = dialect;
    }

    public void setRecordCount(Long recordCount) {
        threadLocal.set(recordCount);
    }

    public Long getRecordCount() {
        return threadLocal.get();
    }

    @Override
    public void executeQuery(StatementScope statementScope, Connection conn, String sql, Object[] parameters, int offset, int limit, RowHandlerCallback callback) throws SQLException {
        if (limit > 0) {
            if (conn != null) {
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                try {
                    statement = conn.prepareStatement(dialect.getCountString(sql));
                    statementScope.getParameterMap().setParameters(statementScope, statement, parameters);
                    resultSet = statement.executeQuery();
                    Long recordCount = 0L;
                    while (resultSet.next()) {
                        recordCount = resultSet.getLong(1);
                    }
                    setRecordCount(recordCount);
                } finally {
                    if (resultSet != null) {
                        resultSet.close();
                    }
                    if (statement != null) {
                        statement.close();
                    }
                }
            }
            sql = dialect.getLimitString(sql, offset, limit);
        }
        super.executeQuery(statementScope, conn, sql, parameters, SqlExecutor.NO_SKIPPED_RESULTS, SqlExecutor.NO_MAXIMUM_RESULTS, callback);
    }
}
