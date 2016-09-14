package com.yealink.uc.platform.utils;

import com.yealink.uc.platform.constant.Constant;
import com.yealink.uc.platform.exception.SessionTimeoutException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * @author ChNan
 */
@SuppressWarnings("unchecked")
public class SessionUtil {
    public static <T> T get(HttpServletRequest request, String key) {
        T t = (T) request.getSession().getAttribute(key);
        if (t == null) {
            String cookieId = getCookieId(request);
            if (cookieId != null) {
                request.getServletContext().removeAttribute(Constant.CURRENT_UC_SESSION + ":" + cookieId); // for of web login check
            }
            throw new SessionTimeoutException("system.session.timeout");
        }

        return t;
    }

    public static <T> T getMayNull(HttpServletRequest request, String key) {
        return (T) request.getSession().getAttribute(key);
    }

    public static <T> void set(HttpServletRequest request, String key, T t) {
        request.getSession().setAttribute(key, t);

        String cookieId = getCookieId(request);
        if (cookieId != null) {
            request.getServletContext().setAttribute(Constant.CURRENT_UC_SESSION + ":" + cookieId, request.getSession()); // for of web login check
        }
    }

    private static String getCookieId(final HttpServletRequest request) {
        String cookieId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("cookieId")) {
                    cookieId = c.getValue();

                }
            }
        }
        return cookieId;
    }

    public static void remove(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);

        String cookieId;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("cookieId")) {
                    cookieId = c.getValue();
                    request.getServletContext().setAttribute(Constant.CURRENT_UC_SESSION + ":" + cookieId, request.getSession()); // for of web login check
                }
            }
        }
    }
}
