package com.ssm.common.captcha;

public class SmsCaptcha extends Captcha {

    private String phone;
    private int checked = 0;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
