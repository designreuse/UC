package com.yealink.uc.web.modules.staff.request;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

/**
 * @author ChNan
 */
public class StaffCommonRequest {
    // UC主账号信息，IM账号
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

    private boolean isSelectPhoneSetting;

    private boolean isSelectExtensionSetting;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(final String birthday) {
        this.birthday = birthday;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(final String entryDate) {
        this.entryDate = entryDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(final String position) {
        this.position = position;
    }

    public boolean getIsIdentityVisible() {
        return isIdentityVisible;
    }

    public void setIsIdentityVisible(final boolean isIdentityVisible) {
        this.isIdentityVisible = isIdentityVisible;
    }

    public boolean getIsBirthdayVisible() {
        return isBirthdayVisible;
    }

    public void setIsBirthdayVisible(final boolean isBirthdayVisible) {
        this.isBirthdayVisible = isBirthdayVisible;
    }

    public boolean getIsEntryDateVisible() {
        return isEntryDateVisible;
    }

    public void setIsEntryDateVisible(final boolean isEntryDateVisible) {
        this.isEntryDateVisible = isEntryDateVisible;
    }

    private List<String> mobilePhones;

    public List<String> getMobilePhones() {
        return mobilePhones;
    }

    public void setMobilePhones(final List<String> mobilePhones) {
        this.mobilePhones = mobilePhones;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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

    public String getExtensionPassword() {
        return extensionPassword;
    }

    public void setExtensionPassword(final String extensionPassword) {
        this.extensionPassword = extensionPassword;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(final Integer pinCode) {
        this.pinCode = pinCode;
    }

    public Integer getExtensionPinCode() {
        return extensionPinCode;
    }

    public void setExtensionPinCode(final Integer extensionPinCode) {
        this.extensionPinCode = extensionPinCode;
    }

    public boolean getIsSelectPhoneSetting() {
        return isSelectPhoneSetting;
    }

    public void setIsSelectPhoneSetting(final boolean isSelectPhoneSetting) {
        this.isSelectPhoneSetting = isSelectPhoneSetting;
    }

    public boolean getIsSelectExtensionSetting() {
        return isSelectExtensionSetting;
    }

    public void setIsSelectExtensionSetting(final boolean isSelectExtensionSetting) {
        this.isSelectExtensionSetting = isSelectExtensionSetting;
    }
}
