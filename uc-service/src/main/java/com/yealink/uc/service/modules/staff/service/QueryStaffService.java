package com.yealink.uc.service.modules.staff.service;

import java.util.Collections;
import java.util.List;

import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.service.modules.staff.dao.StaffDao;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yewl on 2016/6/20.
 */
@Service
public class QueryStaffService {
    @Autowired
    private StaffDao staffDao;

    public Staff getStaff(Long staffId, Long enterpriseId) {
        Staff staff = staffDao.get(staffId, enterpriseId);
        if (staff == null) {
            throw new BusinessHandleException("staff.exception.not.found", staffId);
        }
        return staff;
    }

    public Staff getStaff(Long staffId) {
        Staff staff = staffDao.get(staffId);
        if (staff == null) {
            throw new BusinessHandleException("staff.exception.not.found", staffId);
        }
        return staff;
    }

    public List<Staff> findAllAvailableStaffs(Long enterpriseId) {
        List<Staff> staffList = staffDao.findAllAvailable(enterpriseId);
        if (staffList == null || staffList.isEmpty()) {
            throw new BusinessHandleException("staff.exception.not.available.in.enterprise", enterpriseId);
        }
        return staffList;
    }

    public List<Staff> findStaffs(List<Long> staffIdList) {
        if (CollectionUtils.isEmpty(staffIdList)) return Collections.emptyList();
        List<Staff> staffList = staffDao.findStaffs(staffIdList);
        if (staffIdList.isEmpty()) {
            throw new BusinessHandleException("staff.exception.not.found.staffs", staffIdList);
        }
        return staffList;
    }

    public List<Long> findExistStaffIds(List<Long> staffIdList, Long enterLongId) {
        if (CollectionUtils.isEmpty(staffIdList)) {
            return Collections.emptyList();
        }
        List<Long> staffIds = staffDao.findStaffIds(staffIdList, enterLongId);
        if (staffIdList.isEmpty()) {
            throw new BusinessHandleException("staff.exception.not.found.staffs", staffIdList);
        }
        return staffIds;
    }
}
