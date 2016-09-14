package com.yealink.uc.common.modules.security.role.entity;

import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 */
@Entity(name = "Role")
public class Role {
    private Long _id;

    private String name;

    private String description;

    private Long roleLevelId;

    private Long platformEnterpriseId;

    public Long getRoleLevelId() {
        return roleLevelId;
    }

    public void setRoleLevelId(final Long roleLevelId) {
        this.roleLevelId = roleLevelId;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPlatformEnterpriseId() {
        return platformEnterpriseId;
    }

    public void setPlatformEnterpriseId(final Long platformEnterpriseId) {
        this.platformEnterpriseId = platformEnterpriseId;
    }
}