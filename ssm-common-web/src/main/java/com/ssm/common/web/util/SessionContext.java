package com.ssm.common.web.util;

import com.ssm.common.base.subject.ActiveUser;
import com.ssm.common.base.util.Constant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Deprecated
public abstract class SessionContext {

    private static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    private static void set(String name, Object value) {
        if (value != null) {
            getSession().setAttribute(name, value);
        } else {
            getSession().removeAttribute(name);
        }
    }

    private static Object get(String name) {
        return getSession().getAttribute(name);
    }

    public static void set(ActiveUser activeUser) {
        set(Constant.USER_SESSION_KEY, activeUser);
    }

    public static ActiveUser get() {
        return (ActiveUser) get(Constant.USER_SESSION_KEY);
    }

}
