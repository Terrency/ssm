package com.ssm.act.core.enums;

public enum LeaveStatus {

    INITIAL(0, "初始录入"),
    APPLYING(1, "正在申请"),
    PENDING(2, "正在审批"),
    COMPLETE(3, "审批完成");

    private final int value;

    private final String phrase;

    LeaveStatus(int value, String phrase) {
        this.value = value;
        this.phrase = phrase;
    }

    public int getValue() {
        return value;
    }

    public String getPhrase() {
        return phrase;
    }

}
