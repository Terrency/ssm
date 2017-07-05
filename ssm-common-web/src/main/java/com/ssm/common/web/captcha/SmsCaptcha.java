package com.ssm.common.web.captcha;

import java.io.Serializable;

public class SmsCaptcha extends Captcha implements Serializable {

    private static final long serialVersionUID = 1L;

    private String phone;                   // 手机号
    private int sendCount;                  // 发送次数
    private Verification verification;      // 是否验证通过

    public enum Verification {UNVERIFIED, PASSED, NOT_PASSED}

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

    public Verification getVerification() {
        return verification;
    }

    public void setVerification(Verification verification) {
        this.verification = verification;
    }
}
