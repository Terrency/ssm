package com.ssm.sys.core.integration;

import com.ssm.common.base.util.Constant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-core-context.xml",
        "classpath:sys-core-context.xml"
})
public class SpringMybatisTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SqlSessionFactory sessionFactory;

    @Autowired
    private DataSource dataSource;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSpring() throws Exception {
        Assert.assertNotNull(applicationContext);
    }

    @Test
    public void testSessionFactory() throws Exception {
        Assert.assertNotNull(sessionFactory);
    }

    @Test
    public void testDataSource() throws Exception {
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void testMybatis() throws Exception {
        Assert.assertNotNull(applicationContext.getBean("userMapper"));
    }

}
