package com.yealink.uc.common.modules.businessaccount.vo;

/**
 * @author ChNan
 */
public enum BusinessType {
    IM("IM"), EXTENSION("Extension");
    private String code;

    BusinessType(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
