package com.ssm.rpc;

import com.caucho.hessian.client.HessianProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HessianClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(HessianClient.class);

    public static final String SERVICE_URL = "http://127.0.0.1:8080/hessian/rpc/userHessianService.htm";

    public static void main(String[] args) throws Exception {
        HessianProxyFactory factory = new HessianProxyFactory();
        UserService userService = (UserService) factory.create(UserService.class, SERVICE_URL);
        LOGGER.info("=== {} ===", userService.getById("admin"));
    }

}
