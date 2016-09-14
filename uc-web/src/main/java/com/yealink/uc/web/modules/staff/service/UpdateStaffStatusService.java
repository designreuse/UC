package com.yealink.uc.web.modules.staff.service;

import java.util.ArrayList;
import java.util.List;

import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.staff.vo.StaffStatus;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.staff.dao.UserDao;
import com.yealink.uc.web.modules.staff.producer.StaffMessageProducer;
import com.yealink.uc.web.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * Created by yewl on 2016/6/20.
 */
@Service
public class UpdateStaffStatusService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private StaffMessageProducer staffMessageProducer;
    @Autowired
    private StaffOrgMappingDao staffOrgMappingDao;

    public boolean lockStaff(Long staffId, Long enterpriseId) {
        boolean isSuccess = updateStaffStatus(staffId, enterpriseId, StaffStatus.LOCKED, StaffStatus.WORKING);
        if (isSuccess) {
            staffMessageProducer.lockStaff(staffId);
        }
        return isSuccess;
    }

    public boolean unlockStaff(Long staffId, Long enterpriseId) {
        return updateStaffStatus(staffId, enterpriseId, StaffStatus.WORKING, StaffStatus.LOCKED);
    }

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean recycleStaff(Long staffId, Long enterpriseId) {
        boolean recycleSuccess = updateStaffStatus(staffId, enterpriseId, StaffStatus.RECYCLE, StaffStatus.WORKING, StaffStatus.LOCKED);
        if (recycleSuccess) {
            staffMessageProducer.recycleStaff(staffId);
        }
        return recycleSuccess;
    }

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean recoverStaffs(List<Long> staffIdList, Long enterpriseId) {
        staffIdList = staffDao.findStaffIds(staffIdList, enterpriseId);
        List<StaffOrgMapping> stafforgMappings = staffOrgMappingDao.findByStaffList(staffIdList);
        updateRecoverStaffOrgMapping(staffIdList, stafforgMappings, enterpriseId);
        boolean revertSuccess = batchUpdateStaffStatus(staffIdList, enterpriseId, StaffStatus.WORKING, StaffStatus.RECYCLE);
        if (revertSuccess) {
            staffMessageProducer.recoverStaff(staffIdList);
        }
        return revertSuccess;
    }

    private void updateRecoverStaffOrgMapping(List<Long> staffIdList, List<StaffOrgMapping> oldStafforgMappings, Long enterpriseId) {
        List<Long> staffIdListCopy = new ArrayList<>();
        for (Long staffId : staffIdList) {
            staffIdListCopy.add(new Long(staffId.longValue()));
        }
        List<Long> orgIds = new ArrayList<>();
        for (StaffOrgMapping staffOrgMapping : oldStafforgMappings) {
            orgIds.add(staffOrgMapping.getOrgId());
        }
        orgIds = orgDao.findOrgIds(orgIds, enterpriseId);
        List<StaffOrgMapping> newStafforgMappings = new ArrayList<>();
        for (StaffOrgMapping staffOrgMapping : oldStafforgMappings) {
            for (Long orgId : orgIds) {
                if (staffOrgMapping.getOrgId().longValue() == orgId.longValue()) {
                    newStafforgMappings.add(staffOrgMapping);
                    staffIdListCopy.remove(staffOrgMapping.getStaffId());
                    orgIds.remove(orgId);
                    break;
                }
            }
        }
        staffOrgMappingDao.deleteByStaffIdList(staffIdList);
        newStafforgMappings.addAll(fixStaffToRootOrg(staffIdListCopy, enterpriseId));
        staffOrgMappingDao.batchSave(newStafforgMappings);
    }

    private List<StaffOrgMapping> fixStaffToRootOrg(List<Long> staffIdList, Long enterpriseId) {
        List<StaffOrgMapping> staffRootorgMappings = new ArrayList<>();
        if (staffIdList == null || staffIdList.isEmpty()) {
            return staffRootorgMappings;
        }
        Org org = orgDao.findRootOrgCount(enterpriseId);
        int rootOrgNextIndex = staffOrgMappingDao.nextIndexInOrg(org.get_id());
        for (Long staffId : staffIdList) {
            staffRootorgMappings.add(new StaffOrgMapping(staffId, org.get_id(), rootOrgNextIndex++));
        }
        return staffRootorgMappings;
    }

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean deleteStaff(List<Long> staffIdList, Long enterpriseId) {
        boolean deleteSuccess = batchUpdateStaffStatus(staffIdList, enterpriseId, StaffStatus.OUT_OF_JOB, StaffStatus.WORKING, StaffStatus.LOCKED, StaffStatus.RECYCLE);
        if (deleteSuccess) {
            staffMessageProducer.deleteStaff(staffIdList);
        }
        return deleteSuccess;
    }

    private boolean batchUpdateStaffStatus(List<Long> staffIdList, Long enterpriseId, StaffStatus staffStatus, StaffStatus... oldStatus) {
        userDao.batchUpdateUserStatus(staffIdList, enterpriseId, staffStatus, oldStatus);
        return staffDao.batchUpdateStaffStatus(staffIdList, enterpriseId, staffStatus, oldStatus) >= 1;
    }

    private boolean updateStaffStatus(Long staffId, Long enterpriseId, StaffStatus staffStatus, StaffStatus... oldStatus) {
        userDao.updateUserStatus(staffId, enterpriseId, staffStatus, oldStatus);
        return staffDao.updateStaffStatus(staffId, enterpriseId, staffStatus, oldStatus) == 1;
    }
}
