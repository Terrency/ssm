package com.ssm.common.base.thread;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 当多线程访问公共数据时就有可能产生线程安全问题;
 * 当然大多数情况下, 我们根本不需要考虑线程安全问题, 比如 Service & DAO 等, 除非在 Bean 中声明了实例变量;
 * 因此对于线程不安全的对象最好定义在方法体内作为局部变量使用而不建议定义为成员变量使用!
 * <p/>
 * Synchronized用于线程间数据的共享而ThreadLocal用于线程间数据的隔离
 */
public class ThreadLocalTest {

    private static final Map<Thread, String> map = new HashMap<>();

    private class TestA {
        public void get() {
            System.err.println("TestA: " + map.get(Thread.currentThread()));
        }
    }

    private class TestB {
        public void get() {
            System.err.println("TestB: " + map.get(Thread.currentThread()));
        }
    }

    @Test
    public void testThreadLocal() throws Exception {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    map.put(Thread.currentThread(), Thread.currentThread().getName());
                    new TestA().get();
                    new TestB().get();
                }
            }).start();
        }
    }

}
