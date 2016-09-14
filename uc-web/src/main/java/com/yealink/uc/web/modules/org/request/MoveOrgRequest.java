package com.yealink.uc.web.modules.org.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class MoveOrgRequest {
    @NotNull
    @Min(1)
    private Long orgId;

    @NotNull
    @Min(1)
    private Long targetOrgId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getTargetOrgId() {
        return targetOrgId;
    }

    public void setTargetOrgId(final Long targetOrgId) {
        this.targetOrgId = targetOrgId;
    }

}
