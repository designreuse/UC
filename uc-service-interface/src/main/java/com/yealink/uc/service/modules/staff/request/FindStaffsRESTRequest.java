package com.yealink.uc.service.modules.staff.request;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class FindStaffsRESTRequest {
    @NotEmpty(message = "{staff.validator.ids.null}")
    private List<Long> staffIds;

    public List<Long> getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(final List<Long> staffIds) {
        this.staffIds = staffIds;
    }
}
