package com.yealink.uc.common.modules.security.rolepermission.entity;


import com.yealink.uc.platform.annotations.Entity;

@Entity(name = "RolePermission")
public class RolePermission {
    private Long _id;

    private Long menuId;

    private Long roleId;

    private Long resourceOperationId;

    private String type;

    private Long projectId;

    public RolePermission(final Long roleId, final String type, final Long projectId) {
        this.roleId = roleId;
        this.type = type;
        this.projectId = projectId;
    }

    public RolePermission() {
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(final Long menuId) {
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceOperationId() {
        return resourceOperationId;
    }

    public void setResourceOperationId(final Long resourceOperationId) {
        this.resourceOperationId = resourceOperationId;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(final Long projectId) {
        this.projectId = projectId;
    }
}