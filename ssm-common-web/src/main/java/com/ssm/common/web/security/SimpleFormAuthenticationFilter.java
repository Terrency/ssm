package com.ssm.common.web.security;

import com.google.code.kaptcha.Constants;
import com.ssm.common.base.enums.Config;
import com.ssm.common.base.exception.IncorrectCaptchaException;
import com.ssm.common.base.util.Constant;
import com.ssm.common.base.util.PropertiesLoader;
import com.ssm.common.base.util.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义基于表单认证的过滤器来实现在认证之前对验证码进行校验
 */
public class SimpleFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleFormAuthenticationFilter.class);

    public static final String DEFAULT_CAPTCHA_PARAM = "captcha";

    private String captchaParam = DEFAULT_CAPTCHA_PARAM;

    @Override
    protected CaptchaUsernamePasswordToken createToken(ServletRequest request, ServletResponse response) {
        return new CaptchaUsernamePasswordToken(
                getUsername(request),
                getPassword(request),
                isRememberMe(request),
                getHost(request),
                getCaptcha(request)
        );
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        CaptchaUsernamePasswordToken token = this.createToken(request, response);
        try {
            if (!doCaptchaVerify(request, token)) {
                // 若验证码校验失败则拒绝访问, 不再校验帐户和密码
                request.setAttribute(getFailureKeyAttribute(), IncorrectCaptchaException.class.getName());
                return true;
            }
            Subject subject = getSubject(request, response);
            subject.login(token);
            subject.getSession().setAttribute(Constant.USER_SESSION_KEY, subject.getPrincipal());
            return onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(token, e, request, response);
        }
    }

    protected boolean doCaptchaVerify(ServletRequest request, CaptchaUsernamePasswordToken token) {
        try {
            boolean useCaptcha = PropertiesLoader.getBoolean(StringUtils.toCamelName(Config.USE_CAPTCHA.name()));
            String captcha = (String) WebUtils.toHttp(request).getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            return useCaptcha && captcha.equalsIgnoreCase(token.getCaptcha());
        } catch (Exception e) {
            LOGGER.warn("Verify captcha error: {}", e.getMessage());
            return false;
        }
    }

    protected String getCaptcha(ServletRequest request) {
        return WebUtils.getCleanParam(request, getCaptchaParam());
    }

    public String getCaptchaParam() {
        return captchaParam;
    }

    public void setCaptchaParam(String captchaParam) {
        this.captchaParam = captchaParam;
    }


}
