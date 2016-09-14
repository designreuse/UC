package com.yealink.uc.web.modules.staff.request;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class DeleteStaffRequest {
    @NotEmpty
    private List<Long> staffIds;

    public List<Long> getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(final List<Long> staffIds) {
        this.staffIds = staffIds;
    }
}
