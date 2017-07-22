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
import org.springframework.util.ResourceUtils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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
    public void test1GetDeploymentList() throws Exception {
        List<Deployment> deployList = processService.getDeploymentList();
        LOGGER.info("=== {} ===", deployList);
    }

    @Test
    public void test2Upload() throws Exception {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("LeaveProcess.zip");
        processService.deploy("请假流程", inputStream);
        IOUtils.closeQuietly(inputStream);
    }

    /**
     * Patch code:
     * <pre>
     *   Object value = in.readReply(method.getReturnType());
     *   if (value instanceof InputStream) {
     *       value = new ResultInputStream(conn, is, in, (InputStream) value);
     *       is = null;
     *       conn = null;
     *   }
     * </pre>
     *
     * @see com.caucho.hessian.client.HessianProxy#invoke
     * @see <a href=http://bugs.caucho.com/view.php?id=3655">Hessian Bug</a>
     */
    @Test
    public void test3Download() throws Exception {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = processService.getProcessDiagram("LeaveProcess:1:1704");
            out = new FileOutputStream("LeaveProcess.png");
            int len;
            byte[] buf = new byte[1024 * 4];
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    @Test
    public void test3DeleteDeployment() {
        processService.deleteDeployment("101");
    }

}
