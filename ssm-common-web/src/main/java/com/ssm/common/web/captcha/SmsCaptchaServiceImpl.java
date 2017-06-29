package com.ssm.common.web.captcha;

import com.ssm.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Calendar;

public class SmsCaptchaServiceImpl extends AbstractCaptchaService implements SmsCaptchaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsCaptchaServiceImpl.class);

    private static final int MAX_SEND_COUNT = 3;    // 短信验证码限制发送次数(10分钟)

    private int maxSendCount = MAX_SEND_COUNT;

    private ImgCaptchaService imgCaptchaService;

    @Override
    public String sendSms(String phone, String captcha) throws Exception {
        SmsCaptcha smsCaptcha = cacheService.get(phone, SmsCaptcha.class);
        if (smsCaptcha == null) {
            smsCaptcha = new SmsCaptcha();
            smsCaptcha.setSendCount(1);
            smsCaptcha.setCreateTime(Calendar.getInstance().getTime());
        } else {
            if (smsCaptcha.getSendCount() > maxSendCount) {
                throw new BusinessException("该手机号发送短信验证码已超过限制次数" + maxSendCount + "次，请10分钟以后再试");
            }
            smsCaptcha.setSendCount(smsCaptcha.getSendCount() + 1);
        }
        doSendSms(phone, captcha);
        smsCaptcha.setPhone(phone);
        smsCaptcha.setCaptcha(captcha);
        smsCaptcha.setVerifyCount(0);
        smsCaptcha.setVerification(SmsCaptcha.Verification.NOT_PASS);
        cacheService.set(phone, smsCaptcha, maxAge);
        String smsToken = genToken();
        cacheService.set(smsToken, smsCaptcha, maxAge);
        return smsToken;
    }

    @Override
    public String sendSms(String imgToken, String imgCaptcha, String phone) throws Exception {
        if (!imgCaptchaService.verify(imgToken, imgCaptcha)) {
            throw new BusinessException("图片验证码不正确");
        }
        return sendSms(phone, genToken());
    }

    @Override
    public boolean verify(String token, String captcha, String phone) throws Exception {
        SmsCaptcha smsCaptcha = cacheService.get(token, SmsCaptcha.class);
        if (smsCaptcha == null) {
            throw new BusinessException("该短信验证码不存在或已失效");
        }
        if (!smsCaptcha.getPhone().equals(phone)) {
            throw new BusinessException("两次输入手机号不一致");
        }
        if (!smsCaptcha.getCaptcha().equals(captcha)) {
            return false;
        }
        if (smsCaptcha.getVerifyCount() > MAX_VERIFY_COUNT) {
            cacheService.delete(token);
            throw new BusinessException("该短信验证码已超过限制验证次数" + MAX_VERIFY_COUNT + "次，请重新获取");
        }
        smsCaptcha.setVerifyCount(smsCaptcha.getVerifyCount() + 1);
        smsCaptcha.setVerification(SmsCaptcha.Verification.PASS);
        cacheService.set(token, captcha, maxAge);
        return true;
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

    private void doSendSms(String phoneNo, String captcha) throws Exception {
        // TODO 调用第三方短信平台API发送短信验证码
    }

}
