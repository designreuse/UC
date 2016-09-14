package com.yealink.uc.common.modules.account.vo;

/**
 * @author ChNan
 */
public enum AccountStatus {
    ACTIVE(1), IN_ACTIVE(0);

    private int code;

    AccountStatus(final int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
