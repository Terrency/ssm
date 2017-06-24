package com.ssm.common.web.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 扩展UsernamePasswordToken类用来添加额外的验证码参数
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

    private String capText;
    private String capToken;

    public String getCapText() {
        return capText;
    }

    public void setCapText(String capText) {
        this.capText = capText;
    }

    public String getCapToken() {
        return capToken;
    }

    public void setCapToken(String capToken) {
        this.capToken = capToken;
    }

    public CaptchaUsernamePasswordToken(String username, String password, boolean rememberMe,
                                        String host, String capText, String capToken) {
        super(username, password, rememberMe, host);
        this.capText = capText;
        this.capToken = capToken;
    }

}
