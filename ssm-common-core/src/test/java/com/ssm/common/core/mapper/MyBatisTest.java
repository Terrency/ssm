package com.ssm.common.core.mapper;

import com.ssm.common.util.Constant;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-core-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyBatisTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyBatisTest.class);

    @Inject
    private DataSource dataSource;

    @Inject
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull(dataSource);
        Assert.assertNotNull(sqlSessionFactory);
    }

    @Test
    public void test1GetDatabaseProductName() throws Exception {
        Connection conn = dataSource.getConnection();
        DatabaseMetaData metaData = conn.getMetaData();
        String databaseProductName = metaData.getDatabaseProductName();
        LOGGER.info("DatabaseProductName => {}", databaseProductName);
    }

    @Test
    public void test2GetConfiguration() throws Exception {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        LOGGER.info("LazyLoadingEnabled => {}", configuration.isLazyLoadingEnabled());
        LOGGER.info("AggressiveLazyLoading => {}", configuration.isAggressiveLazyLoading());
        LOGGER.info("CacheEnabled => {}", configuration.isCacheEnabled());
        LOGGER.info("DatabaseId => {}", configuration.getDatabaseId());
    }

}
