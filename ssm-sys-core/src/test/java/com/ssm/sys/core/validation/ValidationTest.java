package com.ssm.sys.core.validation;

import com.ssm.common.base.util.Constant;
import com.ssm.sys.api.model.User;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-validation.xml",
        "classpath:spring-core-context.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValidationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationTest.class);

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull(userService);
    }

    @Test
    public void test1GetUser() throws Exception {
        for (int i = 0; i < 2; i++) {
            User user = userService.getByCode("admin");
            LOGGER.info("{}", user);
        }
    }

}
