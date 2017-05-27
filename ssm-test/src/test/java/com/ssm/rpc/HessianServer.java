package com.ssm.rpc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HessianServer {

    public static void main(String[] args) throws Exception {
        new ClassPathXmlApplicationContext("classpath:hessian-server.xml").start();
    }

}
