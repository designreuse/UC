package com.yealink.uc.web.modules.org.service;

import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.web.modules.org.producer.OrgMessageProducer;
import com.yealink.uc.web.modules.org.service.common.OrgCommonService;
import com.yealink.uc.web.modules.org.service.common.OrgValidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class DeleteOrgService {
    @Autowired
    OrgDao orgDao;
    @Autowired
    OrgMessageProducer orgMessageProducer;
    @Autowired
    QueryOrgService queryOrgService;
    @Autowired
    OrgValidateService orgValidateService;
    @Autowired
    OrgCommonService orgCommonService;

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes","orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean deleteOrg(Long orgId, Long enterpriseId) {
        Org currentOrg = queryOrgService.getOrg(orgId, enterpriseId);
        return deleteWhenOrgExist(currentOrg, enterpriseId);
    }

    private boolean deleteWhenOrgExist(Org currentOrg, Long enterpriseId) {
        if (currentOrg.getParentId() == 0)
            throw new BusinessHandleException("org.exception.delete.root.forbidden");
        if (orgValidateService.checkOrgExistSubOrgOrStaff(currentOrg.get_id(),enterpriseId))
            throw new BusinessHandleException("org.exception.delete.not.empty", currentOrg.getName());
        long deleteCount = orgDao.delete(currentOrg.get_id());
        if (deleteCount == 1) {
            orgMessageProducer.deleteOrg(currentOrg);
            orgCommonService.markOrgModified(currentOrg.getParentId(), enterpriseId);
        }
        return deleteCount == 1;
    }

}
