package com.ssm.sys.core.service;

import com.ssm.common.model.ModelMap;
import com.ssm.common.util.Constant;
import com.ssm.sys.api.model.User;
import com.ssm.sys.api.service.UserService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-core-context.xml",
        "classpath:sys-core-context.xml",
        "classpath:spring-validation.xml"
})
// @TransactionConfiguration(transactionManager = "transactionManager")
@Transactional(transactionManager = "transactionManager")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        Assert.notNull(userService);
        LOGGER.info("isAopProxy => {}", AopUtils.isAopProxy(userService));
        LOGGER.info("isJdkDynamicProxy => {}", AopUtils.isJdkDynamicProxy(userService));
        LOGGER.info("isCglibProxy => {}", AopUtils.isCglibProxy(userService));
    }

    @Test
    @Rollback(false)
    public void test1Add() throws Exception {
        User user = new User();
        user.setId(10001L);
        user.setCode("test_10001");
        user.setName("test_10001");
        user.setPass("test_10001");
        user.setStatus("0");
        int row = userService.add(user);
        Assert.isTrue(row == 1);
    }

    @Test
    public void test2GetList() throws Exception {
        List<Map> list = userService.getList(new ModelMap("id", 10001L));
        Assert.isTrue(list.size() == 1);
    }

    @Test
    @Rollback(false)
    public void test3Update() throws Exception {
        User user = new User();
        user.setId(10001L);
        user.setStatus("1");
        int row = userService.update(user);
        Assert.isTrue(row == 1);
    }

    @Test
    @Rollback(false)
    public void test4Delete() throws Exception {
        int row = userService.delete(new Long[]{10001L, 10002L});
        Assert.isTrue(row == 2);
    }

}
