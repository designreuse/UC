package com.yealink.uc.api.modules.login.request;

import java.util.List;
import javax.validation.constraints.Pattern;

import com.yealink.uc.common.modules.common.CommonConstant;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class LoginAPIRequest {
    @NotBlank(message = "{login.validator.username.null}")
    @Length(min = 1, max = 128, message = "{login.validator.username.too.long}")
    @Pattern(regexp = CommonConstant.RULE_USERNAME, message = "{login.validator.username.invalid}")
    private String username;

    @NotBlank(message = "{login.validator.password.null}")
    @Length(min = 8, max = 64, message = "{login.validator.password.invalid}")
    private String password;

    private String domain;

    @NotBlank(message = "{login.validator.account.type.null}")
    private String accountType;

    @NotEmpty(message = "{login.validator.business.type.null}")
    private List<String> businessTypes;

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    public List<String> getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(final List<String> businessTypes) {
        this.businessTypes = businessTypes;
    }
}
