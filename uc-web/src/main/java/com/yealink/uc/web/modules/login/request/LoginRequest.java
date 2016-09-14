package com.yealink.uc.web.modules.login.request;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author ChNan
 */
public class LoginRequest {
    @NotBlank(message = "{login.validator.username.null}")
    @Length(min = 1, max = 128, message = "{login.validator.username.too.long}")
//    @Pattern(regexp = CommonConstant.RULE_USERNAME, message = "{login.validator.username.invalid}")
    private String username;

    @NotBlank(message = "{login.validator.password.null}")
    @Length(min = 8, max = 64, message = "{login.validator.password.invalid}")
    private String password;

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

}
