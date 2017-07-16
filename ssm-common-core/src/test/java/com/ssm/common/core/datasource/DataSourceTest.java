package com.ssm.common.core.datasource;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.ssm.common.base.util.Constant;
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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * <a href="http://tomcat.apache.org/tomcat-6.0-doc/jndi-resources-howto.html">JNDI</a>
 */
@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-core-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataSourceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test1Jndi() throws Exception {
        Connection conn = null;
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jndi/datasource");
            conn = ds.getConnection();
            Assert.assertNotNull(conn);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Test
    public void test2Jndi() throws Exception {
        // Create initial context
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        InitialContext ic = new InitialContext();
        ic.createSubcontext("java:");
        ic.createSubcontext("java:/comp");
        ic.createSubcontext("java:/comp/env");
        ic.createSubcontext("java:/comp/env/jdbc");
        // Construct DataSource
        MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
        ds.setURL("jdbc:mysql://127.0.0.1:3306/test");
        ds.setUser("root");
        ds.setPassword("root");
        ic.bind("java:/comp/env/jdbc/datasource", ds);
    }

}
