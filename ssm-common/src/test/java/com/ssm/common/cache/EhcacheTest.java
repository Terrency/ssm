package com.ssm.common.cache;

import com.ssm.common.util.Constant;
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
@ContextConfiguration(locations = {"classpath:spring-ehcache-test.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EhcacheTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EhcacheTest.class);

    private static final String CACHE_NAME = "captcha";

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void test() throws Exception {
        String cacheKey = "yENUMdedxL53odW68dYiMr1WFpu0cSvg";
        LOGGER.info("=== {} ===", cacheManager.getCacheNames());
        Cache cache = cacheManager.getCache(CACHE_NAME);
        cache.put(cacheKey, Boolean.TRUE);
        Assert.notNull(cache.get(cacheKey));
        Thread.sleep(TimeUnit.MILLISECONDS.convert(30, TimeUnit.SECONDS));
        Assert.isNull(cache.get(cacheKey));
    }

}
