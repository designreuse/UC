package com.yealink.uc.web.modules.login.service;

import javax.servlet.http.HttpServletRequest;

import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.platform.utils.SecurityUtil;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author ChNan
 */
@Service
public class CSRFTokenService {
    public void setCsrfToken(HttpServletRequest request, String username) {
        String tokenKey;
        if (StringUtils.hasText(username)) {
            tokenKey = request.getSession().getId() + request.getRemoteAddr();
        } else {
            tokenKey = username + request.getSession().getId() + request.getRemoteAddr();
        }
        String cSRFToken = SecurityUtil.createCsrfToken(tokenKey);
        SessionUtil.set(request, SessionConstant.CURRENT_CSRF_TOKEN, cSRFToken);
    }
}
