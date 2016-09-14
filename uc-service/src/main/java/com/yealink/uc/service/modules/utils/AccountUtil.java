package com.yealink.uc.service.modules.utils;

/**
 * @author ChNan
 */
public class AccountUtil {
    public static String createFullUserName(String username, String domain) {
        return username + "@" + domain;
    }
}
