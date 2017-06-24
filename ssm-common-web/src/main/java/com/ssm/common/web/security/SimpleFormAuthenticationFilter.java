package com.ssm.common.web.security;

import com.ssm.common.exception.IncorrectCaptchaException;
import com.ssm.common.util.Constant;
import com.ssm.common.util.PropertiesLoader;
import com.ssm.common.web.captcha.CaptchaService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义基于表单认证的过滤器来实现在认证之前对验证码进行校验
 */
public class SimpleFormAuthenticationFilter extends FormAuthenticationFilter {

    public static final String DEFAULT_CAPTCHA_TEXT_PARAM = "capText";
    public static final String DEFAULT_CAPTCHA_TOKEN_PARAM = "capToken";

    private String capTextParam = DEFAULT_CAPTCHA_TEXT_PARAM;
    private String capTokenParam = DEFAULT_CAPTCHA_TOKEN_PARAM;

    @Autowired
    private CaptchaService captchaService;

    @Override
    protected CaptchaUsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {
        return new CaptchaUsernamePasswordToken(
                getUsername(request),
                getPassword(request),
                isRememberMe(request),
                getHost(request),
                getCapText(request),
                getCapToken(request)
        );
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        CaptchaUsernamePasswordToken token = this.createToken(request, response);
        try {
            // 若验证码校验失败则拒绝访问, 不再校验帐户和密码
            if (!doCaptchaVerify(request, token)) {
                return true;
            }
            Subject subject = getSubject(request, response);
            subject.login(token);
            subject.getSession().setAttribute(Constant.USER_SESSION_ATTRIBUTE, subject.getPrincipal());
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    protected boolean doCaptchaVerify(ServletRequest request, CaptchaUsernamePasswordToken token) {
        if (PropertiesLoader.getBoolean(PropertiesLoader.Config.USE_CAPTCHA)) {
            if (!captchaService.doVerify(token.getCapToken(), token.getCapText())) {
                request.setAttribute(getFailureKeyAttribute(), IncorrectCaptchaException.class.getName());
                return false;
            }
        }
        return true;
    }

    protected String getCapText(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCapTextParam());
    }

    protected String getCapToken(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCapTokenParam());
    }

    public String getCapTextParam() {
        return capTextParam;
    }

    public void setCapTextParam(String capTextParam) {
        this.capTextParam = capTextParam;
    }

    public String getCapTokenParam() {
        return capTokenParam;
    }

    public void setCapTokenParam(String capTokenParam) {
        this.capTokenParam = capTokenParam;
    }
}
