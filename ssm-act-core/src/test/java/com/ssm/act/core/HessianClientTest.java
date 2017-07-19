package com.ssm.act.core;

import com.caucho.hessian.client.HessianProxyFactory;
import com.ssm.act.api.service.LeaveService;
import com.ssm.act.api.service.ProcessService;
import com.ssm.common.base.model.ModelMap;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HessianClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianClientTest.class);

    private static final String LEAVE_SERVICE_URL = "http://127.0.0.1:8082/rpc/leaveHessianService";
    private static final String PROCESS_SERVICE_URL = "http://127.0.0.1:8082/rpc/processHessianService";

    private LeaveService leaveService;

    private ProcessService processService;

    @Before
    public void setUp() throws Exception {
        HessianProxyFactory factory1 = new HessianProxyFactory();
        factory1.setOverloadEnabled(true);
        leaveService = (LeaveService) factory1.create(LeaveService.class, LEAVE_SERVICE_URL);
        Assert.assertNotNull(leaveService);
        HessianProxyFactory factory2 = new HessianProxyFactory();
        factory2.setOverloadEnabled(true);
        processService = (ProcessService) factory2.create(ProcessService.class, PROCESS_SERVICE_URL);
        Assert.assertNotNull(processService);
    }

    @Test
    public void test1GetList() throws Exception {
        List<Map> list = leaveService.getList(new ModelMap());
        LOGGER.info("=== {} ===", list);
    }

    @Test
    public void test2Deploy() throws Exception {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("LeaveProcess.zip");
        processService.deploy("请假流程", inputStream);
        IOUtils.closeQuietly(inputStream);
    }

}
