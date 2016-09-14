package com.yealink.uc.web.modules.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yealink.uc.platform.utils.ClassUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.web.modules.common.annotation.CaptchaRequired;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.web.modules.common.constant.SessionConstant;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CaptchaRequiredInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            CaptchaRequired captchaRequired = ClassUtil.findAnnotation((HandlerMethod) handler, CaptchaRequired.class);
            if (captchaRequired == null) return true;
            Integer captchaErrorCount = SessionUtil.getMayNull(request, SessionConstant.CURRENT_LOGIN_ERROR_COUNT);
            int allowErrorCount = captchaRequired.allowErrorCount();
            if (captchaErrorCount != null && captchaErrorCount >= allowErrorCount) {
                String randCaptcha = SessionUtil.get(request, SessionConstant.CURRENT_CAPTCHA);
                String inputCaptcha = request.getParameter("captcha");
                if (checkCaptchaExpired(randCaptcha)) {
                    throw new BusinessHandleException("login.tips.captcha.out.date");
                }
                if (checkInputCaptchaMissing(inputCaptcha)) {
                    throw new BusinessHandleException("login.tips.captcha.missing");
                }
                if (checkCaptchaError(randCaptcha, inputCaptcha)) {
                    throw new BusinessHandleException("login.tips.auth.error.captcha");
                }
            }
        }
        return true;
    }

    private boolean checkCaptchaExpired(String randCaptcha) {
        return randCaptcha == null;
    }

    private boolean checkInputCaptchaMissing(String inputCaptcha) {
        return inputCaptcha == null;
    }

    private boolean checkCaptchaError(String randCaptcha, String inputCaptcha) {
        return !inputCaptcha.equalsIgnoreCase(randCaptcha);
    }


}
