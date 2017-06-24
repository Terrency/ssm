package com.ssm.common.web.captcha;

import com.ssm.common.exception.BusinessException;

public interface CaptchaService {

    /**
     * 生成验证码令牌
     */
    String genCapToken();

    /**
     * 根据token获取验证码
     *
     * @param capToken 被校验的token
     * @return token中的验证码字符串
     * @throws BusinessException
     */
    String getCapText(String capToken) throws BusinessException;

    /**
     * 验证输入的验证码与令牌是否匹配
     *
     * @param capToken 验证码令牌
     * @param capText  验证码
     * @throws BusinessException
     */
    boolean doVerify(String capToken, String capText) throws BusinessException;

    /**
     * 获取XXTEA加密解密的密钥
     *
     * @return XXTEA加密解密的密钥
     */
    String getSecKey();

    /**
     * 获取验证码失效时限(秒)
     *
     * @return 验证码失效时限(秒)
     */
    int getMaxAge();

    /**
     * 获取缓存前缀
     *
     * @return 缓存前缀
     */
    String getCachePrefix();

    /**
     * 获取验证码字符数
     *
     * @return 验证码字符数
     */
    int getCharLength();

}
