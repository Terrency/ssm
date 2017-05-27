package com.ssm.sys.core.mapper;

import com.ssm.common.model.ModelMap;
import com.ssm.common.page.Page;
import com.ssm.common.page.PageRequest;
import com.ssm.common.util.Constant;
import com.ssm.sys.api.model.User;
import com.ssm.sys.core.enums.UserStatus;
import com.ssm.sys.core.mapper.extension.UserExtMapper;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-core-context.xml",
        "classpath:sys-core-context.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserExtMapperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExtMapperTest.class);

    @Autowired
    private UserExtMapper userExtMapper;

    @Before
    public void setUp() throws Exception {
        Assert.notNull(userExtMapper);
        LOGGER.info("isAopProxy => {}", AopUtils.isAopProxy(userExtMapper));
        LOGGER.info("isJdkDynamicProxy => {}", AopUtils.isJdkDynamicProxy(userExtMapper));
        LOGGER.info("isCglibProxy => {}", AopUtils.isCglibProxy(userExtMapper));
    }

    @Test
    public void test1InsertBatch() throws Exception {
        List<User> list = new ArrayList<>();
        for (int i = 1000; i < 1010; i++) {
            User user = new User();
            user.setId((long) i);
            user.setCode("test_" + i);
            user.setName("test_" + i);
            user.setPass("test_" + i);
            user.setStatus(UserStatus.UNLOCKED.getValue());
            list.add(user);
        }
        int rows = userExtMapper.insertBatch(list);
        Assert.isTrue(rows == list.size());
    }

    @Test
    public void test2DeleteBatch() throws Exception {
        int rows = userExtMapper.deleteBatch(new Long[]{1000L, 1001L, 1002L, 1003L, 1004L});
        Assert.isTrue(rows == 5);
    }

    @Test
    public void test3SelectPage() throws Exception {
        Page<Map> page = userExtMapper.selectPage(PageRequest.newInstance(new ModelMap(), 0, 10));
        LOGGER.info("{}", page.getRecords());
    }

}
