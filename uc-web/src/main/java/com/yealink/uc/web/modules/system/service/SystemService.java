package com.yealink.uc.web.modules.system.service;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;

import com.yealink.uc.platform.utils.LocaleUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.web.modules.common.constant.SessionConstant;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @author ChNan
 */
@Service
public class SystemService {
    public void setSessionLocale(HttpServletRequest request, Locale locale) {
        if (locale != null) {
            SessionUtil.set(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
            SessionUtil.remove(request, SessionConstant.CURRENT_SESSION_ATTR_LANGUAGE);
            SessionUtil.set(request, SessionConstant.CURRENT_SESSION_ATTR_LANGUAGE, locale.toString());
        }
    }

    public Locale getSessionLocale(HttpServletRequest request) {
        if (request.getSession().getAttribute( // 还未登录时取request的语言
            SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) == null) {
            Locale requestLocale = request.getLocale();
            return LocaleUtil.getSupportLocale(requestLocale.getLanguage());
        }
        return (Locale) request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
    }

}
