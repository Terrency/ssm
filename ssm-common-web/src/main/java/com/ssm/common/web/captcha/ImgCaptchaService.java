package com.ssm.common.web.captcha;

import java.awt.image.BufferedImage;

public interface ImgCaptchaService extends CaptchaService {

    /**
     * 根据token生成验证码
     *
     * @param token 验证码token
     * @return 验证码
     */
    String genCaptcha(String token);

    /**
     * 根据验证码生成验证码图片
     *
     * @param captcha 验证码
     * @return 验证码图片
     */
    BufferedImage createImage(String captcha);

    /**
     * 校验验证码是否正确
     *
     * @param token   验证码token
     * @param captcha 验证码
     * @return 验证码是否正确
     * @throws Exception
     */
    boolean verify(String token, String captcha) throws Exception;

}
