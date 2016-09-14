package com.yealink.uc.common.modules.phone;

import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 * 话机信息
 */
@Entity(name = "Phone")
public class Phone {
    private Long _id;

    private Long staffId;

    private String mac;

    private String ip;

    private String model;

    private String settingTemplate;

    private boolean status;

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(final String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(final String ip) {
        this.ip = ip;
    }

    public String getModel() {
        return model;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public String getSettingTemplate() {
        return settingTemplate;
    }

    public void setSettingTemplate(final String settingTemplate) {
        this.settingTemplate = settingTemplate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }
}
