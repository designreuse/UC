package com.yealink.imweb.modules.common.interceptor.vo;

/**
 * Created by yl1240 on 2016/8/12.
 */
public class AuthResult {
    int ret;
    String msg;
    UserInfo userInfo;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
