package com.yealink.uc.service.modules.login.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author ChNan
 */
public class LoginRESTRequest {
    @NotBlank(message = "{login.validator.username.null}")
    @Length(min = 1, max = 128, message = "{login.validator.username.too.long}")
//    @Pattern(regexp = CommonConstant.RULE_USERNAME, message = "{login.validator.username.invalid}")
    private String username;

    @NotBlank(message = "{login.validator.password.null}")
    @Length(min = 8, max = 64, message = "{login.validator.password.invalid}")
    private String password;

    private String accountType;

    private String domain;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }
}
