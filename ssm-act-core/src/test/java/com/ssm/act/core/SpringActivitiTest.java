package com.ssm.act.core;

import com.ssm.common.base.util.Constant;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
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
import org.springframework.util.Assert;

import javax.annotation.Resource;

@ActiveProfiles(Constant.ENV_DEV)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:spring-core-context.xml",
        "classpath:spring-activiti.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringActivitiTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringActivitiTest.class);

    @Resource(name = "processEngine")
    private ProcessEngine processEngine;

    @Before
    public void setUp() throws Exception {
        Assert.notNull(processEngine);
    }

    @Test
    public void test1Query() throws Exception {
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceBusinessKey("34", "LeaveProcess")
                .singleResult();
        LOGGER.info("{}", processInstance);
    }

}
