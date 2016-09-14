package com.yealink.uc.web.modules.org.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.org.vo.OrgTreeNode;
import com.yealink.uc.common.modules.staff.vo.StaffStatus;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.service.modules.org.api.OrgRESTService;
import com.yealink.uc.service.modules.org.response.ListOrgTreesRESTResponse;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
@SuppressWarnings("unchecked")
public class OrgTreeService {
    @Autowired
    OrgDao orgDao;
    @Autowired
    StaffDao staffDao;
    @Autowired
    OrgRESTService orgRESTService;
    @Autowired
    StaffOrgMappingDao staffOrgMappingDao;
    Logger logger = LoggerFactory.getLogger(OrgTreeService.class);

    @Cacheable(value = "orgStaffTreeNodes", key = "#enterpriseId")
    public List<OrgTreeNodeView> showOrgStaffTreeNodes(Long enterpriseId) {
        logger.info("Go in to showOrgStaffTreeNodes");
        ListOrgTreesRESTResponse listOrgTreesResponse = orgRESTService.listOrgTrees(enterpriseId);
        return listOrgTreesResponse.getOrgTreeNodeList();
    }

    @Cacheable(value = "orgTreeNodes", key = "#enterprise.get_id()")
    public List<OrgTreeNode> showOrgStaffTreeNodesForIndex(Enterprise enterprise) {
        logger.info("Go in to showOrgStaffTreeNodesForIndex");
        return createNormalOrgNodes(enterprise);
    }

    @Cacheable(value = "orgTreeNodesWithForbiddenStaff", key = "#enterprise.get_id()")
    public List<OrgTreeNode> treeNodesWithForbiddenStaff(Enterprise enterprise) {
        logger.info("Go in to orgTreeNodesWithForbiddenStaff");
        List<OrgTreeNode> orgOrgTreeNodes = createNormalOrgNodes(enterprise);
        orgOrgTreeNodes.add(createForbiddenOrgNode(enterprise.get_id()));
        return orgOrgTreeNodes;
    }

    private List<OrgTreeNode> createNormalOrgNodes(final Enterprise enterprise) {
        List<Org> orgs = orgDao.findAll(enterprise.get_id());
        List<Long> orgIds = Lists.transform(orgs, new Function<Org, Long>() {
            @Override
            public Long apply(final Org input) {
                return input.get_id();
            }
        });

        List<StaffOrgMapping> staffOrgMappings = staffOrgMappingDao.findByOrgList(orgIds);
        Map<Long, List<Long>> orgStaffIdsMap = createOrgIdStaffsMap(staffOrgMappings);
        List<OrgTreeNode> orgOrgTreeNodes = new ArrayList<>();
        for (Org org : orgs) {
            orgOrgTreeNodes.add(createOrgNodeWithStaffCount(enterprise, orgStaffIdsMap, org));
        }
        return orgOrgTreeNodes;
    }

    private Map<Long, List<Long>> createOrgIdStaffsMap(final List<StaffOrgMapping> staffOrgMappings) {
        Map<Long, List<Long>> orgStaffIdsMap = new HashMap<>();
        for (StaffOrgMapping staffOrgMapping : staffOrgMappings) {
            if (orgStaffIdsMap.get(staffOrgMapping.getOrgId()) == null) {
                orgStaffIdsMap.put(staffOrgMapping.getOrgId(), Lists.newArrayList(staffOrgMapping.getStaffId()));
            } else {
                orgStaffIdsMap.get(staffOrgMapping.getOrgId()).add(staffOrgMapping.getStaffId());
            }
        }
        return orgStaffIdsMap;
    }

    private OrgTreeNode createOrgNodeWithStaffCount(final Enterprise enterprise, final Map<Long, List<Long>> orgStaffIdsMap, final Org org) {
        List<Long> staffIds = orgStaffIdsMap.get(org.get_id());
        Long count;
        if (staffIds != null) {
            count = staffDao.countAvailableStaff(staffIds, enterprise.get_id());
        } else {
            count = 0L;
        }
        return new OrgTreeNode(org.get_id(), org.getName() + '(' + count + ")", org.getParentId(),
            OrgTreeNode.NODE_TYPE_ORG, OrgTreeNode.NODE_TYPE_ORG,
            org.getParentId() == 0, org.getMail(), null, org.getIndex());
    }

    public List<OrgTreeNode> showTreeNodesForMove(Long moveOrgId, Long enterpriseId) {
        if (!orgDao.checkOrgExistInEnterprise(moveOrgId, enterpriseId)) {
            throw new BusinessHandleException("org.exception.not.exist.in.enterprise");
        }

        List<Org> orgs = orgDao.findAll(enterpriseId);
        List<Org> orgsResult = new ArrayList<>();
        orgsResult.addAll(orgs);
        List<Long> excludeOrgIds = new ArrayList<>();
        excludeOrgIds.add(moveOrgId);

        for (Org org : orgs) { // 1:146:126:209 split ":" is 1 146 126 209
            if (Arrays.asList(org.getOrgPath().split(":")).contains(String.valueOf(moveOrgId))) {
                orgsResult.remove(org);
            }
        }
        return createOrgNodeList(orgsResult);
    }


    private List<OrgTreeNode> createOrgNodeList(List<Org> orgList) {
        List<OrgTreeNode> orgOrgTreeNodes = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orgList)) {
            for (Org org : orgList) {
                orgOrgTreeNodes.add(createOrgNode(org));
            }
        }
        return orgOrgTreeNodes;
    }

    private OrgTreeNode createOrgNode(Org org) {
        return new OrgTreeNode(org.get_id(), org.getName(), org.getParentId(),
            OrgTreeNode.NODE_TYPE_ORG, OrgTreeNode.NODE_TYPE_ORG,
            org.getParentId() == 0, org.getMail(), null, org.getIndex());
    }

    public OrgTreeNode createForbiddenOrgNode(long enterpriseId) {
        Org org = orgDao.getRootOrg(enterpriseId);
        long forbiddenStaffCount = staffDao.count(StaffStatus.RECYCLE, enterpriseId);
        return new OrgTreeNode(OrgTreeNode.FORBIDDEN_ORG_ID, OrgTreeNode.FORBIDDEN_ORG + "(" + forbiddenStaffCount + ")", org.get_id(),
            OrgTreeNode.NODE_TYPE_ORG, OrgTreeNode.NODE_TYPE_ORG, false, null, null, Integer.MAX_VALUE);
    }

}
