package com.yealink.uc.web.modules.account.request;

import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class ResetPasswordRequest {
    @NotNull
    private String activeCode;
    @NotNull
    private String newPassword;

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(final String activeCode) {
        this.activeCode = activeCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(final String newPassword) {
        this.newPassword = newPassword;
    }
}
