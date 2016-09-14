package com.yealink.uc.web.modules.security.rolelevel.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class CreateRoleLevelRequest {
    @NotEmpty
    @Length(min = 1, max = 64)
//    @Pattern(regexp = "/^([\\u4E00-\\u9FA5]|\\w)*$/")
    public String name;

    private String description;

    private String code;
    private String type;

    private Integer priority;

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

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(final Integer priority) {
        this.priority = priority;
    }
}
