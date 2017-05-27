package com.ssm.sys.core.thread;

import com.ssm.common.util.Constant;
import com.ssm.sys.api.model.User;
import com.ssm.sys.api.service.UserService;
import net.sourceforge.groboutils.junit.v1.MultiThreadedTestRunner;
import net.sourceforge.groboutils.junit.v1.TestRunnable;
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
@ContextConfiguration(locations = {"classpath:spring-core-context.xml", "classpath:sys-core-context.xml"})
// @TransactionConfiguration(transactionManager = "transactionManager")
// @Transactional(transactionManager = "transactionManager")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ThreadTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadTest.class);

    @Autowired
    private UserService userService;

    private class ExecuteThread extends TestRunnable {
        @Override
        public void runTest() throws Throwable {
            LOGGER.info("==================== {} ====================", Thread.currentThread().getName());
            User user = userService.getByCode("admin");
            LOGGER.info("******************** {} ********************", user);
            // Thread.sleep(20);
        }
    }

    @Test
    public void testThread() throws Throwable {
        TestRunnable[] testRunnable = new TestRunnable[10];
        for (int i = 0; i < testRunnable.length; i++) {
            testRunnable[i] = new ExecuteThread();
        }
        new MultiThreadedTestRunner(testRunnable).runTestRunnables();
    }

    // private static final int COUNT = 100;
    //
    // private CountDownLatch latch = new CountDownLatch(COUNT);
    //
    // private class ExecuteThread implements Runnable {
    //     @Override
    //     public void run() {
    //         try {
    //             // 使当前线程在锁存器倒计数至零之前一直等待
    //             latch.await();
    //             doWork();
    //         } catch (InterruptedException e) {
    //             LOGGER.error("{}", e);
    //         }
    //     }
    //
    //     private void doWork() {
    //         LOGGER.info("==================== {} ====================", Thread.currentThread().getName());
    //         // TODO
    //         LOGGER.info("******************** {} ********************", Thread.currentThread().getId());
    //     }
    // }
    //
    // @Test
    // public void testThread() throws Exception {
    //     for (int i = 0; i < COUNT; i++) {
    //         new Thread(new ExecuteThread()).start();
    //         // 递减锁存器的计数, 如果计数到达零, 则释放所有等待的线程
    //         latch.countDown();
    //     }
    // }

}
