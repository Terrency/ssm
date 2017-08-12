package com.ssm.common.base.cache;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTest.class);

    private Jedis jedis;

    @Before
    public void setUp() throws Exception {
        jedis = new Jedis("127.0.0.1", 6379);
    }

    @Test
    public void test1Hset() throws Exception {
        jedis.hset("gavin", "id", "1001");
        jedis.hset("gavin", "code", "lu774374302");
        jedis.hset("gavin", "name", "lugavin");
        jedis.hset("gavin", "phone", "18798009093");
        LOGGER.info("values: {}", jedis.hkeys("gavin"));
        LOGGER.info("values: {}", jedis.hvals("gavin"));
        LOGGER.info("values: {}", jedis.hgetAll("gavin"));
        jedis.expire("gavin", 60);
    }

    @Test
    public void test2Hmset() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "1001");
        map.put("code", "lu774374302");
        map.put("name", "lugavin");
        map.put("phone", "18798009093");
        jedis.hmset("gavin", map);
        jedis.expire("gavin", 60);
    }

}
