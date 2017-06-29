package com.ssm.common.captcha;

import com.ssm.common.exception.BusinessException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class CaptchaServiceImpl implements CaptchaService {

    private static final char[] CHAR_BASE = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private static final int CHAR_LENGTH = 6;

    private static Random random = new Random();

    private static final String CACHE_NAME = "captcha";

    private final CacheManager cacheManager;

    public CaptchaServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public String genToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 处理逻辑：
     * 在后台生成图片验证码，将 s_${s}->验证码+验证次数存储在缓存中(有效期10分钟)
     */
    @Override
    public String getImgCaptcha(String imgToken) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CHAR_LENGTH; i++) {
            int randInt = Math.abs(random.nextInt());
            sb.append(CHAR_BASE[randInt % CHAR_BASE.length]);
        }
        String captcha = sb.toString();
        ImgCaptcha imgCaptcha = new ImgCaptcha();
        imgCaptcha.setCount(0);
        imgCaptcha.setCaptcha(captcha);
        imgCaptcha.setCreateTime(Calendar.getInstance().getTime());
        Cache cache = cacheManager.getCache(CACHE_NAME);
        cache.put(imgToken, imgCaptcha);
        return captcha;
    }

    /**
     * 处理逻辑：
     * (1)检查图片验证码的验证是否超过调用限制次数(最多3次，超过后直接销毁s值)
     * (2)检查s对应的imgvcode是否正确(验证用户输入的图形验证码是否正确)
     */
    @Override
    public boolean verifyImgCaptcha(String imgToken, String imgCaptcha) throws Exception {
        Cache cache = cacheManager.getCache(CACHE_NAME);
        ImgCaptcha captcha = cache.get(imgToken, ImgCaptcha.class);
        if (captcha == null) {
            throw new BusinessException("该图片验证码不存在或已失效");
        }
        if (captcha.getCount() > 3) {
            cache.evict(imgToken);
            throw new BusinessException("该图片验证码已超过限制验证次数3次，请重新获取");
        }
        captcha.setCount(captcha.getCount() + 1);
        cache.put(imgToken, captcha);
        return captcha.getCaptcha().equals(imgCaptcha);
    }

    /**
     * 处理逻辑：
     * (1)检查图片验证码的验证是否超过调用限制次数(最多3次，超过后直接销毁s值)
     * (2)检查s对应的imgvcode是否正确(验证用户输入的图形验证码是否正确)
     * (3)检查手机号发送验证码是否超过调用限制次数(30分钟内最多3次)
     * (4)给手机号phone发送短信验证码phonevcode，并更新redis中phonechk_{phone}对应的发送次数
     * (5)生成一个32位的随机字符串k，将${k}->phone+phonevcode +是否验证通过+验证次数存储在redis中（有效期 2分钟）
     */
    @Override
    public String sendSms(String imgToken, String imgCaptcha, String phone) throws Exception {
        boolean flag = verifyImgCaptcha(imgToken, imgToken);
        if (!flag) {
            throw new BusinessException("图片验证码不正确");
        }
        String smsToken = String.format("%s_%s", imgToken, phone);
        // Cache cache = cacheManager.getCache(CACHE_NAME);
        // SmsCaptcha smsCaptcha = cache.get(smsToken, SmsCaptcha.class);
        // if (captcha == null) {
        //     throw new BusinessException("该图片验证码不存在或已失效");
        // }
        // if (smsCaptcha.getCount() > 3) {
        //     cache.evict(imgToken);
        //     throw new BusinessException("该图片验证码已超过限制验证次数3次，请重新获取");
        // }
        return smsToken;
    }

    @Override
    public boolean verifySmsCaptcha(String smsToken, String smsCaptcha) throws Exception {
        return false;
    }

}
