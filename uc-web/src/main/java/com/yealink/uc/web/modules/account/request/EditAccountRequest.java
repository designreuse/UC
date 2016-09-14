package com.yealink.uc.web.modules.account.request;

import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class EditAccountRequest {
    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    private String enterpriseName;

    private String oldPassword;
    private String newPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(final String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(final String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}
