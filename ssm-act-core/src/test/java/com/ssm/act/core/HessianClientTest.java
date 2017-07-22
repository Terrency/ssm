package com.ssm.act.core;

import com.ssm.act.api.service.ProcessService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.IOUtils;
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

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HessianClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianClientTest.class);

    @Autowired
    private ProcessService processService;

    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull(processService);
    }

    @Test
    public void test1GetList() throws Exception {
        List<Deployment> deployList = processService.getDeploymentList();
        LOGGER.info("=== {} ===", deployList);
    }

    @Test
    public void test2Deploy() throws Exception {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("LeaveProcess.zip");
        processService.deploy("请假流程", new ZipInputStream(inputStream));
        IOUtils.closeQuietly(inputStream);
    }

    @Test
    public void test3DeleteDeployment() {
        processService.deleteDeployment("101");
    }

}
