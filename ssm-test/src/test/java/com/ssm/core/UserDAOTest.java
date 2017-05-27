package com.ssm.core;

import com.ssm.core.dao.UserDAO;
import com.ssm.core.page.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class UserDAOTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOTest.class);

    @Resource(name = "userDAO")
    private UserDAO userDAO;

    @Test
    public void test1() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "admin");
        Page page = userDAO.queryForPage(map, 0, 10);
        LOGGER.info("{}", page);
    }

}
