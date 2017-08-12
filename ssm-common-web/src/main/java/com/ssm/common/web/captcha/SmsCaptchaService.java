package com.ssm.common.web.captcha;

public interface SmsCaptchaService extends CaptchaService {

    /**
     * 发送短信
     *
     * @param phone   手机号
     * @param captcha 短信验证码
     * @return 短信验证码Token
     */
    String sendSms(String phone, String captcha) throws Exception;

    /**
     * 发送短信
     *
     * @param imgToken   图片验证码token
     * @param imgCaptcha 图片验证码
     * @param phone      手机号
     * @return 短信验证码Token
     * @throws Exception
     */
    String sendSms(String imgToken, String imgCaptcha, String phone) throws Exception;

    /**
     * 校验验证码是否正确
     *
     * @param token   短信验证码token
     * @param captcha 短信验证码
     * @param phone   手机号
     * @return 短信验证码是否正确
     * @throws Exception
     */
    boolean verify(String token, String captcha, String phone) throws Exception;

    /**
     * 校验验证码是否正确(供后台其它功能调用)
     *
     * @param token 短信验证码token
     * @return 短信验证码是否正确
     */
    boolean verify(String token);

}
