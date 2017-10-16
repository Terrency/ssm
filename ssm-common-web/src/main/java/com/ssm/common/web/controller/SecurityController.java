package com.ssm.common.web.controller;

import com.ssm.common.base.exception.IncorrectCaptchaException;
import com.ssm.common.base.util.Constant;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/security")
public class SecurityController {

    /**
     * 登录成功后跳转到登陆前的请求页面
     *
     * @see org.apache.shiro.web.util.WebUtils#getSavedRequest
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        String exceptionName = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        if (exceptionName != null) {
            if (IncorrectCaptchaException.class.getName().equals(exceptionName)) {
                request.setAttribute(Constant.EXCEPTION_KEY, "Incorrect Captcha");
            } else {
                request.setAttribute(Constant.EXCEPTION_KEY, "Invalid Username/Password");
            }
        }
        // String url = WebUtils.getSavedRequest(request).getRequestUrl();
        // 认证失败转到登录页面(认证成功由Shiro自动转到上一请求路径)
        return "security/login";
    }

}
