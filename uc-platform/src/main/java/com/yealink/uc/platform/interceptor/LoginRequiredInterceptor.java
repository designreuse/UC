package com.yealink.uc.platform.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.platform.constant.Constant;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.ClassUtil;
import com.yealink.uc.platform.utils.SessionUtil;

import org.apache.commons.httpclient.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginRequiredInterceptor extends HandlerInterceptorAdapter {
    public static final String HEADER_REDIRECT_LOCATION = "location";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            LoginRequired loginRequired = ClassUtil.findAnnotation((HandlerMethod) handler, LoginRequired.class);
            if (loginRequired == null) return true;
            if (!loggedIn(request)) {
                redirectToLoginPage(request, response);
                return false;
            }
        }
        return true;
    }

    private boolean loggedIn(HttpServletRequest request) {
        return SessionUtil.get(request, Constant.CURRENT_SESSION_ACCOUNT) != null;
    }

    private void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response) {
        final String ajaxHeader = request.getHeader("x-requested-with");
        if (StringUtils.hasText(ajaxHeader) && "XMLHttpRequest".equalsIgnoreCase(ajaxHeader)) {
            throw new BusinessHandleException("login.error.need.relogin");
        } else {
            response.setStatus(HttpStatus.SC_MOVED_TEMPORARILY);
            response.setHeader(HEADER_REDIRECT_LOCATION, "/");
        }
    }
}
