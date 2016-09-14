package com.yealink.uc.web.modules.org.service;

import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.web.modules.org.producer.OrgMessageProducer;
import com.yealink.uc.web.modules.org.request.CreateOrgRequest;
import com.yealink.uc.web.modules.org.service.common.OrgCommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class CreateOrgService {
    @Autowired
    OrgDao orgDao;
    @Autowired
    IdGeneratorDao idGeneratorDao;
    @Autowired
    OrgMessageProducer orgMessageProducer;
    @Autowired
    QueryOrgService queryOrgService;
    @Autowired
    OrgCommonService orgCommonService;

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes", "orgTreeNodesWithForbiddenStaff"}, key = "#enterprise.get_id()")
    })
    public boolean createOrg(CreateOrgRequest createOrgRequest, Enterprise enterprise) {
        if (checkOrgNameDuplicateInParentOrg(createOrgRequest.getParentId(), createOrgRequest.getName())) {
            Org parentOrg = queryOrgService.findParentOrg(createOrgRequest.getParentId(), enterprise.get_id());
            throw new BusinessHandleException("org.exception.create.error.name.duplicate", createOrgRequest.getName(), parentOrg.getName());
        }
        Long orgId = idGeneratorDao.nextId(EntityUtil.getEntityName(Org.class));
        Org org = createOrgEntity(orgId, createOrgRequest, enterprise);
        long insertCount = orgDao.save(org);

        if (insertCount == 1) {
            orgMessageProducer.createOrg(org.get_id());
            orgCommonService.markOrgModified(createOrgRequest.getParentId(), enterprise.get_id());
        }
        return insertCount == 1;
    }

    private Org createOrgEntity(Long orgId, CreateOrgRequest createOrgRequest, Enterprise enterprise) {
        Org org = new Org();
        int nextIndex = orgDao.nextIndex(createOrgRequest.getParentId());
        org.setIndex(nextIndex);
        orgCommonService.buildOrgCommonInfo(createOrgRequest, org);
        buildCreateOrgInfo(orgId, createOrgRequest, org, enterprise);
        return org;
    }

    private boolean checkOrgNameDuplicateInParentOrg(Long parentId, String orgName) {
        return orgDao.findSubOrgCountByName(parentId, orgName) > 0;
    }

    private void buildCreateOrgInfo(Long orgId, CreateOrgRequest createOrgRequest, Org org, Enterprise enterprise) {
        org.set_id(orgId);
        org.setEnterpriseId(enterprise.get_id());
        Org parentOrg = queryOrgService.findParentOrg(createOrgRequest.getParentId(), enterprise.get_id());
        org.setModificationDate(System.currentTimeMillis());
        org.setParentId(parentOrg.get_id());
        org.setOrgPath(parentOrg.getOrgPath() + ":" + org.get_id());
        if (org.getOrgPath().contains("null"))
            throw new BusinessHandleException("org.path.error.contain.null");
    }

}
