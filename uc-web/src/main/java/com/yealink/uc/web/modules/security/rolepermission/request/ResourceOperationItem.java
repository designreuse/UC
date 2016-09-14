package com.yealink.uc.web.modules.security.rolepermission.request;

/**
 * @author ChNan
 */
public class ResourceOperationItem {
    private Long resourceId;

    private Long operationId;


    public ResourceOperationItem(final Long resourceId, final Long operationId) {
        this.resourceId = resourceId;
        this.operationId = operationId;
    }

    public ResourceOperationItem() {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ResourceOperationItem that = (ResourceOperationItem) o;

        if (operationId != null ? !operationId.equals(that.operationId) : that.operationId != null) return false;
        if (resourceId != null ? !resourceId.equals(that.resourceId) : that.resourceId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = resourceId != null ? resourceId.hashCode() : 0;
        result = 31 * result + (operationId != null ? operationId.hashCode() : 0);
        return result;
    }
}
