package com.yealink.uc.web.modules.org.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class CreateOrgRequest extends OrgCommonRequest {
    @Min(1)
    @NotNull
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }
}
