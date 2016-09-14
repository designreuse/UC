package com.yealink.uc.service.modules.staff.response;

import java.util.List;

import com.yealink.uc.service.modules.staff.vo.StaffView;

/**
 * @author ChNan
 */
public class FindStaffsRESTResponse {
    List<StaffView> staffs;

    public List<StaffView> getStaffs() {
        return staffs;
    }

    public void setStaffs(final List<StaffView> staffs) {
        this.staffs = staffs;
    }

    public FindStaffsRESTResponse(final List<StaffView> staffs) {
        this.staffs = staffs;
    }
}
