package com.yealink.uc.common.modules.staffrolemapping;

import com.yealink.uc.platform.annotations.Entity;

@Entity(name = "StaffRoleMapping")
public class StaffRoleMapping {
    private Long _id;
    private Long roleId;
    private Long staffId;

    public StaffRoleMapping(final Long roleId, final Long staffId) {
        this.roleId = roleId;
        this.staffId = staffId;
    }

    public StaffRoleMapping() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }
}