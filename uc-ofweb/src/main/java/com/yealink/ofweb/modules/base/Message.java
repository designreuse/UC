package com.yealink.ofweb.modules.base;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 资源信息管理类。<br>
 * <p/>
 * 调用方式：<br/>
 * Message.getMessage(String key);<br/>
 * Message.getMessage(String key,String argument);<br/>
 * Message.getMessage(String key,Object[] arguments);<br/>
 * 
 * @author yjz
 */
public class Message {

	/**
	 * 资源配置文件放置在resources/message_zh_CN.properties.
	 */
	private static final String DEAULT_RESOURCE_FILE = "i18n/message";

	/**
	 * The bundle locale cache.
	 */
	private static Map<String, Map<String, Object>> BUNDLE_LOCAL_CACHE = new HashMap<String, Map<String, Object>>();

	/**
	 * 根据资源名称取得资源信息.
	 * 
	 * @param key
	 *            the key
	 * @return string
	 */
	public static String getMessage(String key) {
		return getMessage(DEAULT_RESOURCE_FILE, key, Locale.CHINA);
	}

	/**
	 * 根据资源名称及资源信息参数取得资源信息.
	 * 
	 * @param name
	 *            the name
	 * @param arguments
	 *            the arguments
	 * @return string
	 */
	public static String getMessage(String name, Object[] arguments) {
		return getMessage(DEAULT_RESOURCE_FILE, name, arguments);
	}

	/**
	 * 根据资源名称及资源信息参数取得资源信息.
	 * 
	 * @param name
	 *            the name
	 * @param argument
	 *            the argument
	 * @return string
	 */
	public static String getMessage(String name, String argument) {
		return getMessage(DEAULT_RESOURCE_FILE, name, new Object[] { argument });
	}

	/**
	 * 根据资源名称及locale取得资源信息.
	 * 
	 * @param key
	 *            the key
	 * @param locale
	 *            the locale
	 * @return string
	 */
	public static String getMessage(String key, Locale locale) {
		return getMessage(DEAULT_RESOURCE_FILE, key, locale);
	}

	/**
	 * 根据locale，信息资源文件取得信息，如果没有找到则返回name.
	 *
	 * @param resource
	 *            信息资源文件，如: exampleMessage
	 * @param name
	 *            信息key
	 * @param locale
	 *            如：zh_CN等对应的Locale
	 * @return 信息值
	 */
	public static String getMessage(String resource, String name, Locale locale) {
		if (resource == null || resource.isEmpty()) {
			return "";
		}
		if (name == null || name.isEmpty()) {
			return "";
		}

		ResourceBundle bundle = getResourceBundle(resource, locale);
		if (bundle == null) {
			return "";
		}

		String value = null;
		try {
			value = bundle.getString(name);
		} catch (Exception e) {

		}
		return value == null ? name : value.trim();
	}

	/**
	 * 取得<tt>ResourceBundle</tt>.
	 *
	 * @param resource
	 *            the resource
	 * @param locale
	 *            the locale
	 * @return the resource bundle
	 */
	private static ResourceBundle getResourceBundle(String resource,
			Locale locale) {
		Map<String, Object> bundleMap = getResourceBundleMap(resource, locale);
		if (bundleMap == null) {
			return null;
		}
		return (ResourceBundle) bundleMap.get("_RESOURCE_BUNDLE_");
	}

	/**
	 * 返回以Map形式保存的resources.
	 *
	 * @param resource
	 *            the resource
	 * @param locale
	 *            the locale
	 * @return the resource bundle map
	 */
	private static Map<String, Object> getResourceBundleMap(String resource,
			Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}

		String resourceCacheKey = resource + "_" + locale.toString();
		Map<String, Object> bundleMap = BUNDLE_LOCAL_CACHE
				.get(resourceCacheKey);

		if (bundleMap == null) {
			ResourceBundle bundle = getBaseResourceBundle(resource, locale);
			bundleMap = resourceBundleToMap(bundle);
			if (bundleMap != null) {
				BUNDLE_LOCAL_CACHE.put(resourceCacheKey, bundleMap);
			}
		}
		return bundleMap;
	}

	/**
	 * Resource bundle to map.
	 * 
	 * @param bundle
	 *            the bundle
	 * @return the map
	 */
	private static Map<String, Object> resourceBundleToMap(ResourceBundle bundle) {
		if (bundle == null) {
			return new HashMap<String, Object>();
		}
		// NOTE: this should return all keys, including keys from parent
		// ResourceBundles, if not then something else must be done here...
		Enumeration<String> keyNum = bundle.getKeys();
		Map<String, Object> resourceBundleMap = new HashMap<String, Object>();
		while (keyNum.hasMoreElements()) {
			String key = keyNum.nextElement();
			Object value = bundle.getObject(key);
			resourceBundleMap.put(key, value);
		}
		resourceBundleMap.put("_RESOURCE_BUNDLE_", bundle);
		return resourceBundleMap;
	}

	/**
	 * Gets the base resource bundle.
	 *
	 * @param resource
	 *            the resource
	 * @param locale
	 *            the locale
	 * @return the base resource bundle
	 */
	private static ResourceBundle getBaseResourceBundle(String resource,
			Locale locale) {
		if (resource == null || resource.length() <= 0) {
			return null;
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		ResourceBundle bundle = null;
		try {
			bundle = ResourceBundle.getBundle(resource, locale, loader);
		} catch (MissingResourceException e) {

			return null;
		}
		if (bundle == null) {
			return null;
		}
		return bundle;
	}

	/**
	 * 根据资源名称及locale取得资源信息.
	 * 
	 * @param key
	 *            the key
	 * @param arguments
	 *            the arguments
	 * @param locale
	 *            the locale
	 * @return string
	 */
	public static String getMessage(String key, Object[] arguments,
			Locale locale) {
		return getMessage(DEAULT_RESOURCE_FILE, key, arguments, locale);
	}

	/**
	 * 取得默认local的message.
	 * 
	 * @param resource
	 *            the resource
	 * @param name
	 *            the name
	 * @param arguments
	 *            the arguments
	 * @return String
	 */
	public static String getMessage(String resource, String name,
			Object[] arguments) {
		return getMessage(resource, name, arguments, Locale.CHINA);
	}

	/**
	 * 根据locale，信息资源文件取得信息.
	 * 
	 * @param resource
	 *            信息资源文件，如: exampleMessage
	 * @param name
	 *            信息key
	 * @param arguments
	 *            信息体包含的变量值
	 * @param locale
	 *            如：zh_CN等对应的Locale
	 * @return 信息值
	 */
	public static String getMessage(String resource, String name,
			Object[] arguments, Locale locale) {
		String value = getMessage(resource, name, locale);

		if (value == null || value.length() == 0) {
			return "";
		} else {
			if (arguments != null && arguments.length > 0) {
				value = MessageFormat.format(value, arguments);
			}
			return value;
		}
	}
}
