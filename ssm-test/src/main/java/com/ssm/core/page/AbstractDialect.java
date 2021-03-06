package com.ssm.core.page;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDialect implements Dialect {

    protected static final String COUNT_REPLACEMENT_TEMPLATE = "SELECT COUNT(*) %s";
    protected static final Pattern ORDER_BY_PATTERN = Pattern.compile("\\s+ORDER\\s+BY\\s+.*", Pattern.CASE_INSENSITIVE);

    @Override
    public String getCountString(String sql) {
        if (sql == null || sql.length() == 0) {
            throw new IllegalArgumentException("The argument [sql] is required and it must not be null.");
        }
        return String.format(COUNT_REPLACEMENT_TEMPLATE, removeSelect(removeOrderBy(sql)));
    }

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        if (sql == null || sql.length() == 0) {
            throw new IllegalArgumentException("The argument [sql] is required and it must not be null.");
        }
        return getLimitQueryString(sql, offset, limit);
    }

    protected abstract String getLimitQueryString(String sql, int offset, int limit);

    /**
     * Remove select clause
     */
    protected String removeSelect(String sql) {
        return sql.substring(sql.toUpperCase().indexOf("FROM"));
    }

    /**
     * Remove Order By clause
     */
    protected String removeOrderBy(String sql) {
        Matcher matcher = ORDER_BY_PATTERN.matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
