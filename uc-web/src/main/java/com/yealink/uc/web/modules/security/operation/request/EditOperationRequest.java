package com.yealink.uc.web.modules.security.operation.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class EditOperationRequest {
    @Min(1)
    @NotNull
    private Long id;

    @NotEmpty
    @Length(min = 1, max = 64)
//    @Pattern(regexp = "/^([\\u4E00-\\u9FA5]|\\w)*$/")
    public String name;

    private String description;

    private String code;

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

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }
}
