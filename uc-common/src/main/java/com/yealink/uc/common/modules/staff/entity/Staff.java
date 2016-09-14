package com.yealink.uc.common.modules.staff.entity;


import java.util.List;

import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 */
@Entity(name = "Staff", code = 50)
public class Staff {
    private Long _id;

    private String name;

    private String email;

    private Long creationDate;

    private Long modificationDate;

    private Integer gender;

    private List<String> mobilePhones;

    private String workPhone;

    private String identity;

    private boolean isIdentityVisible;

    private String birthday;

    private boolean isBirthdayVisible;

    private String entryDate;

    private boolean isEntryDateVisible;

    private String position;

    private String number;

    /**
     * 在职状态
     */
    private Integer status;

    private String domain;

    private Long enterpriseId;

    private String description;

    private String avatar;

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

    public List<String> getMobilePhones() {
        return mobilePhones;
    }

    public void setMobilePhones(final List<String> mobilePhones) {
        this.mobilePhones = mobilePhones;
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


    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Long creationDate) {
        this.creationDate = creationDate;
    }

    public Long getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(final Long modificationDate) {
        this.modificationDate = modificationDate;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(final Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(final String avatar) {
        this.avatar = avatar;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Staff staff = (Staff) o;

        if (_id != null ? !_id.equals(staff._id) : staff._id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return _id != null ? _id.hashCode() : 0;
    }
}
