package com.yealink.uc.web.modules.security.resourceoperation.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class CreateResourceOperationRequest {
    @Min(1)
    @NotNull
    private Long resourceId;

    @Min(1)
    @NotNull
    private Long operationId;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(final Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(final Long operationId) {
        this.operationId = operationId;
    }
}
