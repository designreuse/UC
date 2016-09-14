package com.yealink.uc.web.modules.staff.request;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class CreateStaffRequest extends StaffCommonRequest {
    @NotEmpty
    private List<Long> orgMappings;

    public List<Long> getOrgMappings() {
        return orgMappings;
    }

    public void setOrgMappings(List<Long> orgMappings) {
        this.orgMappings = orgMappings;
    }

}
