package com.ssm.common.web.util;

import com.ssm.common.base.subject.ActiveUser;
import org.apache.shiro.SecurityUtils;

public abstract class SecurityHelper {

    public static ActiveUser getActiveUser() {
        return (ActiveUser) SecurityUtils.getSubject().getPrincipal();
    }

}
