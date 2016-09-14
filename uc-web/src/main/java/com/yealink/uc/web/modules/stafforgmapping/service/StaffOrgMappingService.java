package com.yealink.uc.web.modules.stafforgmapping.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.StringUtil;
import com.yealink.uc.web.modules.org.service.OrgService;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.staff.producer.StaffMessageProducer;
import com.yealink.uc.web.modules.staff.request.MoveStaffRequest;
import com.yealink.uc.web.modules.staff.request.ReindexStaffRequest;
import com.yealink.uc.web.modules.staff.request.ReleaseOrgMappingRequest;
import com.yealink.uc.web.modules.staff.service.QueryStaffService;
import com.yealink.uc.web.modules.staff.service.common.CommonStaffService;
import com.yealink.uc.web.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class StaffOrgMappingService {
    @Autowired
    StaffOrgMappingDao staffOrgMappingDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private CommonStaffService commonStaffService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private StaffMessageProducer staffMessageProducer;
    @Autowired
    private QueryStaffService queryStaffService;

    public boolean batchSave(List<StaffOrgMapping> stafforgMappings) {
        return staffOrgMappingDao.batchSave(stafforgMappings) > 0;
    }

    public List<Long> findStaffIdListByOrg(Long orgId) {
        return staffOrgMappingDao.findStaffIdListByOrg(orgId);
    }

    public List<StaffOrgMapping> findHasTitleByOrg(Long orgId) {
        List<StaffOrgMapping> stafforgMappings = staffOrgMappingDao.findByOrg(orgId);

        CollectionUtils.filter(stafforgMappings, new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return StringUtil.hasText(((StaffOrgMapping) object).getTitle());
            }
        });
        return stafforgMappings;
    }

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean moveStaff(MoveStaffRequest moveStaffRequest, Long enterpriseId) {
        queryStaffService.findExistStaffIds(Arrays.asList(moveStaffRequest.getStaffId()), enterpriseId);
        List<StaffOrgMapping> currentStafforgMappings = staffOrgMappingDao.findByStaff(moveStaffRequest.getStaffId());
        List<StaffOrgMapping> staffOrgMappings = commonStaffService.mergeOrgMappings(moveStaffRequest.getStaffId(),
            moveStaffRequest.getTargetOrgIds(), currentStafforgMappings, enterpriseId);
        staffOrgMappingDao.deleteByStaff(moveStaffRequest.getStaffId());
        boolean moveSuccess = batchSave(staffOrgMappings);
        if (moveSuccess) {
            staffMessageProducer.moveStaff(currentStafforgMappings, staffOrgMappings);
        }
        return moveSuccess;
    }

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean releaseOrgMapping(ReleaseOrgMappingRequest releaseOrgMappingRequest, Long enterpriseId) {
        queryStaffService.findExistStaffIds(Arrays.asList(releaseOrgMappingRequest.getStaffId()), enterpriseId);
        List<StaffOrgMapping> staffOrgMappings = staffOrgMappingDao.findByStaff(releaseOrgMappingRequest.getStaffId());
        boolean hasChange = false;
        for (StaffOrgMapping staffOrgMapping : staffOrgMappings) {
            if (staffOrgMapping.getOrgId().equals(releaseOrgMappingRequest.getOrgId())) {
                staffOrgMappings.remove(staffOrgMapping);
                hasChange = true;
                break;
            }
        }
        if (!hasChange) {
            return true;
        }
        if (staffOrgMappings.isEmpty()) {
            throw new BusinessHandleException(MessageUtil.getMessage("staff.exception.only.in.one.org.cannot.release"));
        }
        Long updateCount = staffOrgMappingDao.deleteByStaffOrg(releaseOrgMappingRequest.getStaffId(), releaseOrgMappingRequest.getOrgId());
        if (updateCount == 1) {
            staffMessageProducer.editStaff(releaseOrgMappingRequest.getStaffId());
        }
        return updateCount == 1;
    }

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean reindexStaff(ReindexStaffRequest reindexRequest, Long enterpriseId) {
        orgService.getOrg(reindexRequest.getOrgId(), enterpriseId);
        List<Long> existStaffIdsByAsc = getExistStaffIds(reindexRequest.getStaffIdsByAsc(), enterpriseId);
        int index = 1;
        int updateEffect = 0;
        Map<String, Object> updateMap = new HashMap<>();
        for (Long staffId : existStaffIdsByAsc) {
            updateMap.put("staffIndexInOrg", index++);
            updateEffect += staffOrgMappingDao.updateByMappingId(reindexRequest.getOrgId() + "_" + staffId, updateMap);
        }
        if (updateEffect > 0) {
            staffMessageProducer.reindexStaff(reindexRequest.getOrgId());
        }
        return updateEffect > 0;
    }

    private List<Long> getExistStaffIds(List<Long> reindexStaffIdsByAsc, Long enterpriseId) {
        List<Long> existStaffIds = queryStaffService.findExistStaffIds(reindexStaffIdsByAsc, enterpriseId);
        List<Long> exitStaffIdsByAsc = new ArrayList<>();
        for (Long staffId : reindexStaffIdsByAsc) {
            if (existStaffIds.contains(staffId)) {
                exitStaffIdsByAsc.add(staffId);
            }
        }
        return exitStaffIdsByAsc;
    }

    public String findStaffOrgMapping(Long staffId, Long enterpriseId) {
        queryStaffService.findExistStaffIds(Arrays.asList(staffId), enterpriseId);
        List<StaffOrgMapping> stafforgMappings = staffOrgMappingDao.findByStaff(staffId);
        StringBuffer buffer = new StringBuffer();
        for (StaffOrgMapping staffOrgMapping : stafforgMappings) {
            buffer.append(staffOrgMapping.getOrgId());
            buffer.append(";");
        }
        if (buffer.length() != 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }

    public List<StaffOrgMapping> findByStaff(Long staffId, Long enterpriseId) {
        queryStaffService.findExistStaffIds(Arrays.asList(staffId), enterpriseId);
        return staffOrgMappingDao.findByStaff(staffId);
    }
}
