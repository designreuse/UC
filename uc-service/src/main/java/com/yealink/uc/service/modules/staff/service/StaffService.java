package com.yealink.uc.service.modules.staff.service;

import java.util.List;

import com.yealink.uc.common.modules.staff.entity.Staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */

@Service
public class StaffService {
    @Autowired
    QueryStaffService queryStaffService;

    public List<Staff> findAllAvailable(Long enterpriseId) {
        return queryStaffService.findAllAvailableStaffs(enterpriseId);
    }

    public Staff getStaff(Long staffId) {
        return queryStaffService.getStaff(staffId);
    }

    public List<Staff> findStaffs(List<Long> staffIds) {
        return queryStaffService.findStaffs(staffIds);
    }
}
