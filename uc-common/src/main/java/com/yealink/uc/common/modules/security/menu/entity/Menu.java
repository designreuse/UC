package com.yealink.uc.common.modules.security.menu.entity;

import com.yealink.uc.platform.annotations.Entity;

@Entity(name = "Menu")
public class Menu {
    private Long _id;

    private String uuId;

    private Integer index;

    private String url;

    private String name;

    private String description;

    private Long projectId;

    private Long moduleId;

    private String icon;

    public String getUuId() {
        return uuId;
    }

    public void setUuId(final String uuId) {
        this.uuId = uuId;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(final Integer index) {
        this.index = index;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(final Long projectId) {
        this.projectId = projectId;
    }

    public void setModuleId(final Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public Long getModuleId() {
        return moduleId;
    }
}