package com.yealink.uc.web.modules.staff.response;

/**
 * @author ChNan
 */
public class UCAccountDetail {
    private String username;
    private String plainPassword;
    private Integer pinCode;

    public UCAccountDetail(final String username, final String plainPassword, final Integer pinCode) {
        this.username = username;
        this.plainPassword = plainPassword;
        this.pinCode = pinCode;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(final Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(final String plainPassword) {
        this.plainPassword = plainPassword;
    }
}
