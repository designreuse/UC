package com.yealink.uc.service.modules.org.vo;

/**
 * @author ChNan
 */
public enum OrgTreeNodeType {
    ORG("org"), STAFF("staff");
    private String code;

    OrgTreeNodeType(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
