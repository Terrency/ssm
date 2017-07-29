package com.ssm.common.web.captcha;

import java.io.Serializable;

public class SmsCaptcha extends Captcha implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int UNVERIFIED = 0;
    public static final int VERIFICATION_PASSED = 1;
    public static final int VERIFICATION_NOT_PASSED = 2;

    private String phone;       // 手机号
    private int sendCount;      // 发送次数
    private int verification;   // 是否验证通过

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public int getVerification() {
        return verification;
    }

    public void setVerification(int verification) {
        this.verification = verification;
    }
}
