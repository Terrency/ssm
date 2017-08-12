package com.ssm.common.base.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

public class RedisCacheService extends AbstractCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheService.class);

    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error(String.format("缓存%s时被中断操作 ", key), e);
            return false;
        }
    }

    @Override
    public boolean set(String key, Object value, long maxAge) {
        try {
            redisTemplate.opsForValue().set(key, value, maxAge, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            LOGGER.error(String.format("缓存%s时被中断操作 ", key), e);
            return false;
        }
    }

    @Override
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            LOGGER.error(String.format("删除缓存%s时被中断操作 ", key), e);
            return false;
        }
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean setExpire(String key, long maxAge) {
        return redisTemplate.expire(key, maxAge, TimeUnit.SECONDS);
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void checkCacheConfig() throws IllegalArgumentException {
        Assert.notNull(redisTemplate, "Property 'redisTemplate' is required.");
    }

}
