package com.yealink.uc.common.modules.security.rolepermission.vo;

/**
 * @author ChNan
 */
public enum PermissionType {
    MENU("menu"), RESOURCE_OPERATION("resource_operation");
    private String code;

    PermissionType(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
