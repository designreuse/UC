package com.yealink.uc.service.modules.org.vo;

import java.util.List;

import com.yealink.uc.service.modules.staff.vo.StaffView;

/**
 * @author ChNan
 */
public class StaffOrgsMappingView {
    StaffView staff;

    List<OrgView> orgs;

    public StaffView getStaff() {
        return staff;
    }

    public void setStaff(final StaffView staff) {
        this.staff = staff;
    }

    public List<OrgView> getOrgs() {
        return orgs;
    }

    public void setOrgs(final List<OrgView> orgs) {
        this.orgs = orgs;
    }
}
