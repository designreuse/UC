package com.yealink.uc.common.modules.enterprise.vo;

/**
 * @author ChNan
 */
public enum EnterpriseType {
    PLATFORM("platform"), EXTERNAL("external");

    private String name;

    EnterpriseType(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
