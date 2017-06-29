package com.ssm.common.captcha;

import java.util.Date;

public abstract class Captcha {

    // 验证码
    private String captcha;
    // 验证次数
    private int count = 0;
    // 创建时间
    private Date createTime;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
