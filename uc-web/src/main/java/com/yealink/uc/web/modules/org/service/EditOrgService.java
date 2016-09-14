package com.yealink.uc.web.modules.org.service;

import java.util.List;

import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.Maps;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.web.modules.org.producer.OrgMessageProducer;
import com.yealink.uc.web.modules.org.request.EditOrgRequest;
import com.yealink.uc.web.modules.org.request.EditOrgRequestItem;
import com.yealink.uc.web.modules.org.service.common.OrgCommonService;
import com.yealink.uc.web.modules.org.service.common.OrgValidateService;
import com.yealink.uc.web.modules.stafforgmapping.dao.StaffOrgMappingDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class EditOrgService {
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
    @Autowired
    StaffOrgMappingDao staffOrgMappingDao;

    @Autowired
    MoveOrgService moveOrgService;

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean editOrg(EditOrgRequest editOrgRequest, Long enterpriseId) {
        Org currentOrg = queryOrgService.getOrg(editOrgRequest.getOrgId(), enterpriseId);
        if (orgValidateService.checkNameDuplicateExceptItSelf(currentOrg.getParentId(), editOrgRequest.getOrgId(), editOrgRequest.getName())) {
            Org parentOrg = queryOrgService.findParentOrg(currentOrg.getParentId(), enterpriseId);
            throw new BusinessHandleException("org.exception.edit.name.duplicate", editOrgRequest.getName(), parentOrg.getName());
        }

        if (!currentOrg.getParentId().equals(editOrgRequest.getParentId())) {
            return editChangeParentOrg(editOrgRequest, enterpriseId, currentOrg);
        } else {
            return editNotChangeParentOrg(editOrgRequest, currentOrg);
        }
    }

    private boolean editNotChangeParentOrg(final EditOrgRequest editOrgRequest, final Org currentOrg) {
        orgCommonService.buildOrgCommonInfo(editOrgRequest, currentOrg);
        deleteAllTitleFirst(currentOrg);
        saveCurrentTitle(editOrgRequest, currentOrg);
        long updateCount = orgDao.update(currentOrg);
        if (updateCount == 1) {
            orgMessageProducer.editOrg(currentOrg.get_id());
        }
        return updateCount == 1;
    }

    private boolean editChangeParentOrg(final EditOrgRequest editOrgRequest, final Long enterpriseId, final Org currentOrg) {
        Long oldParentId = currentOrg.getParentId();
        Org parentOrg = queryOrgService.findParentOrg(editOrgRequest.getParentId(), enterpriseId);
        if (orgValidateService.checkOrgNameDuplicateInParentOrg(editOrgRequest.getParentId(), currentOrg.getName()))
            throw new BusinessHandleException("org.exception.move.name.duplicate", currentOrg.getName(), parentOrg.getName());
        String newOrgPath = parentOrg.getOrgPath() + ":" + currentOrg.get_id();
        String oldOrgPath = currentOrg.getOrgPath();
        Long now = System.currentTimeMillis();
        currentOrg.setParentId(parentOrg.get_id());
        currentOrg.setOrgPath(newOrgPath);
        currentOrg.setModificationDate(now);
        currentOrg.setIndex(orgDao.nextIndex(parentOrg.get_id()));
        if (currentOrg.getOrgPath().contains("null"))
            throw new BusinessHandleException("org.path.error.contain.null");
        orgCommonService.buildOrgCommonInfo(editOrgRequest, currentOrg);
        deleteAllTitleFirst(currentOrg);
        saveCurrentTitle(editOrgRequest, currentOrg);

        long updateCount = orgDao.update(currentOrg);
        if (updateCount == 1) {
            moveOrgService.batchUpdateSubOrgPath(oldOrgPath, newOrgPath);
            orgMessageProducer.moveOrg(currentOrg, oldParentId);
            orgCommonService.markOrgModified(currentOrg.getParentId(), enterpriseId);
            orgCommonService.markOrgModified(oldParentId, enterpriseId);
        }
        return updateCount == 1;
    }

    private void deleteAllTitleFirst(final Org currentOrg) {
        staffOrgMappingDao.updateByOrg(currentOrg.get_id(), Maps.newMapChain().put("title", "").getMap());
    }

    private void saveCurrentTitle(EditOrgRequest editOrgRequest, Org currentOrg) {
        List<EditOrgRequestItem> editOrgRequestItemList = editOrgRequest.getEditOrgRequestItemList();
        for (EditOrgRequestItem editOrgRequestItem : editOrgRequestItemList) {
            staffOrgMappingDao.updateByMappingId(currentOrg.get_id() + "_" + editOrgRequestItem.getStaffId(),
                Maps.newMapChain().put("title", editOrgRequestItem.getTitle()).getMap());
        }
    }

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public void updateRootOrg(String orgName, Long enterpriseId) {
        Org org = orgDao.getRootOrg(enterpriseId);
        org.setName(orgName);
        long updateCount = orgDao.update(org);
        if (updateCount == 1) {
            orgMessageProducer.editOrg(org.get_id());
        }
    }

}
