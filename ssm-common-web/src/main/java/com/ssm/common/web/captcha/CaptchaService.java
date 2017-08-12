package com.ssm.common.web.captcha;

public interface CaptchaService {

    /**
     * 生成一个经过加密后的令牌Token
     *
     * @return 令牌Token
     */
    String genToken(String prefix);

    /**
     * 检查token格式是否正确
     *
     * @param token 令牌Token
     * @return token格式是否正确
     */
    boolean checkToken(String token);

    /**
     * 使验证码失效
     *
     * @param token 验证码token
     */
    void invalid(String token);

}
