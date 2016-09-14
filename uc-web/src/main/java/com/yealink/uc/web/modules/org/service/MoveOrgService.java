package com.yealink.uc.web.modules.org.service;

import java.util.List;

import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.web.modules.org.dao.OrgDao;
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
public class MoveOrgService {
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private OrgMessageProducer orgMessageProducer;
    @Autowired
    private QueryOrgService queryOrgService;
    @Autowired
    private OrgValidateService orgValidateService;
    @Autowired
    private OrgCommonService orgCommonService;

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean moveOrg(Long orgId, Long targetOrgId, Long enterpriseId) {
        Org currentOrg = queryOrgService.getOrg(orgId, enterpriseId);
        Org parentOrg = queryOrgService.findParentOrg(targetOrgId, enterpriseId);
        if (orgValidateService.checkOrgNameDuplicateInParentOrg(targetOrgId, currentOrg.getName()))
            throw new BusinessHandleException("org.exception.move.name.duplicate", currentOrg.getName(), parentOrg.getName());
        // 判断目标节点是否被异步修改成处于子组织路径中了。
        if (orgValidateService.checkParentOrgChangeToSub(orgId, targetOrgId))
            throw new BusinessHandleException("org.exception.move.parent.changeto.sub", currentOrg.getName(), parentOrg.getName());
        return moveWhenOrgExist(currentOrg, parentOrg, currentOrg.getParentId(), enterpriseId);
    }

    private boolean moveWhenOrgExist(Org currentOrg, Org parentOrg, Long oldParentId, Long enterpriseId) {
        String newOrgPath = parentOrg.getOrgPath() + ":" + currentOrg.get_id();
        String oldOrgPath = currentOrg.getOrgPath();
        Long now = System.currentTimeMillis();
        currentOrg.setParentId(parentOrg.get_id());
        currentOrg.setOrgPath(newOrgPath);
        currentOrg.setModificationDate(now);
        currentOrg.setIndex(orgDao.nextIndex(parentOrg.get_id()));
        if (currentOrg.getOrgPath().contains("null"))
            throw new BusinessHandleException("org.path.error.contain.null");
        long updateCount = orgDao.update(currentOrg);
        batchUpdateSubOrgPath(oldOrgPath, newOrgPath);
        if (updateCount == 1) {
            orgMessageProducer.moveOrg(currentOrg, oldParentId);
            orgCommonService.markOrgModified(currentOrg.getParentId(), enterpriseId);
            orgCommonService.markOrgModified(oldParentId, enterpriseId);
        }
        return updateCount == 1;
    }

    public void batchUpdateSubOrgPath(String oldOrgPath, String newOrgPath) {
        List<Org> subOrgList = orgDao.findAllSubOrgListInOrgPath(oldOrgPath);
        for (Org subOrg : subOrgList) {
            subOrg.setOrgPath(subOrg.getOrgPath().replace(oldOrgPath, newOrgPath));
            orgDao.update(subOrg);
            orgMessageProducer.editOrg(subOrg.get_id());
        }
    }
}
