package com.yealink.uc.api.modules.account.vo;

/**
 * @author ChNan
 */
public class BusinessAccountService {
    private Long _id;

    private String username;

    private String password;

    private String businessType;

    private String businessServiceUrl;

    private String randomAESEncryptKey;

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

    public String getRandomAESEncryptKey() {
        return randomAESEncryptKey;
    }

    public void setRandomAESEncryptKey(final String randomAESEncryptKey) {
        this.randomAESEncryptKey = randomAESEncryptKey;
    }
}
