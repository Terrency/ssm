package com.ssm.common.web.servlet.interceptor;

import com.ssm.common.subject.ActiveUser;
import com.ssm.common.subject.Permission;
import com.ssm.common.util.ResourcesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 授权拦截器
 */
public class PermissionInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionInterceptor.class);

    /**
     * 进入handler方法之前执行
     * 应用场景：用户身份认证、权限校验
     * 返回值：true(放行) false(拦截)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String url = request.getRequestURI();
        // 判断是否为匿名访问地址(不用登录即可访问)
        List<String> anonymousURLList = ResourcesUtils.getKeys("anonymousURL");
        for (String anonymousURL : anonymousURLList) {
            if (url.contains(anonymousURL)) {
                return true;
            }
        }
        // 判断是否为公共访问地址(登录后均可访问)
        List<String> commonURLList = ResourcesUtils.getKeys("commonURL");
        for (String commonURL : commonURLList) {
            if (url.contains(commonURL)) {
                return true;
            }
        }
        // 权限校验
        ActiveUser activeUser = (ActiveUser) request.getSession().getAttribute("activeUser");
        for (Permission permission : activeUser.getFunctions()) {
            if (url.contains(permission.getUrl())) {
                return true;
            }
        }
        request.getRequestDispatcher("/error/authzRefuse.jsp").forward(request, response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        LOGGER.info("PermissionInterceptor.postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        LOGGER.info("PermissionInterceptor.afterCompletion");
    }
}
