package com.ssm.common.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

public class RedisCacheService extends AbstractCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisCacheService.class);

    private RedisTemplate redisTemplate;

    @Override
    public boolean set(String key, Object value, int maxAge) {
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
    public void checkCacheConfig() throws IllegalArgumentException {
        Assert.notNull(redisTemplate, "Property 'redisTemplate' is required.");
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
