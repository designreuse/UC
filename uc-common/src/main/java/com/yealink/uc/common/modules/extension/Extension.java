package com.yealink.uc.common.modules.extension;

import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 * 话机信息
 */
@Entity(name = "Extension")
public class Extension {
    private Long _id;

    private Long staffId;

    private String number;

    private String password;

    private String pin;

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

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(final String pin) {
        this.pin = pin;
    }
}
