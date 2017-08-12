package com.ssm.common.base.cache;

import com.ssm.common.base.util.Constant;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-redis-test.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisTemplateTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTemplateTest.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test1OpsForValue() throws Exception {
        String captcha = "abc123";
        String prefix = "CAPTCHA";
        String token = String.format("%s_%d", prefix, System.currentTimeMillis());
        // redisTemplate.opsForValue().set(token, captcha);
        redisTemplate.opsForValue().set(token, captcha, 60, TimeUnit.SECONDS);
        Assert.assertTrue(redisTemplate.hasKey(token));
        Object value = redisTemplate.opsForValue().get(token);
        LOGGER.info("=== captcha: {} ===", value);
        LOGGER.info("=== expire: {} ===", redisTemplate.getExpire(token));
    }

    @Test
    public void test2OpsForHash() throws Exception {
        String prefix = "CAPTCHA";
        String token = String.format("%s_%d", prefix, System.currentTimeMillis());
        Map<String, Object> map = new HashMap<>();
        map.put("captcha", "abc123");
        map.put("verifyCount", "0");
        map.put("verifyCorrectCount", "0");
        map.put("verifyErrorCount", "0");
        redisTemplate.opsForHash().putAll(token, map);
        redisTemplate.expire(token, 30, TimeUnit.SECONDS);
        Thread.sleep(TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS));
        redisTemplate.opsForHash().put(token, "captcha", "123abc");
        Long maxAge = redisTemplate.getExpire(token, TimeUnit.SECONDS);
        Object captcha = redisTemplate.opsForHash().get(token, "captcha");
        LOGGER.info("=== maxAge: {} ===", maxAge);
        LOGGER.info("=== captcha: {} ===", captcha);
    }

    @Test
    public void test3OpsForPojo() throws Exception {
        String token = String.format("%s_%d", Captcha.class.getSimpleName().toUpperCase(), System.currentTimeMillis());
        Captcha captcha = new Captcha();
        captcha.setCaptcha("abc123");
        captcha.setCreateTime(Calendar.getInstance().getTime());
        redisTemplate.opsForValue().set(token, captcha, 60, TimeUnit.SECONDS);
        Thread.sleep(TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS));
        Object obj = redisTemplate.opsForValue().get(token);
        if (obj instanceof Captcha) {
            captcha = (Captcha) obj;
            captcha.setCaptcha("123abc");
            redisTemplate.opsForValue().set(token, captcha, redisTemplate.getExpire(token), TimeUnit.SECONDS);
        }
    }

    @Test
    public void test4OpsForPojo() throws Exception {
        Captcha captcha = new Captcha();
        captcha.setCaptcha("abc123");
        captcha.setCreateTime(Calendar.getInstance().getTime());
        JacksonHashMapper<Captcha> hashMapper = new JacksonHashMapper<>(Captcha.class);
        Map<String, Object> map = hashMapper.toHash(captcha);
        String prefix = "CAPTCHA";
        String token = String.format("%s_%d", prefix, System.currentTimeMillis());
        HashOperations<String, String, Object> opsForHash = redisTemplate.opsForHash();
        opsForHash.putAll(token, map);
        redisTemplate.expire(token, 600, TimeUnit.SECONDS);
        Map<String, Object> entries = opsForHash.entries(token);
        captcha = hashMapper.fromHash(entries);
        LOGGER.info("{}", captcha);
    }

}
