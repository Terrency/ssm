package com.ssm.common.web.captcha;

import java.io.Serializable;

public abstract class Captcha implements Serializable {

    private static final long serialVersionUID = 1L;

    private String captcha;                 // 验证码
    private int verifyCount = 0;            // 验证次数
    private int verifyCorrectCount = 0;     // 验证正确次数
    private int verifyErrorCount = 0;       // 验证错误次数

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public int getVerifyCount() {
        return verifyCount;
    }

    public void setVerifyCount(int verifyCount) {
        this.verifyCount = verifyCount;
    }

    public int getVerifyCorrectCount() {
        return verifyCorrectCount;
    }

    public void setVerifyCorrectCount(int verifyCorrectCount) {
        this.verifyCorrectCount = verifyCorrectCount;
    }

    public int getVerifyErrorCount() {
        return verifyErrorCount;
    }

    public void setVerifyErrorCount(int verifyErrorCount) {
        this.verifyErrorCount = verifyErrorCount;
    }

}

