package com.yealink.uc.common.modules.businessaccount.entity;

import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 * 业务账号实体
 */
@Entity(name = "BusinessAccount")
public class BusinessAccount {
    private Long _id;

    private Long staffId;

    private String username;

    private String encryptedPassword;

    private String businessType;

    private Integer pinCode;

    private  boolean status;

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(final String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(final String businessType) {
        this.businessType = businessType;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(final Integer pinCode) {
        this.pinCode = pinCode;
    }
}
