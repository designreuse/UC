package com.yealink.uc.api.modules.login.response;

import java.util.List;

import com.yealink.uc.api.modules.account.vo.BusinessAccountService;
import com.yealink.uc.api.modules.account.vo.StaffDetail;
import com.yealink.uc.api.modules.account.vo.UCAccount;

/**
 * @author ChNan
 */
public class LoginAPIResponse {
    private List<BusinessAccountService> businessAccountServices;

    private UCAccount ucAccount;

    private StaffDetail staff;

    public UCAccount getUcAccount() {
        return ucAccount;
    }

    public void setUcAccount(final UCAccount ucAccount) {
        this.ucAccount = ucAccount;
    }

    public List<BusinessAccountService> getBusinessAccountServices() {
        return businessAccountServices;
    }

    public void setBusinessAccountServices(final List<BusinessAccountService> businessAccountServices) {
        this.businessAccountServices = businessAccountServices;
    }

    public StaffDetail getStaff() {
        return staff;
    }

    public void setStaff(final StaffDetail staff) {
        this.staff = staff;
    }
}
