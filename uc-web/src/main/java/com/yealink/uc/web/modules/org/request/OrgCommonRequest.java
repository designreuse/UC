package com.yealink.uc.web.modules.org.request;

import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class OrgCommonRequest {
    @NotEmpty
    @Length(min = 1, max = 64)
//    @Pattern(regexp = "/^([\\u4E00-\\u9FA5]|\\w)*$/")
    public String name;

    @Email
    private String mail;

    private boolean isExtAssistance;

    private List<String> phones;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }

    public boolean getIsExtAssistance() {
        return isExtAssistance;
    }

    public void setIsExtAssistance(final boolean isExtAssistance) {
        this.isExtAssistance = isExtAssistance;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(final List<String> phones) {
        this.phones = phones;
    }
}
