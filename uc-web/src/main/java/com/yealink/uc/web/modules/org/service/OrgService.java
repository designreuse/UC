package com.yealink.uc.web.modules.org.service;

import java.util.List;

import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.org.vo.OrgTreeNode;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;
import com.yealink.uc.web.modules.org.request.CreateOrgRequest;
import com.yealink.uc.web.modules.org.request.EditOrgRequest;
import com.yealink.uc.web.modules.org.request.MoveOrgRequest;
import com.yealink.uc.web.modules.org.request.ReIndexOrgRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class OrgService {
    @Autowired
    CreateOrgService createOrgService;
    @Autowired
    DeleteOrgService deleteOrgService;
    @Autowired
    MoveOrgService moveOrgService;
    @Autowired
    EditOrgService editOrgService;
    @Autowired
    ReIndexOrgService reIndexOrgService;
    @Autowired
    QueryOrgService queryOrgService;
    @Autowired
    OrgTreeService orgTreeService;


    public Org getOrg(Long orgId, Long enterpriseId) {
        return queryOrgService.getOrg(orgId, enterpriseId);
    }

    public boolean createOrg(CreateOrgRequest createOrgRequest, Enterprise enterprise) {
        return createOrgService.createOrg(createOrgRequest, enterprise);
    }

    public boolean editOrg(EditOrgRequest editOrgRequest, Long enterpriseId) {
        return editOrgService.editOrg(editOrgRequest, enterpriseId);
    }

    public boolean moveOrg(MoveOrgRequest moveOrgRequest, Long enterpriseId) {
        return moveOrgService.moveOrg(moveOrgRequest.getOrgId(),moveOrgRequest.getTargetOrgId(), enterpriseId);
    }

    public boolean reIndex(ReIndexOrgRequest reIndexOrgRequest, Long enterpriseId) {
        return reIndexOrgService.reIndex(reIndexOrgRequest, enterpriseId);
    }

    public boolean deleteOrg(List<Long> orgIds, Long enterpriseId) {
        boolean deleteSuccess = false;
        for (Long orgId : orgIds) {
            deleteSuccess = deleteOrgService.deleteOrg(orgId, enterpriseId);
        }
        return deleteSuccess;
    }

    public List<OrgTreeNodeView> showOrgStaffTreeNodes(Long enterpriseId) {
        return orgTreeService.showOrgStaffTreeNodes(enterpriseId);
    }

    public List<OrgTreeNode> showOrgStaffTreeNodesForIndex(Enterprise enterprise) {
        return orgTreeService.showOrgStaffTreeNodesForIndex(enterprise);
    }

    public List<OrgTreeNode> createTreeNodesForMove(Long moveOrgId, Long enterpriseId) {
        return orgTreeService.showTreeNodesForMove(moveOrgId, enterpriseId);
    }

    public Org createForbiddenOrg(){
        Org o = new Org();
        o.set_id(OrgTreeNode.FORBIDDEN_ORG_ID);
        o.setName(OrgTreeNode.FORBIDDEN_ORG);
        return o;
    }
}
