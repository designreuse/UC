package com.yealink.imweb.modules.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yl1240 on 2016/8/9.
 */
public class DateUtil {
    public static String timeToStr(Long time,String formater){
        SimpleDateFormat sdf = new SimpleDateFormat(formater);
        String date = sdf.format(new Date(time));
        return date;
    }

    public static Date toDate(String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    public static Date currentDate() {
        return new Date();
    }

    public static String toString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static void main(String args[]){
        System.out.println(timeToStr((new Date()).getTime(),"yyyy-MM-dd'T'HH:mm:ssZ"));

    }
}
