package com.yealink.uc.service.modules.login.response;

import com.yealink.uc.service.modules.account.vo.UCAccountView;

/**
 * @author ChNan
 */
public class LoginRESTResponse {
    private UCAccountView ucAccount;
    private Long errorCount;

    public UCAccountView getUcAccount() {
        return ucAccount;
    }

    public void setUcAccount(final UCAccountView ucAccount) {
        this.ucAccount = ucAccount;
    }

    public Long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(final Long errorCount) {
        this.errorCount = errorCount;
    }
}
