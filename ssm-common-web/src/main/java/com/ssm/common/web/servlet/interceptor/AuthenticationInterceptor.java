package com.ssm.common.web.servlet.interceptor;

import com.ssm.common.subject.ActiveUser;
import com.ssm.common.util.ResourcesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 认证拦截器
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    /**
     * 进入handler方法之前执行
     * 应用场景：用户身份认证、权限校验
     * 返回值：true(放行) false(拦截)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        // 判断是否为匿名访问地址(不用登录即可访问)
        List<String> anonymousURLList = ResourcesUtils.getKeys("anonymousURL");
        for (String anonymousURL : anonymousURLList) {
            if(url.contains(anonymousURL)) {
                return true;
            }
        }
        // 判断用户身份信息是否在session中存在
        ActiveUser activeUser = (ActiveUser) request.getSession().getAttribute("activeUser");
        if (activeUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/portal/login.jsp").forward(request, response);
            return false;
        }
        return true;
    }

    /**
     * 进入handler方法之后、返回modelAndView之前执行
     * 应用场景：统一指定公用的模型视图数据(如菜单导航)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.info("AuthenticationInterceptor.postHandle");
    }

    /**
     * handler方法执行完成之后执行
     * 应用场景：统一异常处理和日志处理
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.info("AuthenticationInterceptor.afterCompletion");
    }

}
