package com.yealink.ofweb.modules.storage.entity;

/**
 * 文件存储设置
 * author:pengzhiyuan
 * Created on:2016/8/25.
 */
public class FileStorageSetting {
    /**
     * 文件存储路径
     */
    private String serverSavePath;
    /**
     * 是否删除离线文件
     */
    private boolean isDelOnlineFile;
    /**
     * 离线文件存储时间 单位:天
     */
    private String offlineSaveDays;
    /**
     * 是否删除消息
     */
    private boolean isDelMsg;
    /**
     * 消息存储天数，单位:天
     */
    private String msgSaveDays;

    public String getServerSavePath() {
        return serverSavePath;
    }

    public void setServerSavePath(String serverSavePath) {
        this.serverSavePath = serverSavePath;
    }

    public boolean getIsDelOnlineFile() {
        return isDelOnlineFile;
    }

    public void setDelOnlineFile(boolean delOnlineFile) {
        isDelOnlineFile = delOnlineFile;
    }

    public String getOfflineSaveDays() {
        return offlineSaveDays;
    }

    public void setOfflineSaveDays(String offlineSaveDays) {
        this.offlineSaveDays = offlineSaveDays;
    }

    public String getMsgSaveDays() {
        return msgSaveDays;
    }

    public void setMsgSaveDays(String msgSaveDays) {
        this.msgSaveDays = msgSaveDays;
    }

    public boolean getIsDelMsg() {
        return isDelMsg;
    }

    public void setDelMsg(boolean delMsg) {
        isDelMsg = delMsg;
    }
}
