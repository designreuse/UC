package com.yealink.uc.platform.web.permission.vo;

/**
 * @author ChNan
 */
public class ResourceOperationView {
    private String resource;
    private String operation;

    public ResourceOperationView() {
    }

    public ResourceOperationView(final String resource, final String operation) {
        this.resource = resource;
        this.operation = operation;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(final String resource) {
        this.resource = resource;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(final String operation) {
        this.operation = operation;
    }
}
