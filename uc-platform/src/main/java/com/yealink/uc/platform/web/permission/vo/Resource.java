package com.yealink.uc.platform.web.permission.vo;

/**
 * @author ChNan
 */
public enum Resource {
    ORG("Org"), STAFF("Staff"),

    ACCOUNT("Account"), ENTERPRISE("Enterprise"),

    PROJECT("Project"), MODULE("Module"), MENU("Menu"),

    ROLE("Role"), ROLE_LEVEL("Role_level"), ROLE_PERMISSION("Role_permission"),

    OPERATION("Operation"), RESOURCE("Resource"),

    RESOURCE_OPERATION("Resource_operation");

    private String code;

    Resource(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
