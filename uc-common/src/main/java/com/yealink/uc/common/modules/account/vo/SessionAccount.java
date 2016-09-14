package com.yealink.uc.common.modules.account.vo;

/**
 * @author ChNan
 */
public class SessionAccount {
    private Long _id;
    private String userName;
    private String name;
    private Long enterpriseId;

    public SessionAccount(Long _id, String userName, Long enterpriseId, String name) {
        this._id = _id;
        this.userName = userName;
        this.enterpriseId = enterpriseId;
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
    }
}
