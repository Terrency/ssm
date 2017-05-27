package com.ssm.sys.api.model.extension;

import com.ssm.sys.api.model.Permission;

public class PermissionExt extends Permission {

    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

}
