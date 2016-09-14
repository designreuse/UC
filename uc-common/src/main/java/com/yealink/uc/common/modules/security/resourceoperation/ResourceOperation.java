package com.yealink.uc.common.modules.security.resourceoperation;

import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 */
@Entity(name = "ResourceOperation")
public class ResourceOperation {
    private Long _id;
    private Long resourceId;
    private Long operationId;
    private Long platformEnterpriseId;

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
    }

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

    public Long getPlatformEnterpriseId() {
        return platformEnterpriseId;
    }

    public void setPlatformEnterpriseId(final Long platformEnterpriseId) {
        this.platformEnterpriseId = platformEnterpriseId;
    }
}
