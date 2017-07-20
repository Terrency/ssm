package com.ssm.act.core;

import com.caucho.hessian.client.HessianProxyFactory;
import com.ssm.act.api.service.LeaveService;
import com.ssm.act.api.service.ProcessService;
import com.ssm.common.base.model.ModelMap;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HessianClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianClientTest.class);

    private static final String SERVICE_URL = "http://127.0.0.1:8082/rpc/processHessianService";

    private ProcessService processService;

    @Before
    public void setUp() throws Exception {
        HessianProxyFactory factory = new HessianProxyFactory();
        factory.setOverloadEnabled(true);
        processService = (ProcessService) factory.create(ProcessService.class, SERVICE_URL);
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

}
