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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-redis-test.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() throws Exception {
        redisTemplate.opsForValue().set("uid", "Gavin", 50, TimeUnit.SECONDS);
        Assert.assertTrue(redisTemplate.hasKey("uid"));
    }

}
