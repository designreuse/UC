package com.yealink.uc.service.modules.staff.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * @author ChNan
 */
public class UpdateStaffsRESTRequest {
    @NotNull
    @Length(max = 32)
    private String username;

    @NotNull
    @Length(max = 32)
    private String name;
    @NotNull
    private String password;

    private Integer pinCode;

    // 基础信息
    private String email;

    // 男：1 女：0
    @Min(value = 0)
    @Max(value = 1)
    private Integer gender;

    private String workPhone;

    private String identity;

    private boolean isIdentityVisible;

    private String birthday;

    private boolean isBirthdayVisible;

    private String entryDate;

    private boolean isEntryDateVisible;

    private String position;

    private String number;

    // 话机 分机信息
    private String phoneMac;

    private String phoneIP;

    private String phoneModel;

    private String phoneSettingTemplate;

    // Extension 分机信息
    private String extensionNumber;

    private Integer extensionPinCode;

    private String extensionPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(final Integer gender) {
        this.gender = gender;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(final String workPhone) {
        this.workPhone = workPhone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(final String identity) {
        this.identity = identity;
    }

    public boolean isIdentityVisible() {
        return isIdentityVisible;
    }

    public void setIdentityVisible(final boolean isIdentityVisible) {
        this.isIdentityVisible = isIdentityVisible;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(final String birthday) {
        this.birthday = birthday;
    }

    public boolean isBirthdayVisible() {
        return isBirthdayVisible;
    }

    public void setBirthdayVisible(final boolean isBirthdayVisible) {
        this.isBirthdayVisible = isBirthdayVisible;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(final String entryDate) {
        this.entryDate = entryDate;
    }

    public boolean isEntryDateVisible() {
        return isEntryDateVisible;
    }

    public void setEntryDateVisible(final boolean isEntryDateVisible) {
        this.isEntryDateVisible = isEntryDateVisible;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(final String position) {
        this.position = position;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public String getPhoneMac() {
        return phoneMac;
    }

    public void setPhoneMac(final String phoneMac) {
        this.phoneMac = phoneMac;
    }

    public String getPhoneIP() {
        return phoneIP;
    }

    public void setPhoneIP(final String phoneIP) {
        this.phoneIP = phoneIP;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(final String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneSettingTemplate() {
        return phoneSettingTemplate;
    }

    public void setPhoneSettingTemplate(final String phoneSettingTemplate) {
        this.phoneSettingTemplate = phoneSettingTemplate;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(final String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public Integer getExtensionPinCode() {
        return extensionPinCode;
    }

    public void setExtensionPinCode(final Integer extensionPinCode) {
        this.extensionPinCode = extensionPinCode;
    }

    public String getExtensionPassword() {
        return extensionPassword;
    }

    public void setExtensionPassword(final String extensionPassword) {
        this.extensionPassword = extensionPassword;
    }
}
