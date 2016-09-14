package com.yealink.uc.api.modules.account.vo;

/**
 * @author ChNan
 */
public class StaffDetail {
    private Long staffId;
    private Long enterpriseId;
    public StaffDetail() {
    }

    public StaffDetail(final Long staffId, final Long enterpriseId) {
        this.staffId = staffId;
        this.enterpriseId = enterpriseId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(final Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }
}
