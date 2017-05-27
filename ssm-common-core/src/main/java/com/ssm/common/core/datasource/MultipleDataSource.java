package com.ssm.common.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSource extends AbstractRoutingDataSource {

    public static final String CURRENT_LOOKUP_KEY_READ = "read";
    public static final String CURRENT_LOOKUP_KEY_WRITE = "write";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 获取当前使用的数据源键值
     *
     * @return 数据源名称
     */
    public static String getCurrentLookupKey() {
        return contextHolder.get();
    }

    /**
     * 设置数据源键值
     *
     * @param currentLookupKey 设置数据源键值
     */
    public static void setCurrentLookupKey(String currentLookupKey) {
        contextHolder.set(currentLookupKey);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getCurrentLookupKey();
    }

}
