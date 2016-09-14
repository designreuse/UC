package com.yealink.uc.web.modules.staff.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author yewl
 */
public class ReleaseOrgMappingRequest {
    @NotNull
    @Min(value = 1)
    private Long staffId;

    @NotNull
    @Min(value = 1)
    private Long orgId;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
