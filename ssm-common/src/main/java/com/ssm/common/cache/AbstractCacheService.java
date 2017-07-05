package com.ssm.common.cache;

import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractCacheService implements CacheService, InitializingBean {

    @Override
    public <T> T get(String key, Class<T> clazz) {
        try {
            return clazz.cast(get(key));
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        checkCacheConfig();
    }

    protected abstract void checkCacheConfig() throws IllegalArgumentException;

}
