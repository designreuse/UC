package com.yealink.uc.web.modules.staff.response;

/**
 * @author ChNan
 */
public class StaffExtension {
    private String number;
    private String password;
    private Integer pinCode;

    private boolean status;
    public StaffExtension() {
    }

    public StaffExtension(final String number, final String password, final Integer pinCode, final boolean status) {
        this.number = number;
        this.password = password;
        this.pinCode = pinCode;
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(final Integer pinCode) {
        this.pinCode = pinCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }
}
