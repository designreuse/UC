package com.yealink.uc.platform.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * @author ChNan
 */
public class MailUtil {
    private static final String REGULAR_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    public static boolean isRegular(String str, String regular) {
        if (StringUtils.hasText(str) && StringUtils.hasText(regular)) {
            Pattern pattern = Pattern.compile(regular.trim());
            Matcher matcher = pattern.matcher(str.trim());
            if (!matcher.find()) {
                return false;
            }
        }

        return true;
    }

    public static boolean isEmail(String email) {
        return isRegular(email, REGULAR_EMAIL);
    }
}
