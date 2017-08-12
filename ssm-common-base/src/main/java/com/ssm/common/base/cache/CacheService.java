package com.ssm.common.base.cache;

public interface CacheService {

    boolean set(String key, Object value);

    boolean set(String key, Object value, long maxAge);

    Object get(String key);

    <T> T get(String key, Class<T> clazz);

    boolean delete(String key);

    boolean hasKey(String key);

    boolean setExpire(String key, long maxAge);

    long getExpire(String key);

}
