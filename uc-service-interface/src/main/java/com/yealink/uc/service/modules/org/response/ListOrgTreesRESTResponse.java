package com.yealink.uc.service.modules.org.response;

import java.util.List;

import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;

/**
 * @author ChNan
 */
public class ListOrgTreesRESTResponse {
    List<OrgTreeNodeView> orgTreeNodeList;

    public List<OrgTreeNodeView> getOrgTreeNodeList() {
        return orgTreeNodeList;
    }

    public void setOrgTreeNodeList(final List<OrgTreeNodeView> orgTreeNodeList) {
        this.orgTreeNodeList = orgTreeNodeList;
    }
}
