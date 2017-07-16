package com.ssm.sys.core.mapper;

import com.ssm.common.base.model.ModelMap;
import com.ssm.common.base.page.Page;
import com.ssm.common.base.page.PageRequest;
import com.ssm.common.base.util.Constant;
import com.ssm.sys.core.mapper.extension.Select2Mapper;
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

import java.util.List;
import java.util.Map;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-core-context.xml",
        "classpath:sys-core-context.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Select2MapperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Select2MapperTest.class);

    @Autowired
    private Select2Mapper select2Mapper;

    @Before
    public void setUp() throws Exception {
        Assert.notNull(select2Mapper);
        LOGGER.info("isAopProxy => {}", AopUtils.isAopProxy(select2Mapper));
        LOGGER.info("isJdkDynamicProxy => {}", AopUtils.isJdkDynamicProxy(select2Mapper));
        LOGGER.info("isCglibProxy => {}", AopUtils.isCglibProxy(select2Mapper));
    }

    @Test
    public void test1SelectList() throws Exception {
        List<Map> list = select2Mapper.selectActor(new ModelMap());
        LOGGER.info("{}", list);
    }

    @Test
    public void test1SelectPage() throws Exception {
        Page<Map> page = select2Mapper.selectActorPage(PageRequest.newInstance(new ModelMap(), 0, 10));
        LOGGER.info("{}", page.getRecords());
    }

}
