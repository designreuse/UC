/**
 * @(#)BaseControllerImpl  1.0
 *
 * Copyright 2014 by Yealink Corporation.
 * 4th-5th Floor, South Building, No.63 Wanghai Road,
 * 2nd Software Park, Xiamen, China.
 *
 */

package com.yealink.ofweb.modules.base;


import java.util.Calendar;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;



/**
 * 控制层基类
 * 
 * @author yl0164
 * 
 */
public class BaseController {

	private static final String HEADER_MENU = "headerMenu";
	private static final String LEFT_MENU = "leftMenu";
	private static final Locale LOCALE_RU = new Locale("ru");
	private static final Locale LOCALE_PT = new Locale("pt");
	private static final Locale LOCALE_ES = new Locale("es");
	private static final Locale LOCALE_IT = new Locale("it");
	protected static final Locale LOCALE_ZH_CN = new Locale("zh", "CN");
	private static final Locale LOCALE_EN = new Locale("en");

	protected static final String SESSION_ATTR_LANGUAGE = "language";
	private static final String SESSION_ATTR_COPYRIGHT = "copyright";

	/**
	 * 根据传入的异常编码获取对应的异常提示消息
	 *
	 * @return
	 */
	protected String getMessage(String key) {
		return Message.getMessage(key);
	}

	/**
	 * 获取会话的Locale，从Session的SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME<br/>
	 * 属性中获取，如果没有则使用request中获取Locale，如果浏览器的语言系统不支持，则默认使用英文.
	 * 
	 * @param request
	 * @return
	 */
	protected Locale getSessionLocale(HttpServletRequest request) {
		// 还未登录时取request的语言
		if (request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) == null) {
			Locale requestLocale = request.getLocale();
			return getSupportLocale(requestLocale.getLanguage());
		}
		return (Locale) request.getSession().getAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
	}

	/**
	 * 根据会话的locale获取对应key的国际化资源.
	 * 
	 * @param key
	 *            消息的国际化Key
	 * @param request
	 *            the request
	 * @return the message
	 */
	protected String getMessage(String key, HttpServletRequest request) {
		return Message.getMessage(key, getSessionLocale(request));
	}

	/**
	 * 根据会议的Locale获取对应的Key国际化资源.
	 * 
	 * @param key
	 *            消息国际化的Key
	 * @param args
	 *            参数
	 * @param request
	 *            HTTP请求
	 * @return the message
	 */
	protected String getMessage(String key, Object[] args,
			HttpServletRequest request) {
		return Message.getMessage(key, args, getSessionLocale(request));
	}


	/**
	 * 设置会话的Locale及版权信息，将版权信息放入到session中，将Locale放入Session中，属性：
	 * SessionLocaleResolver. LOCALE_SESSION_ATTRIBUTE_NAME，Key为Locale<br/>
	 * 属性：language, Key:Locale的language属性
	 * 
	 * @param request
	 *            请求
	 * @param locale
	 *            Locale对象
	 */
	protected void setSessionLocaleAndCopyright(HttpServletRequest request,
			Locale locale) {
		request.getSession().setAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		request.getSession().removeAttribute(SESSION_ATTR_LANGUAGE);
		request.getSession().setAttribute(SESSION_ATTR_LANGUAGE,
				locale.toString());
		setCopyright(request);
	}

	/**
	 * 设置会话的Locale及版权信息.<br>
	 * 从request中获取Locale信息设置到session中.<br>
	 * 属性： SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME，Key为Locale<br/>
	 * 属性：language, Key:Locale的language属性
	 * 
	 * @param request
	 *            请求
	 */
	protected void setSessionLocaleAndCopyright(HttpServletRequest request) {
		Locale locale = getSessionLocale(request);
		request.getSession().setAttribute(
				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		request.getSession().removeAttribute(SESSION_ATTR_LANGUAGE);
		request.getSession().setAttribute(SESSION_ATTR_LANGUAGE,
				locale.toString());
		setCopyright(request);
	}

	/**
	 * 将版权信息放入到session中.
	 * 
	 * @param request
	 */
	private void setCopyright(HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		String year = Integer.toString(cal.get(Calendar.YEAR));
		String copyright = getMessage("system.common.title.copyright",
				new String[] { year }, request);
		request.getSession().setAttribute(SESSION_ATTR_COPYRIGHT, copyright);
	}

	/**
	 * 获取系统支持的语言，如果语言的参数为空或者空内容，则返回英文<br/>
	 * 如果是包含zh，则使用中文简体<br/>
	 * 如果包含IT，则使用意大利语<br/>
	 * 如果包含ES，则使用西班牙语<br/>
	 * 如果包含PT，则使用葡萄牙语<br/>
	 * 如果包含RU，则使用俄语 .
	 * 
	 * @param language
	 *            语言参数，例如zh_cn
	 * @return 系统支持的语言Locale
	 */
	protected Locale getSupportLocale(String language) {
		if (language == null || language.isEmpty()) {
			return LOCALE_EN;
		}
		if (language.toUpperCase().indexOf("ZH") >= 0) {
			return LOCALE_ZH_CN;
		} else if (language.toUpperCase().indexOf("IT") >= 0) {
			return LOCALE_IT;
		} else if (language.toUpperCase().indexOf("ES") >= 0) {
			return LOCALE_ES;
		} else if (language.toUpperCase().indexOf("PT") >= 0) {
			return LOCALE_PT;
		} else if (language.toUpperCase().indexOf("RU") >= 0) {
			return LOCALE_RU;
		} else {
			return LOCALE_EN;
		}
	}

}
