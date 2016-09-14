package com.yealink.uc.web.modules.login.service;

import java.util.Calendar;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;

import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class LoginSessionService {


    public HttpSession clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return request.getSession(true);
    }

    public void clearCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Cookie cookie = cookies[0];
            cookie.setMaxAge(0);
        }
    }



    public void setCopyright(HttpServletRequest request) {
        String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        String copyright = MessageUtil.getMessage("system.common.title.copyright", year);
        SessionUtil.set(request, SessionConstant.CURRENT_SESSION_ATTR_COPYRIGHT, copyright);
    }

    public void setLoginErrorCount(HttpServletRequest request) {
        Integer errorCount = SessionUtil.getMayNull(request, SessionConstant.CURRENT_LOGIN_ERROR_COUNT);
        if (errorCount == null)
            SessionUtil.set(request, SessionConstant.CURRENT_LOGIN_ERROR_COUNT, 0);
    }

}
