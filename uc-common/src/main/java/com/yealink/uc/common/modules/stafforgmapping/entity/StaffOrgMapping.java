package com.yealink.uc.common.modules.stafforgmapping.entity;

import com.yealink.uc.platform.annotations.Entity;

@Entity(name = "StaffOrgMapping")
public class StaffOrgMapping {
    private String _id;
    private Long orgId;
    private Long staffId;
    private Integer staffIndexInOrg;
    private String title;

    public StaffOrgMapping() {
    }

    public StaffOrgMapping(Long staffId, Long orgId, Integer staffIndexInOrg) {
        this._id = orgId + "_" + staffId;
        this.orgId = orgId;
        this.staffIndexInOrg = staffIndexInOrg;
        this.staffId = staffId;
    }

    public Integer getStaffIndexInOrg() {
        return staffIndexInOrg;
    }

    public void setStaffIndexInOrg(Integer staffIndexInOrg) {
        this.staffIndexInOrg = staffIndexInOrg;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(final String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}