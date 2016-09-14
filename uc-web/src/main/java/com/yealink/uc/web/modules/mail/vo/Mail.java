package com.yealink.uc.web.modules.mail.vo;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Email;

public class Mail {
    @Valid
    @Email(message = "{mail.validator.receiver.invalid}")
    private String[] receivers;

    private String subject;

    private String content;

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    private String[] cc;
}
