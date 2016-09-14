package com.yealink.uc.web.modules.staff.vo;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.businessaccount.entity.BusinessAccount;
import com.yealink.uc.common.modules.phone.Phone;
import com.yealink.uc.common.modules.staff.entity.Staff;

/**
 * @author ChNan
 */
public class StaffDetail {
    BusinessAccount imAccount;
    BusinessAccount extensionAccount;
    Account ucAccount;
    Staff staff;
    Phone phone;

    public StaffDetail() {
    }

    public StaffDetail(final Account ucAccount, final Staff staff, final BusinessAccount extensionAccount, final Phone phone) {
        this.ucAccount = ucAccount;
        this.staff = staff;
        this.extensionAccount = extensionAccount;
        this.phone = phone;
    }

    public BusinessAccount getExtensionAccount() {
        return extensionAccount;
    }

    public void setExtensionAccount(final BusinessAccount extensionAccount) {
        this.extensionAccount = extensionAccount;
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

    public BusinessAccount getImAccount() {

        return imAccount;
    }

    public void setImAccount(final BusinessAccount imAccount) {
        this.imAccount = imAccount;
    }

    public Account getUcAccount() {
        return ucAccount;
    }

    public void setUcAccount(final Account ucAccount) {
        this.ucAccount = ucAccount;
    }
}
