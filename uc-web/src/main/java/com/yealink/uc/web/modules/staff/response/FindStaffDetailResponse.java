package com.yealink.uc.web.modules.staff.response;

import com.yealink.uc.common.modules.phone.Phone;
import com.yealink.uc.common.modules.staff.entity.Staff;

/**
 * @author ChNan
 */
public class FindStaffDetailResponse {
    UCAccountDetail ucAccount;
    Staff staff;
    Phone phone;
    StaffExtension extension;

    public FindStaffDetailResponse(final UCAccountDetail ucAccount, final Staff staff, final Phone phone, final StaffExtension extension) {
        this.ucAccount = ucAccount;
        this.staff = staff;
        this.phone = phone;
        this.extension = extension;
    }

    public UCAccountDetail getUcAccount() {
        return ucAccount;
    }

    public void setUcAccount(final UCAccountDetail ucAccount) {
        this.ucAccount = ucAccount;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(final Staff staff) {
        this.staff = staff;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(final Phone phone) {
        this.phone = phone;
    }

    public StaffExtension getExtension() {
        return extension;
    }

    public void setExtension(final StaffExtension extension) {
        this.extension = extension;
    }
}
