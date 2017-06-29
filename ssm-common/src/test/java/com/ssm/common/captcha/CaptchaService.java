package com.ssm.common.captcha;

public interface CaptchaService {

    /**
     * 生成随机数
     */
    String genToken();

    /**
     * 处理逻辑：
     * 在后台生成图片验证码，将 s_${s}->验证码+验证次数存储在缓存中(有效期10分钟)
     */
    String getImgCaptcha(String imgToken);


    /**
     * 验证输入的验证码与令牌是否匹配
     */
    boolean verifyImgCaptcha(String imgToken, String imgCaptcha) throws Exception;

    /**
     * 处理逻辑：
     * 检查图片验证码的验证是否超过调用限制次数(最多3次，超过后直接销毁s值)
     * 检查s对应的imgvcode是否正确(验证用户输入的图形验证码是否正确)
     * 检查手机号发送验证码是否超过调用限制次数(30分钟内最多3次)
     * 给手机号phone发送短信验证码phonevcode，并更新redis中phonechk_{phone}对应的发送次数
     * 生成一个32位的随机字符串k，将${k}->phone+phonevcode +是否验证通过+验证次数存储在redis中（有效期 2分钟）
     */
    String sendSms(String imgToken, String imgCaptcha, String phone) throws Exception;

    boolean verifySmsCaptcha(String smsToken, String smsCaptcha) throws Exception;

}
