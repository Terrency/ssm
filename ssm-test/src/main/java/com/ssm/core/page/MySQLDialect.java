package com.ssm.core.page;

public class MySQLDialect extends AbstractDialect {

    private static final String LIMIT_REPLACEMENT_TEMPLATE = "SELECT * FROM (%s) row_ LIMIT %d, %d";

    @Override
    public String getLimitQueryString(String sql, int offset, int limit) {
        return String.format(LIMIT_REPLACEMENT_TEMPLATE, sql, offset > 1 ? offset - 1 : 0, limit);
    }

}
