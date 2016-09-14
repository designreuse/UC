package com.yealink.ofweb.modules.msg.vo;

/**
 * 消息属性
 * author:pengzhiyuan
 * Created on:2016/8/8.
 */
public class MsgProperty {
    /**
     * 消息撤回有效时间
     */
    private String revertMsgValidTimes;
    /**
     * 消息存储时间
     */
    private String msgSaveDays;
    /**
     * 最近联系人最大个数
     */
    private String recentMaxNumbers;

    public String getRevertMsgValidTimes() {
        return revertMsgValidTimes;
    }

    public void setRevertMsgValidTimes(String revertMsgValidTimes) {
        this.revertMsgValidTimes = revertMsgValidTimes;
    }

    public String getMsgSaveDays() {
        return msgSaveDays;
    }

    public void setMsgSaveDays(String msgSaveDays) {
        this.msgSaveDays = msgSaveDays;
    }

    public String getRecentMaxNumbers() {
        return recentMaxNumbers;
    }

    public void setRecentMaxNumbers(String recentMaxNumbers) {
        this.recentMaxNumbers = recentMaxNumbers;
    }
}
