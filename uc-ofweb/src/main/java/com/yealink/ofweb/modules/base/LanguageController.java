/**
 * 
 */
package com.yealink.ofweb.modules.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yl0167
 * 
 */
@Controller
public class LanguageController extends BaseController {
	@RequestMapping(value = "/set-language")
	@ResponseBody
	public String selectLanguage(HttpServletRequest request, String language) {
		setSessionLocaleAndCopyright(request, getSupportLocale(language));
		return null;
	}
}
