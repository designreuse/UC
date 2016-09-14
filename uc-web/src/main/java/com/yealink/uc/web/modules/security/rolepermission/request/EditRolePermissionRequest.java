package com.yealink.uc.web.modules.security.rolepermission.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class EditRolePermissionRequest {
    @Min(1)
    @NotNull
    private Long roleId;

    public List<Long> menuIdList;

    public List<Long> resourceOperationIdList;

    @NotNull
    private String type;

    @NotNull
    @Min(1)
    private Long projectId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(final List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public List<Long> getResourceOperationIdList() {
        return resourceOperationIdList;
    }

    public void setResourceOperationIdList(final List<Long> resourceOperationIdList) {
        this.resourceOperationIdList = resourceOperationIdList;
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
