package com.yealink.uc.web.modules.staff.vo;

import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.phone.Phone;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.web.modules.staff.response.StaffExtension;

/**
 * @author ChNan
 */
public class StaffSearchInfo {
    private Staff staff;

    private Org org;

    private StaffExtension extension;

    private Phone phone;

    public StaffSearchInfo() {
    }

    public StaffSearchInfo(final Staff staff, final Org org, final StaffExtension extension) {
        this.staff = staff;
        this.org = org;
        this.extension = extension;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(final Staff staff) {
        this.staff = staff;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(final Org org) {
        this.org = org;
    }

    public StaffExtension getExtension() {
        return extension;
    }

    public void setExtension(final StaffExtension extension) {
        this.extension = extension;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(final Phone phone) {
        this.phone = phone;
    }
}
