package com.yealink.uc.service.modules.org.vo;

import java.util.List;

/**
 * @author ChNan
 */
public class OrgView {
    private Long _id;

    private Long parentId;

    private String orgPath;

    private Integer index;

    private String mail;

    private Long enterpriseId;

    private boolean isExtAssistance;

    private String name;

    private List<String> phones;

    private Long modificationDate;

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    public String getOrgPath() {
        return orgPath;
    }

    public void setOrgPath(final String orgPath) {
        this.orgPath = orgPath;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(final Integer index) {
        this.index = index;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(final Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public boolean getIsExtAssistance() {
        return isExtAssistance;
    }

    public void setIsExtAssistance(final boolean isExtAssistance) {
        this.isExtAssistance = isExtAssistance;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(final List<String> phones) {
        this.phones = phones;
    }

    public Long getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(final Long modificationDate) {
        this.modificationDate = modificationDate;
    }

}
