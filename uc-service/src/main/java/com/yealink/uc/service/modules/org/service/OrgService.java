package com.yealink.uc.service.modules.org.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.service.modules.org.response.FindOrgsRESTResponse;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;
import com.yealink.uc.service.modules.staff.service.StaffService;
import com.yealink.uc.service.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class OrgService {
    @Autowired
    QueryOrgService queryOrgService;
    @Autowired
    OrgTreeService orgTreeService;
    @Autowired
    StaffService staffService;

    @Autowired
    StaffOrgMappingDao staffOrgMappingDao;

    public Org getOrg(Long orgId, Long enterpriseId) {
        return queryOrgService.getOrg(orgId, enterpriseId);
    }

    public List<Org> findAll(Long enterpriseId) {
        return queryOrgService.findAll(enterpriseId);
    }

    public List<OrgTreeNodeView> showOrgTrees(Long enterpriseId) {
        return orgTreeService.showOrgStaffTreeNodes(enterpriseId);
    }

    public FindOrgsRESTResponse findOrgs(List<Long> staffIds) {
        List<Staff> staffs = staffService.findStaffs(staffIds);
        List<StaffOrgMapping> staffOrgMappings = staffOrgMappingDao.findByOrgList(staffIds);

        List<Long> orgIds = Lists.transform(staffOrgMappings, new Function<StaffOrgMapping, Long>() {
            @Override
            public Long apply(final StaffOrgMapping input) {
                return input.getOrgId();
            }
        });
        List<Org> orgs = queryOrgService.findOrgs(orgIds);
        Map<Long, Org> orgIdMap = Maps.uniqueIndex(orgs.iterator(), new Function<Org, Long>() {
            @Override
            public Long apply(final Org input) {
                return input.get_id();
            }
        });
        Map<Long, Staff> staffIdMap = Maps.uniqueIndex(staffs.iterator(), new Function<Staff, Long>() {
            @Override
            public Long apply(final Staff input) {
                return input.get_id();
            }
        });
        Map<Long, List<Org>> staffIdMappingsMap = new HashMap<>();
        for (StaffOrgMapping staffOrgMapping : staffOrgMappings) {
            if (staffIdMappingsMap.get(staffOrgMapping.getStaffId()) != null) {
                Org org = orgIdMap.get(staffOrgMapping.getOrgId());
                List<Org> staffOrgs = new ArrayList<>();
                staffOrgs.add(org);
                staffIdMappingsMap.put(staffOrgMapping.getStaffId(), staffOrgs);
            } else {
                Org org = orgIdMap.get(staffOrgMapping.getOrgId());
                staffIdMappingsMap.get(staffOrgMapping.getStaffId()).add(org);
            }
        }
        FindOrgsRESTResponse response = new FindOrgsRESTResponse();
        for (Map.Entry<Long, List<Org>> entry : staffIdMappingsMap.entrySet()) {
            Long staffId = entry.getKey();
//            List<Org> orgs = entry.getValue();
        }
        return response;
    }

}
