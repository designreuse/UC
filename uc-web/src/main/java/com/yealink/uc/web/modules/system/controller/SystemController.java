package com.yealink.uc.web.modules.system.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.LocaleUtil;
import com.yealink.uc.web.modules.system.request.LocaleChangeRequest;
import com.yealink.uc.web.modules.system.service.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ChNan
 */
@Controller
public class SystemController {
    @Autowired
    SystemService systemService;

    @RequestMapping(value = "/system/locale/change", method = RequestMethod.POST)
    @ResponseBody
    public Response selectLanguage(HttpServletRequest request, @Valid LocaleChangeRequest localeChangeRequest) {
        Locale locale = LocaleUtil.getSupportLocale(localeChangeRequest.getLanguage());
        systemService.setSessionLocale(request, locale);
        return Response.buildResponse(true, "");
    }
}
