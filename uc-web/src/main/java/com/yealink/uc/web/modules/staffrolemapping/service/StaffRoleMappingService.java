package com.yealink.uc.web.modules.staffrolemapping.service;

import java.util.List;

import com.yealink.uc.common.modules.staffrolemapping.StaffRoleMapping;
import com.yealink.uc.web.modules.staffrolemapping.dao.StaffRoleMappingDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class StaffRoleMappingService {
    @Autowired
    StaffRoleMappingDao staffRoleMappingDao;

    public List<StaffRoleMapping> find(Long staffId) {
        return staffRoleMappingDao.findByStaff(staffId);
    }

    public List<Long> findRoleIds(Long staffId) {
        return staffRoleMappingDao.findRoleIds(staffId);
    }
}
