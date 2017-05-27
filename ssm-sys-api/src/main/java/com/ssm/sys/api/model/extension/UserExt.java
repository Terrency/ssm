package com.ssm.sys.api.model.extension;

import com.ssm.sys.api.model.User;

public class UserExt extends User {

    private String managerCode;

    private String managerName;

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}
