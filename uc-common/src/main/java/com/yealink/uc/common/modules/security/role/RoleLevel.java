package com.yealink.uc.common.modules.security.role;

import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 */
@Entity(name = "RoleLevel")
public class RoleLevel {
    private Long _id;
    private String name;
    private String description;
    private Integer priority;
    private String type;
    private String code;
    private Long platformEnterpriseId;

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


    public Integer getPriority() {
        return priority;
    }

    public void setPriority(final Integer priority) {
        this.priority = priority;
    }

    public Long getPlatformEnterpriseId() {
        return platformEnterpriseId;
    }

    public void setPlatformEnterpriseId(final Long platformEnterpriseId) {
        this.platformEnterpriseId = platformEnterpriseId;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }
}
