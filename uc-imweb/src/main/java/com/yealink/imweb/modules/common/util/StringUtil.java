package com.yealink.imweb.modules.common.util;

/**
 * Created by yl1240 on 2016/8/22.
 */
public class StringUtil {
    //是否为空
    public static boolean isEmpty(String str){
        if("".equals(str) || str == null){
            return true;
        }
        return false;
    }
}
