package com.yealink.uc.platform.interceptor;

import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yealink.uc.platform.log.track.TrackLogger;
import com.yealink.uc.platform.utils.ClassUtil;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author ChNan
 */
public class RequestContextInterceptor extends HandlerInterceptorAdapter {
    TrackLogger trackLogger = TrackLogger.get();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        assignAction(handler);
        assignRequestId(request);
        return true;
    }

    private void assignRequestId(final HttpServletRequest request) {
        trackLogger.requestId(getRequestId(request));
    }

    private String getRequestId(final HttpServletRequest request) {
        String requestId = request.getHeader("REQUEST_ID");
        if (StringUtils.hasText(requestId)) {
            return requestId;
        }
        return UUID.randomUUID().toString();
    }

    private void assignAction(Object handler) {
        String classMethod = getClassMethod(handler);
        trackLogger.action(classMethod);
    }

    public String getClassMethod(Object handler) {
        return String.format("%s-%s", ClassUtil.getSimpleClassName(((HandlerMethod) handler).getBean()),
            ((HandlerMethod) handler).getMethod().getName());
    }
}
