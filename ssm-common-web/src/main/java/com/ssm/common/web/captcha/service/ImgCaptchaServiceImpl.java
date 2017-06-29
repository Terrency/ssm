package com.ssm.common.web.captcha.service;

import com.google.code.kaptcha.Producer;
import com.ssm.common.exception.BusinessException;
import com.ssm.common.web.captcha.model.ImgCaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.awt.image.BufferedImage;
import java.util.Calendar;

public class ImgCaptchaServiceImpl extends AbstractCaptchaService implements ImgCaptchaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImgCaptchaServiceImpl.class);

    private Producer captchaProducer;

    @Override
    public String getCaptcha(String token) {
        String captcha = genCaptcha();
        ImgCaptcha imgCaptcha = new ImgCaptcha();
        imgCaptcha.setVerifyCount(0);
        imgCaptcha.setCaptcha(captcha);
        imgCaptcha.setCreateTime(Calendar.getInstance().getTime());
        cacheService.set(token, imgCaptcha, maxAge);
        return captcha;
    }

    @Override
    public BufferedImage getCaptchaImage(String captcha) {
        return captchaProducer.createImage(captcha);
    }

    @Override
    public boolean verify(String token, String captcha) throws Exception {
        ImgCaptcha imgCaptcha = cacheService.get(token, ImgCaptcha.class);
        if (imgCaptcha == null) {
            throw new BusinessException("该图片验证码已失效，请重新获取");
        }
        if (imgCaptcha.getVerifyCount() > MAX_VERIFY_COUNT) {
            cacheService.delete(token);
            throw new BusinessException("该图片验证码已超过限制验证次数" + MAX_VERIFY_COUNT + "次，请重新获取");
        }
        imgCaptcha.setVerifyCount(imgCaptcha.getVerifyCount() + 1);
        cacheService.set(token, imgCaptcha, maxAge);
        return imgCaptcha.getCaptcha().equalsIgnoreCase(captcha);
    }

    public void setCaptchaProducer(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @Override
    protected void checkCaptchaConfig() throws IllegalArgumentException {
        Assert.notNull(captchaProducer);
    }

}
