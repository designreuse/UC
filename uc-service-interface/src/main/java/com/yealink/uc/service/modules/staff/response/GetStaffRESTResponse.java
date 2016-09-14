package com.yealink.uc.service.modules.staff.response;

import com.yealink.uc.service.modules.staff.vo.StaffView;

/**
 * @author ChNan
 */
public class GetStaffRESTResponse {
    StaffView staff;

    public GetStaffRESTResponse() {
    }

    public GetStaffRESTResponse(final StaffView staff) {
        this.staff = staff;
    }

    public StaffView getStaff() {
        return staff;
    }

    public void setStaff(final StaffView staff) {
        this.staff = staff;
    }
}
