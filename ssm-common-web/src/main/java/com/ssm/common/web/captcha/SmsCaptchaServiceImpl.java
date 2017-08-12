package com.ssm.common.web.captcha;

import com.ssm.common.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class SmsCaptchaServiceImpl extends AbstractCaptchaService implements SmsCaptchaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsCaptchaServiceImpl.class);

    private static final String SMS_CACHE_PREFIX = "SMS_CAPTCHA_%s";

    private static final int MAX_SEND_COUNT = 3;    // 短信验证码限制发送次数(10分钟)

    private int maxSendCount = MAX_SEND_COUNT;

    private ImgCaptchaService imgCaptchaService;

    @Override
    public String sendSms(String phone, String captcha) throws Exception {
        String cacheKey = String.format(SMS_CACHE_PREFIX, phone);
        SmsCaptcha smsCaptcha = cacheService.get(cacheKey, SmsCaptcha.class);
        if (smsCaptcha == null) {
            smsCaptcha = new SmsCaptcha();
            smsCaptcha.setSendCount(1);
        } else {
            if (smsCaptcha.getSendCount() > maxSendCount) {
                throw new BusinessException("该手机号发送短信验证码已超过限制发送次数" + maxSendCount + "次，请10分钟以后再试");
            }
            smsCaptcha.setSendCount(smsCaptcha.getSendCount() + 1);
        }
        doSendSms(phone, captcha);
        smsCaptcha.setPhone(phone);
        smsCaptcha.setCaptcha(captcha);
        smsCaptcha.setVerifyCount(0);
        smsCaptcha.setVerifyCorrectCount(0);
        smsCaptcha.setVerifyErrorCount(0);
        smsCaptcha.setVerification(SmsCaptcha.UNVERIFIED);
        cacheService.set(cacheKey, smsCaptcha, cacheService.hasKey(cacheKey) ? cacheService.getExpire(cacheKey) : maxAge);
        String token = genToken(captcha);
        cacheService.set(token, smsCaptcha, maxAge);
        return token;
    }

    @Override
    public String sendSms(String imgToken, String imgCaptcha, String phone) throws Exception {
        if (!imgCaptchaService.verify(imgToken, imgCaptcha)) {
            throw new BusinessException("图片验证码不正确");
        }
        String captcha = genCaptcha();
        return sendSms(phone, captcha);
    }

    @Override
    public boolean verify(String token, String captcha, String phone) throws Exception {
        SmsCaptcha smsCaptcha = cacheService.get(token, SmsCaptcha.class);
        if (smsCaptcha == null) {
            throw new BusinessException("该短信验证码已失效");
        }
        if (!smsCaptcha.getPhone().equals(phone)) {
            throw new BusinessException("两次输入手机号不一致");
        }
        if (smsCaptcha.getVerifyCount() > MAX_VERIFY_COUNT) {
            cacheService.delete(token);
            throw new BusinessException("该短信验证码已超过限制验证次数" + MAX_VERIFY_COUNT + "次，请重新获取");
        }
        boolean flag = smsCaptcha.getCaptcha().equals(captcha);
        if (flag) {
            smsCaptcha.setVerifyCorrectCount(smsCaptcha.getVerifyCorrectCount() + 1);
            smsCaptcha.setVerification(SmsCaptcha.VERIFICATION_PASSED);
        } else {
            smsCaptcha.setVerifyErrorCount(smsCaptcha.getVerifyErrorCount() + 1);
            smsCaptcha.setVerification(SmsCaptcha.VERIFICATION_NOT_PASSED);
        }
        smsCaptcha.setVerifyCount(smsCaptcha.getVerifyCount() + 1);
        cacheService.set(token, smsCaptcha, cacheService.getExpire(token));
        return flag;
    }

    @Override
    public boolean verify(String token) {
        SmsCaptcha smsCaptcha = cacheService.get(token, SmsCaptcha.class);
        return smsCaptcha != null && smsCaptcha.getVerification() == SmsCaptcha.VERIFICATION_PASSED;
    }

    @Override
    protected void checkCaptchaConfig() throws IllegalArgumentException {
        Assert.notNull(imgCaptchaService);
    }

    public void setMaxSendCount(int maxSendCount) {
        this.maxSendCount = maxSendCount;
    }

    public void setImgCaptchaService(ImgCaptchaService imgCaptchaService) {
        this.imgCaptchaService = imgCaptchaService;
    }

    private void doSendSms(String phone, String captcha) throws Exception {
        // TODO 调用第三方短信平台API发送短信验证码
    }

}
