package com.yealink.uc.service.modules.account.vo;

/**
 * @author ChNan
 */
public class UCAccountView {
    private Long _id;
    private String username;
    private String name;
    private Long enterpriseId;
    private Long staffId;

    public UCAccountView() {
    }

    public UCAccountView(Long _id, String username, Long enterpriseId, String name) {
        this._id = _id;
        this.username = username;
        this.enterpriseId = enterpriseId;
        this.name = name;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
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

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
}
