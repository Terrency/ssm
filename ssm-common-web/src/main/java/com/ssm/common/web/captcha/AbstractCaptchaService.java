package com.ssm.common.web.captcha;

import com.ssm.common.base.cache.CacheService;
import com.ssm.common.base.util.EncryptUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Random;

public abstract class AbstractCaptchaService implements InitializingBean {

    public static final String DEFAULT_CHAR_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static final int DEFAULT_MAX_AGE = 600;   // 验证码有效期(默认600秒即10分钟)
    public static final int DEFAULT_CHAR_LENGTH = 6; // 验证码字符个数
    public static final int MAX_VERIFY_COUNT = 3;    // 验证码限制验证次数(默认10分钟3次)

    protected char[] chars = DEFAULT_CHAR_STRING.toCharArray();
    protected int maxAge = DEFAULT_MAX_AGE;
    protected int charLength = DEFAULT_CHAR_LENGTH;
    protected int maxVerifyCount = MAX_VERIFY_COUNT;

    private Random random = new Random();

    protected CacheService cacheService;

    public String genCaptcha() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charLength; i++) {
            int randInt = Math.abs(random.nextInt());
            sb.append(chars[randInt % chars.length]);
        }
        return sb.toString();
    }

    public String genToken(String captcha) {
        return EncryptUtils.encrypt(String.format("%s_%d", captcha, System.currentTimeMillis()), EncryptUtils.DEFAULT_KEY);
    }

    public String getCaptcha(String token) {
        try {
            String plainText = EncryptUtils.decrypt(token, EncryptUtils.DEFAULT_KEY);
            String[] plainTextArr = plainText.split("_");
            if (plainTextArr.length != 2) {
                throw new IllegalStateException("The token data format error.");
            }
            // long timestamp = Long.parseLong(plainTextArr[1]);
            // if ((System.currentTimeMillis() - timestamp) > TimeUnit.MILLISECONDS.convert(DEFAULT_MAX_AGE, TimeUnit.SECONDS)) {
            //     throw new IllegalStateException("The verification code has expired.");
            // }
            return plainTextArr[0];
        } catch (Exception e) {
            // TODO Save the token to the blacklist
            throw new RuntimeException("Can not decrypt the token [" + token + "].", e);
        }
    }

    public void invalid(String token) {
        cacheService.delete(token);
    }

    public void setCharString(String charString) {
        this.chars = charString.toCharArray();
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setCharLength(int charLength) {
        this.charLength = charLength;
    }

    public void setMaxVerifyCount(int maxVerifyCount) {
        this.maxVerifyCount = maxVerifyCount;
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cacheService, "Property 'cacheService' is required.");
        checkCaptchaConfig();
    }

    protected abstract void checkCaptchaConfig() throws IllegalArgumentException;

}
