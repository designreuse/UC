package com.yealink.imweb.modules.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yl1240 on 2016/7/15.
 */
public class ToolUtil {

    public static String[] string2Array(String srcStr,String separate ){
        return srcStr.split(separate);
    }
    public static String array2String(String[] array,String separate ){
        String str = "";
        if(array != null && array.length > 0){
            for(String curStr : array){
                str = str + curStr + separate;
            }
            str = str.substring(0,str.length() - 1);
        }
        return str;
    }

    public static void main(String[] args){
/*        String[] array = new String[2];
        array[0] = "1";
        array[1] = "2";
        System.out.println(array2String(array,";"));*/
    }
}
