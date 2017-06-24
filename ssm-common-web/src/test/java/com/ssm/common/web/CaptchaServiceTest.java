package com.ssm.common.web;

import com.ssm.common.util.Constant;
import com.ssm.common.web.captcha.CaptchaService;
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
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CaptchaServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaServiceTest.class);

    @Autowired
    private CaptchaService captchaService;

    @Test
    public void test() throws Exception {
        String capToken = captchaService.genCapToken();
        String capText = captchaService.getCapText(capToken);
        LOGGER.info("=== capToken: {} ===", capToken);
        LOGGER.info("=== capText: {} ===", capText);
    }

}
