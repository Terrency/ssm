package com.ssm.common.web.base;

import java.util.Calendar;
import java.util.Date;

public class SmsData {

    public static final int DEFAULT_MAX_AGE = 600;  // 600 second = 10 minute

    private final String code;
    private final String phone;
    private final Date createTime;
    private final int maxAge;
    private final Date expiryTime;

    public SmsData(String phone, String code) {
        this(phone, code, Calendar.getInstance().getTime());
    }

    public SmsData(String phone, String code, Date createTime) {
        this(phone, code, createTime, DEFAULT_MAX_AGE);
    }

    public SmsData(String phone, String code, Date createTime, int maxAge) {
        this.phone = phone;
        this.code = code;
        this.createTime = createTime;
        this.maxAge = maxAge;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createTime);
        calendar.add(Calendar.SECOND, DEFAULT_MAX_AGE);
        this.expiryTime = calendar.getTime();
    }

    public String getCode() {
        return code;
    }

    public String getPhone() {
        return phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

}
