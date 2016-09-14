package com.yealink.uc.common.modules.account.entity;


import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 *         UC 账号实体
 */
@Entity(name = "Account")
public class Account {
    private Long _id;
    private String username;
    private String password;
    private String salt;
    private Long enterpriseId;
    private String activeCode;
    private String name;
    private Long activeCodeExpiredDate;
    private int status;
    private String type;
    private String domain;
    private Long staffId;
    private Integer pinCode;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(final String activeCode) {
        this.activeCode = activeCode;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(final Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }


    public Long getActiveCodeExpiredDate() {
        return activeCodeExpiredDate;
    }

    public void setActiveCodeExpiredDate(final Long activeCodeExpiredDate) {
        this.activeCodeExpiredDate = activeCodeExpiredDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(final Integer pinCode) {
        this.pinCode = pinCode;
    }
}

