package com.yealink.uc.common.modules.account.vo;

/**
 * @author yewl
 */
public enum AccountType {
    PRIMARY("Primary"), EMAIL("Email"), MOBILE("Mobile");

    private String code;

    AccountType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
