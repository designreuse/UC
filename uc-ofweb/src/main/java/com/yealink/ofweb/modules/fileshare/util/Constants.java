package com.yealink.ofweb.modules.fileshare.util;

/**
 * 常量类
 * author:pengzhiyuan
 * Created on:2016/8/2.
 */
public class Constants {
    /**
     * 文件共享属性名称
     */
    /**
     * 离线文件存储时间
     */
    public static final String FILE_SHARE_PROPERTY_OFFLINE_SAVEDAYS="fileshare.offline.savedays";
    /**
     * 文件传输最大值
     */
    public static final String FILE_SHARE_PROPERTY_MAXSIZE="fileshare.file.maxsize";
    /**
     * 头像支持的图片类型
     */
    public static final String FILE_SHARE_PROPERTY_FILETYPE="fileshare.avatar.filetype";

    // 成功失败标志
    public static final String SUCCESS_CODE="1";
    public static final String FAIL_CODE="0";

    /**
     * 通知删除文件
     */
    public static final String OPT_DELETE_OFFLINEFILE = "delOffline";

    /**
     * 文件类别
     */
    public static final String FILE_TYPE_ONLINE = "online";
    public static final String FILE_TYPE_OFFLINE = "offline";
    public static final String FILE_TYPE_GROUP = "group";
    public static final String FILE_TYPE_CHAT = "chat";
    public static final String FILE_TYPE_AVATAR = "avatar";

    // 传输状态
    /**
     * offline file receiveFlag - 0 no receive
     */
    public static final String FILE_RECEIVE_FLAG_NO = "0";
    /**
     * offline file receiveFlag- 1 has received
     */
    public static final String FILE_RECEIVE_FLAG_YES = "1";
    /**
     * abort
     */
    public static final String FILE_RECEIVE_FLAG_ABORT = "2";
    /**
     * 发送失败
     */
    public static final String FILE_RECEIVE_FLAG_FAIL = "3";
    /**
     * 拒绝接收
     */
    public static final String FILE_RECEIVE_FLAG_NOTACCEPTABLE = "4";
}
