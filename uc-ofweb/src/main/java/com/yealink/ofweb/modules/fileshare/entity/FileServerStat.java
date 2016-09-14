package com.yealink.ofweb.modules.fileshare.entity;

import com.yealink.uc.platform.annotations.Entity;

/**
 * 文件服务器状态
 * author:pengzhiyuan
 * Created on:2016/7/27.
 */
@Entity(name="ylFileServerStat")
public class FileServerStat {

    private String _id;
    /**
     * 文件服务器JID
     */
    private String jid;
    /**
     * cpu使用率
     */
    private String cpuRate;
    /**
     * 内存使用率
     */
    private String memRate;
    /**
     * io使用率
     */
    private String ioRate;
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

    public String getMemRate() {
        return memRate;
    }

    public void setMemRate(String memRate) {
        this.memRate = memRate;
    }

    public String getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(String cpuRate) {
        this.cpuRate = cpuRate;
    }

    public String getIoRate() {
        return ioRate;
    }

    public void setIoRate(String ioRate) {
        this.ioRate = ioRate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
