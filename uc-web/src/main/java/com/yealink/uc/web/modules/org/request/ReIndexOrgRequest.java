package com.yealink.uc.web.modules.org.request;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class ReIndexOrgRequest {
    private Long parentOrgId;
    @NotEmpty
    private List<ReIndexOrgRequestItem> reIndexOrgRequestItemList;

    public List<ReIndexOrgRequestItem> getReIndexOrgRequestItemList() {
        return reIndexOrgRequestItemList;
    }

    public void setReIndexOrgRequestItemList(final List<ReIndexOrgRequestItem> reIndexOrgRequestItemList) {
        this.reIndexOrgRequestItemList = reIndexOrgRequestItemList;
    }

    public Long getParentOrgId() {
        return parentOrgId;
    }

    public void setParentOrgId(final Long parentOrgId) {
        this.parentOrgId = parentOrgId;
    }
}
