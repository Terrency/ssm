package com.ssm.common.core.page;

public class MySQLDialect extends AbstractDialect {

    private static final String LIMIT_REPLACEMENT_TEMPLATE = "SELECT * FROM (%s) row_ LIMIT %d, %d";

    @Override
    public String getLimitQueryString(String sql, int pageSize, int currentPage) {
        return String.format(LIMIT_REPLACEMENT_TEMPLATE, sql, currentPage > 1 ? (currentPage - 1) * pageSize : 0, pageSize);
    }

}
