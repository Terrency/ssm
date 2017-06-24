package com.ssm.common.cache;

import com.ssm.common.util.Constant;
import net.spy.memcached.MemcachedClient;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-memcached-test.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemcachedTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedTest.class);

    private static final int MAX_AGE = 30;

    @Autowired
    private MemcachedClient memcachedClient;

    @Test
    public void test() throws Exception {
        String cacheKey = "yENUMdedxL53odW68dYiMr1WFpu0cSvg";
        Assert.isTrue(memcachedClient.set(cacheKey, MAX_AGE, Boolean.TRUE).get());
        LOGGER.info("=== {} ===", memcachedClient.get(cacheKey));
        Thread.sleep(TimeUnit.MILLISECONDS.convert(MAX_AGE, TimeUnit.SECONDS));
        LOGGER.info("=== {} ===", memcachedClient.get(cacheKey));
    }

}
