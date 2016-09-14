package com.yealink.uc.web.modules.staff.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class MoveStaffRequest {
    @NotNull
    @Min(1)
    private Long staffId;

    @NotEmpty
    private List<Long> targetOrgIds;


    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }

    public List<Long> getTargetOrgIds() {
        return targetOrgIds;
    }

    public void setTargetOrgIds(List<Long> targetOrgIds) {
        this.targetOrgIds = targetOrgIds;
    }
}
