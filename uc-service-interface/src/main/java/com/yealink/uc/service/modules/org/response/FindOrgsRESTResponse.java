package com.yealink.uc.service.modules.org.response;

import java.util.List;

import com.yealink.uc.service.modules.org.vo.StaffOrgsMappingView;

/**
 * @author ChNan
 */
public class FindOrgsRESTResponse {
    List<StaffOrgsMappingView> staffOrgsMappings;

    public List<StaffOrgsMappingView> getStaffOrgsMappings() {
        return staffOrgsMappings;
    }

    public void setStaffOrgsMappings(final List<StaffOrgsMappingView> staffOrgsMappings) {
        this.staffOrgsMappings = staffOrgsMappings;
    }
}
