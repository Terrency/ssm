package com.ssm.sys.web.rpc;

import com.ssm.sys.api.service.RoleService;
import com.ssm.sys.api.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:sys-web-context.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HessianClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianClientTest.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull(userService);
        Assert.assertNotNull(roleService);
    }

    @Test
    public void test() throws Exception {
        LOGGER.info("=== {} ===", userService);
        LOGGER.info("=== {} ===", roleService);
    }

}
