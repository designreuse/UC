package com.yealink.uc.common.modules.security.project.entity;

import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 */
@Entity(name = "Project")
public class Project {
    private Long _id;
    private String name;
    private String description;
    private String url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }


    public Long getPlatformEnterpriseId() {
        return platformEnterpriseId;
    }

    public void setPlatformEnterpriseId(final Long platformEnterpriseId) {
        this.platformEnterpriseId = platformEnterpriseId;
    }
}
