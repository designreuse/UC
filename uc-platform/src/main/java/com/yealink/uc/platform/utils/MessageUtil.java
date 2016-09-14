package com.yealink.uc.platform.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

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
public class MessageUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageUtil.class);

    private static final String DEFAULT_RESOURCE_FILE = "/i18n/message";
    private static final Locale CURRENT_LOCALE = LocaleContextHolder.getLocale();

    private static Map<String, Map<String, Object>> BUNDLE_LOCAL_CACHE = new HashMap<>();

    public static String getMessage(String key) {
        return getMessage(DEFAULT_RESOURCE_FILE, key, CURRENT_LOCALE);
    }

    public static String getMessage(String key, Object... arguments) {
        return getMessage(DEFAULT_RESOURCE_FILE, key, CURRENT_LOCALE, arguments);
    }

    public static String getMessage(String key, Locale locale) {
        return getMessage(DEFAULT_RESOURCE_FILE, key, locale);
    }

    public static String getMessage(String key, Locale locale, Object... arguments) {
        return getMessage(DEFAULT_RESOURCE_FILE, key, locale, arguments);
    }

    private static String getMessage(String resource, String name, Locale locale) {
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

    private static String getMessage(String resource, String name, Locale locale,
                                     Object... arguments) {
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

    private static ResourceBundle getResourceBundle(String resource,
                                                    Locale locale) {
        Map<String, Object> bundleMap = getResourceBundleMap(resource, locale);
        if (bundleMap == null) {
            return null;
        }
        return (ResourceBundle) bundleMap.get("_RESOURCE_BUNDLE_");
    }

    private static Map<String, Object> getResourceBundleMap(String resource,
                                                            Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        String resourceCacheKey = resource + "_" + locale.toString();
        Map<String, Object> bundleMap = BUNDLE_LOCAL_CACHE.get(resourceCacheKey);

        if (bundleMap == null) {
            ResourceBundle bundle = getBaseResourceBundle(resource, locale);
            bundleMap = resourceBundleToMap(bundle);
            if (bundleMap != null) {
                BUNDLE_LOCAL_CACHE.put(resourceCacheKey, bundleMap);
            }
        }
        return bundleMap;
    }

    private static Map<String, Object> resourceBundleToMap(ResourceBundle bundle) {
        if (bundle == null) {
            return new HashMap<>();
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
            LOGGER.error("Could not find resource: " + resource + " for CURRENT_LOCALE "
                + locale.toString() + ": " + e.toString());
            return null;
        }
        if (bundle == null) {
            LOGGER.error("Could not find resource: " + resource + " for CURRENT_LOCALE "
                + locale.toString());
            return null;
        }
        return bundle;
    }

    public static String traceExceptionMessage(Throwable ex) {
        StringWriter writer = new StringWriter();
        ex.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    public static String createBindingErrorMessage(BindingResult result) {
        StringBuilder stringBuilder = new StringBuilder();
        if (result.getFieldErrorCount() > 0) {
            for (FieldError error : result.getFieldErrors()) {
                stringBuilder.append(error.getField()).append(' ').append(error.getDefaultMessage()).append(';');
            }
        }
        if (result.getGlobalErrorCount() > 0) {
            for (ObjectError error : result.getGlobalErrors()) {
                stringBuilder.append(error.getObjectName()).append(' ').append(error.getDefaultMessage()).append(';');
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
