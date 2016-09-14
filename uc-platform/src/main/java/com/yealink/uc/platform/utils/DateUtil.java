package com.yealink.uc.platform.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ChNan
 */
public class DateUtil {
    public static String toString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date currentDate() {
        return new Date();
    }
}
