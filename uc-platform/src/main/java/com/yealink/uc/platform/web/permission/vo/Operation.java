package com.yealink.uc.platform.web.permission.vo;

public enum Operation {
    READ("Read"), CREATE("Create"), UPDATE("Update"), DELETE("Delete");

    private String code;

    Operation(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
