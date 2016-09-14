package com.yealink.uc.web.modules.account.request;

import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class EditAccountPasswordRequest {
    @NotNull
    private String oldPassword;
    @NotNull
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
}
