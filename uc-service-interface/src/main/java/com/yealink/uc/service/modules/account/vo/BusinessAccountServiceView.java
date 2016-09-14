package com.yealink.uc.service.modules.account.vo;

/**
 * @author ChNan
 */
public class BusinessAccountServiceView {
    private Long _id;

    private Long staffId;

    private String username;

    private String password;

    private String businessType;

    private String businessServiceUrl;

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


    public String getBusinessServiceUrl() {
        return businessServiceUrl;
    }

    public void setBusinessServiceUrl(final String businessServiceUrl) {
        this.businessServiceUrl = businessServiceUrl;
    }

}
