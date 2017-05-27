package com.ssm.sys.core.dubbo;

import com.ssm.common.util.Constant;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.AbstractEnvironment;

public class ProviderTest {

    public static void main(String[] args) throws Exception {
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, Constant.ENV_DEV);
        new ClassPathXmlApplicationContext("classpath:sys-service-provider.xml").start();
        System.out.println("Press any key to exit.");
        System.in.read();
    }

}
