package com.yealink.ofweb.modules.fileshare.entity;

import com.yealink.uc.platform.annotations.Entity;

/**
 * 服务器监控设置
 * author:pengzhiyuan
 * Created on:2016/7/27.
 */
@Entity(name="ylFileServerMonitorSet")
public class FileServerMonitorSet {
    private String _id;
    /**
     * 服务器JID
     */
    private String jid;
    /**
     * 监控项
     */
    private String item;
    /**
     * 启动标志
     */
    private String startFlag;
    /**
     * 监控时间长度
     */
    private int monitorTime;
    /**
     * 监控阈值
     */
    private float threshold;
    /**
     * 报警方式
     */
    private String alarmWay;
    /**
     * 创建时间
     */
    private String createDate;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(String startFlag) {
        this.startFlag = startFlag;
    }

    public int getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(int monitorTime) {
        this.monitorTime = monitorTime;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public String getAlarmWay() {
        return alarmWay;
    }

    public void setAlarmWay(String alarmWay) {
        this.alarmWay = alarmWay;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
