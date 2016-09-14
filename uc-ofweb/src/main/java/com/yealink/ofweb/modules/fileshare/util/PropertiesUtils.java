package com.yealink.ofweb.modules.fileshare.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * properties文件工具
 * author:pengzhiyuan
 * Created on:2016/7/28.
 */
public class PropertiesUtils {
    private Object propertiesObject=null;
    private Properties properties=null;
    private InputStream inputStream=null;

    /**
     * 获取Properties Object
     * @return Returns the propertiesObject.
     */
    public Object getPropertiesObject() {
        return propertiesObject;
    }

    /**
     * 设置Properties Object
     * @param propertiesObject The propertiesObject to set.
     */
    public void setPropertiesObject(Object propertiesObject) {
        this.propertiesObject = propertiesObject;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * 初始化
     */
    public void parse() {
        if(propertiesObject==null) {
            return;
        }
        properties=new Properties();
        if(propertiesObject instanceof String) {
            String filename=(String)propertiesObject;
            inputStream=fileToInputStream(filename);
            try {
                properties.load(inputStream);
            }catch(Exception e) {
                e.printStackTrace();
                close();
            }
        }else if(propertiesObject instanceof Properties) {
            this.properties=(Properties)propertiesObject;
        }else if(propertiesObject instanceof InputStream) {
            inputStream=(InputStream)propertiesObject;
            try {
                properties.load(inputStream);
            }catch(Exception e) {
                e.printStackTrace();
                close();
            }
        }
    }

    /**
     * 把文件读到InputStream中
     * @param filename
     * @return InputStream
     */
    private InputStream fileToInputStream(String filename){
        return getClass().getResourceAsStream(filename);
    }

    /**
     * 获取属性值
     * @param key
     * @return String
     */
    public String readValue(String key) {
        String value=null;
        value=properties.getProperty(key);
        return value;
    }

    /**
     * 获取属性值
     * @param key
     * @param defaultKey
     * @return String
     */
    public String readValue(String key,String defaultKey) {
        String value=null;
        value=properties.getProperty(key,defaultKey);
        return value;
    }

    /**
     * 获取属性值Map
     * @return Map
     */
    public Map getKeyValueMap(){
        Map keyValueMap=new HashMap();
        Enumeration enumeration=properties.propertyNames();
        if(enumeration!=null){
            while(enumeration.hasMoreElements()){
                String parameterName=getString((String)enumeration.nextElement());
                String parameterValue=readValue(parameterName);
                keyValueMap.put(parameterName, parameterValue);
            }
        }
        return keyValueMap;
    }

    public static String getString(Object str) {
        if (str == null) {
            return "";
        }
        return String.valueOf(str).trim();
    }

    /**
     * 将路径中的"\"全部替换成"/"
     *
     * @param path
     * @return String
     */
    public static String getFormatPath(String path) {
        path = path.replaceAll("\\\\", "/");
        path = path.replaceAll("//", "/");
        return path;
    }

    /**
     * 关闭资源
     *
     */
    public void close() {
        if(properties!=null) {
            properties.clear();
        }
        if(inputStream!=null) {
            try {
                inputStream.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对UTF-8进行中文解码
     * @param string
     * @return String
     */
    public static String getStringFromUtf8(String string){
        try {
            string= URLDecoder.decode(string,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace() ;
        }
        return string;
    }

    /**
     * 对中文进行UTF-8编码
     * @param string
     * @return String
     */
    public static String getUtf8String(String string){
        try {
            string= URLEncoder.encode(string, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return string;
    }

}
