package com.yealink.uc.web.modules.staff.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author yewl
 */
public class ReindexStaffRequest {
    @NotEmpty
    private List<Long> staffIdsByAsc;

    @NotNull
    @Min(1)
    private Long orgId;

    public List<Long> getStaffIdsByAsc() {
        return staffIdsByAsc;
    }

    public void setStaffIdsByAsc(List<Long> staffIdsByAsc) {
        this.staffIdsByAsc = staffIdsByAsc;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
}
