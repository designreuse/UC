package com.yealink.uc.service.modules.org.response;

import java.util.List;

import com.yealink.uc.service.modules.org.vo.OrgView;

/**
 * @author ChNan
 */
public class FindAllOrgRESTResponse {
    List<OrgView> orgList;

    public List<OrgView> getOrgList() {
        return orgList;
    }

    public void setOrgList(final List<OrgView> orgList) {
        this.orgList = orgList;
    }
}
