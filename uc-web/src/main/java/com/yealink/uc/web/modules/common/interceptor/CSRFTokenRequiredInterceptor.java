package com.yealink.uc.web.modules.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yealink.uc.platform.utils.ClassUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.web.modules.common.annotation.CSRFTokenRequired;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.web.modules.common.constant.SessionConstant;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CSRFTokenRequiredInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            CSRFTokenRequired csrfTokenRequired = ClassUtil.findAnnotation((HandlerMethod) handler, CSRFTokenRequired.class);
            if (csrfTokenRequired == null) return true;
            if (!checkCSRFfToken(request)) {
                throw new BusinessHandleException("exception.token.out.date");
            }
        }
        return true;
    }

    private boolean checkCSRFfToken(HttpServletRequest request) {
        String cSRFTokenFromRequest = request.getParameter(SessionConstant.CURRENT_CSRF_TOKEN);
        String cSRFTokenSession = SessionUtil.getMayNull(request, SessionConstant.CURRENT_CSRF_TOKEN);
        if (!StringUtils.hasText(cSRFTokenFromRequest) || !StringUtils.hasText(cSRFTokenSession)) {
            return false;
        }
        return cSRFTokenFromRequest.equals(cSRFTokenSession);
    }

}
