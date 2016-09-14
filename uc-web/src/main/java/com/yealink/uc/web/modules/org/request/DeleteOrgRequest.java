package com.yealink.uc.web.modules.org.request;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class DeleteOrgRequest {
    @NotEmpty
    private List<Long> orgIds;

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(final List<Long> orgIds) {
        this.orgIds = orgIds;
    }
}
