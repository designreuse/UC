package com.yealink.uc.web.modules.org.service.common;

import java.util.List;

import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author ChNan
 */
@Service
public class OrgValidateService {
    @Autowired
    OrgDao orgDao;
    @Autowired
    private StaffOrgMappingDao staffOrgMappingDao;

    @Autowired
    StaffDao staffDao;

    public boolean checkOrgNameDuplicateInParentOrg(Long parentId, String orgName) {
        return orgDao.findSubOrgCountByName(parentId, orgName) > 0;
    }

    public boolean checkNameDuplicateExceptItSelf(Long parentId, Long orgId, String orgName) {
        return orgDao.findSubOrgCountByNameExceptItSelf(parentId, orgId, orgName) > 0;
    }

    public boolean checkOrgExistSubOrgOrStaff(Long orgId, Long enterpriseId) {
        List<Long> staffIdList = staffOrgMappingDao.findStaffIdListByOrg(orgId);
        if (CollectionUtils.isEmpty(staffIdList)) return false;
        return orgDao.findSubOrgCount(orgId) > 0 || staffDao.countAvailableStaff(staffIdList, enterpriseId) > 0;
    }

    public boolean checkParentOrgChangeToSub(Long orgId, Long targetOrgId) {
        return orgDao.checkTargetOrgIsMovedOrgSub(orgId, targetOrgId);
    }
}
