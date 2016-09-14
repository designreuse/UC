package com.yealink.uc.common.modules.security.menu.vo;

/**
 * @author ChNan
 */
public enum MenuResourceType {
    PROJECT("project"), MODULE("module"), MENU("menu"), RESOURCE("resource"), OPERATION("operation");
    private String type;

    MenuResourceType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
