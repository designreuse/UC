package com.yealink.uc.web.modules.security.role.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class CreateRoleRequest {
    @NotEmpty
    @Length(min = 1, max = 64)
//    @Pattern(regexp = "/^([\\u4E00-\\u9FA5]|\\w)*$/")
    public String name;

    private String description;
    @Min(1)
    @NotNull
    private Long roleLevelId;

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

    public Long getRoleLevelId() {
        return roleLevelId;
    }

    public void setRoleLevelId(final Long roleLevelId) {
        this.roleLevelId = roleLevelId;
    }
}
