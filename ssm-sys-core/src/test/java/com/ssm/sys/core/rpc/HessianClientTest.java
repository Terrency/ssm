package com.ssm.sys.core.rpc;

import com.caucho.hessian.client.HessianProxyFactory;
import com.ssm.sys.api.model.User;
import com.ssm.sys.api.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HessianClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianClientTest.class);

    private static final String SERVICE_URL = "http://127.0.0.1:8081/rpc/userHessianService";

    private UserService userService;

    @Before
    public void setUp() throws Exception {
        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setOverloadEnabled(true);
        userService = (UserService) factory.create(UserService.class, SERVICE_URL);
        Assert.assertNotNull(userService);
    }

    @Test
    public void test() throws Exception {
        User user = userService.getByCode("admin");
        LOGGER.info("=== {} ===", user);
    }

}
