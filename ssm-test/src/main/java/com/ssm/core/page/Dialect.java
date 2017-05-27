package com.ssm.core.page;

public interface Dialect {

    String getCountString(String sql);

    String getLimitString(String sql, int offset, int limit);

}
