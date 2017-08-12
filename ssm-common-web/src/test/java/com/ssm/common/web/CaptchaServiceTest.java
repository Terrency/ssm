package com.ssm.common.web;

import com.ssm.common.base.util.Constant;
import com.ssm.common.web.captcha.Captcha;
import com.ssm.common.web.captcha.ImgCaptchaService;
import com.ssm.common.web.captcha.SmsCaptchaService;
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
    private ImgCaptchaService imgCaptchaService;

    @Autowired
    private SmsCaptchaService smsCaptchaService;

    @Test
    public void test1ImgCaptcha() throws Exception {
        String token = imgCaptchaService.genToken(Captcha.class.getSimpleName().toUpperCase());
        String captcha = imgCaptchaService.genCaptcha(token);
        LOGGER.info("=== {} ===", imgCaptchaService.verify(token, captcha));
        LOGGER.info("=== {} ===", imgCaptchaService.verify(token, captcha));
    }

    @Test
    public void test2SmsCaptcha() throws Exception {
        String phone = "18798009093";
        String captcha = "666666";
        String token = smsCaptchaService.sendSms(phone, captcha);
        LOGGER.info("=== {} ===", smsCaptchaService.verify(token, captcha, phone));
        LOGGER.info("=== {} ===", smsCaptchaService.verify(token, captcha, phone));
    }

}
