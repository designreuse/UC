package com.yealink.uc.api.modules.account.vo;

/**
 * @author ChNan
 */
public class UCAccount {
    private String username;
    private Long accountId;

    public UCAccount() {
    }

    public UCAccount(String username, String name,Long accountId) {
        this.username = username;
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }
}
