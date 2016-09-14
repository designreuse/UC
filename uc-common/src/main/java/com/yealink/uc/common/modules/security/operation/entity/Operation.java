package com.yealink.uc.common.modules.security.operation.entity;

import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 */
@Entity(name = "Operation")
public class Operation {
    private Long _id;

    private String name;

    private String description;

    private Long platformEnterpriseId;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getPlatformEnterpriseId() {
        return platformEnterpriseId;
    }

    public void setPlatformEnterpriseId(final Long platformEnterpriseId) {
        this.platformEnterpriseId = platformEnterpriseId;
    }
}