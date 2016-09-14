package com.yealink.uc.service.modules.account.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class EditAccountPasswordRESTRequest {
    @NotNull
    @Min(1)
    private Long accountId;
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;

    public EditAccountPasswordRESTRequest() {
    }

    public EditAccountPasswordRESTRequest(final Long accountId) {
        this.accountId = accountId;
    }

    public EditAccountPasswordRESTRequest(final Long accountId, final String oldPassword, final String newPassword) {
        this.accountId = accountId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }
}
