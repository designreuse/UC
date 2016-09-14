package com.yealink.uc.api.modules.account.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class EditAccountPwdAPIRequest {
    @NotNull
    @Min(1)
    private Long accountId;
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
    @NotNull
    private String randomAESEncryptKey;

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

    public String getRandomAESEncryptKey() {
        return randomAESEncryptKey;
    }

    public void setRandomAESEncryptKey(final String randomAESEncryptKey) {
        this.randomAESEncryptKey = randomAESEncryptKey;
    }
}
