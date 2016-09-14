package com.yealink.uc.platform.utils;

import java.util.Locale;

/**
 * @author ChNan
 */
public class LocaleUtil {
    public static final Locale LOCALE_RU = new Locale("ru");
    public static final Locale LOCALE_PT = new Locale("pt");
    public static final Locale LOCALE_ES = new Locale("es");
    public static final Locale LOCALE_IT = new Locale("it");
    public static final Locale LOCALE_EN = new Locale("en");
    public static final Locale LOCALE_ZH_CN = new Locale("zh", "CN");

    public static Locale getSupportLocale(String language) {
        if (language == null || language.isEmpty()) {
            return LOCALE_EN;
        }
        if (language.toUpperCase().contains("ZH")) {
            return LOCALE_ZH_CN;
        } else if (language.toUpperCase().contains("IT")) {
            return LOCALE_IT;
        } else if (language.toUpperCase().contains("ES")) {
            return LOCALE_ES;
        } else if (language.toUpperCase().contains("PT")) {
            return LOCALE_PT;
        } else if (language.toUpperCase().contains("RU")) {
            return LOCALE_RU;
        } else {
            return LOCALE_EN;
        }
    }

}
