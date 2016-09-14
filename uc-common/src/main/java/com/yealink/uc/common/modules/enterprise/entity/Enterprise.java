package com.yealink.uc.common.modules.enterprise.entity;

import com.yealink.uc.common.modules.license.entity.License;
import com.yealink.uc.platform.annotations.Entity;

/**
 * @author ChNan
 */
@Entity(name = "Enterprise")
public class Enterprise {
    private Long _id;
    private String name;
    private String email;
    private String domain;
    private License license;
    private Long modificationDate;
    private Long parentId; // 0 表示系统默认创建，大于 0 表示为某个企业的子企业，-1 表示企业是自己注册的。
    private String logo;
    /**
     * @see com.yealink.uc.common.modules.enterprise.vo.EnterpriseType
     */
    private Long type;

    public Long get_id() {
        return _id;
    }

    public void set_id(final Long _id) {
        this._id = _id;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(final License license) {
        this.license = license;
    }

    public Long getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Long modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    public Long getType() {
        return type;
    }

    public void setType(final Long type) {
        this.type = type;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(final String logo) {
        this.logo = logo;
    }
}
