package com.yealink.uc.common.modules.staff.vo;

/**
 * @author ChNan
 */
public enum StaffStatus {
    WORKING(1), LOCKED(-1), RECYCLE(-2), OUT_OF_JOB(-3);

    private int code;

    StaffStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
