package com.yealink.ofweb.modules.common;

import com.yealink.uc.platform.constant.Constant;

/**
 * Created by chenkl on 2016/8/11.
 */
public class Constants {
    public static final String STR_PREFIX_OPERATION = "operation.";
    public static final String STRING_SHORT_SIGNATURE_END = "(..)";
    public static final String USER ="sessionAccount";

    /** === 属性设置 _id常量值 ===*/
    /**
     * 服务器存储位置
     */
    public static final String PROPERTY_STORAGE_SERVER_SAVEPATH = "uc.storage.save.path";
    /**
     * 离线文件存储时间 属性名称 单位天
     */
    public static final String PROPERTY_OFFLINE_SAVEDAYS="fileshare.offline.savedays";
    /**
     * 消息存储时间 属性名称 单位天
     */
    public static final String PROPERTY_MSG_SAVEDAYS = "msg.save.days";
    /**
     * 是否删除离线文件 0-不删除 1-删除
     */
    public static final String PROPERTY_IS_DELETEOFFLINE = "fileshare.is.delofflinefile";
    /**
     * 是否删除消息 0-不删除 1-删除
     */
    public static final String PROPERTY_IS_DELMSG = "msg.is.delmsg";
    /**
     * linux用户名
     */
    public static final String PROPERTY_LINUX_USERNAME = "linuxUserName";
    /**
     * linux密码
     */
    public static final String PROPERTY_LINUX_PASSWORD = "linuxPassword";

    /**
     * 文件存储设置默认值
     */
    public static final String DEFAULT_SERVER_SAVE_PATH = "/usr/local/FileServer/data";
    public static final String DEFAULT_MSG_SAVE_DAYS = "90";
    public static final String DEFAULT_OFFLINE_SAVE_DAYS = "15";

    /**
     * 服务名称
     */
    public static final String SERVICE_NAME_FS = "FS";
    public static final String SERVICE_NAME_IM = "IM";

    /**
     * 服务状态key
     */
    public static final String SERVICE_STATUS_FS = "fileServer";
    public static final String SERVICE_STATUS_IM = "openfire";

    // 跨context的 session attribute name
    public static final String CROSS_CONTEXT_SESSION_NAME = Constant.CURRENT_UC_SESSION;
    // 建个cookie name
    public static final String CROSS_CONTEXT_COOKIE_NAME = "cookieId";

}
