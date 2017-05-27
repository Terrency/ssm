package com.ssh.sys.web.dubbo;

import com.ssm.sys.api.model.User;
import com.ssm.sys.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerTest.class);

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:sys-web-consumer.xml");
        context.start();
        UserService userService = context.getBean(UserService.class);   // 获取远程服务代理
        User user = userService.getByCode("admin");                     // 执行远程方法
        LOGGER.info("=== {} ===", user);                                // 显示调用结果
    }

}
