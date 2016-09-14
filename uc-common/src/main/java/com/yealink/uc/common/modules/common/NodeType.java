package com.yealink.uc.common.modules.common;

/**
 * @author ChNan
 */
public enum NodeType {
    PROJECT("project"), MODULE("module"), MENU("menu"),

    RESOURCE("resource"), OPERATION("operation"),

    RESOURCE_OPERATION("resource_operation"),

    ORG("org"), STAFF("staff"),

    ENTERPRISE("enterprise");

    private String type;

    NodeType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
