package com.yealink.uc.web.modules.enterprise.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * Created by yewl.
 */
public class EditEnterpriseRequest {
    private Long id;

    @NotNull
    @Length(max = 64)
    private String name;

    private String logo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(final String logo) {
        this.logo = logo;
    }
}
