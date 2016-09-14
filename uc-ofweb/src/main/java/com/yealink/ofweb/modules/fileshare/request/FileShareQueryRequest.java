package com.yealink.ofweb.modules.fileshare.request;

/**
 * 文件共享查询
 * Created by pzy on 2016/8/3.
 */
public class FileShareQueryRequest<T> extends PageRequest<T> {
    private String userName;

    private String isUsing;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(String isUsing) {
        this.isUsing = isUsing;
    }
}
