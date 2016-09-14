package com.yealink.uc.web.modules.org.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class ReIndexOrgRequestItem {
    @NotNull
    @Min(1)
    private Long orgId;

    @NotNull
    @Min(1)
    private Integer index;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(final Integer index) {
        this.index = index;
    }
}
