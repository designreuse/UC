package com.yealink.ofweb.modules.fileshare.request;

/**
 * 文件属性设置请求
 * author:pengzhiyuan
 * Created on:2016/7/27.
 */
public class FileSharePropertyRequest {
    private String offlineSaveDays;

    private String fileMaxSize;

    private String avatarFileTypes;

    public String getOfflineSaveDays() {
        return offlineSaveDays;
    }

    public void setOfflineSaveDays(String offlineSaveDays) {
        this.offlineSaveDays = offlineSaveDays;
    }

    public String getFileMaxSize() {
        return fileMaxSize;
    }

    public void setFileMaxSize(String fileMaxSize) {
        this.fileMaxSize = fileMaxSize;
    }

    public String getAvatarFileTypes() {
        return avatarFileTypes;
    }

    public void setAvatarFileTypes(String avatarFileTypes) {
        this.avatarFileTypes = avatarFileTypes;
    }
}
