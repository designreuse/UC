package com.yealink.uc.web.modules.mail.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.yealink.uc.platform.utils.SecurityUtil;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class MailConfig {
    private int accountId;

    @NotEmpty(message = "mail.config.validator.server.null")
    @Length(min = 0, max = 128, message = "mail.config.validator.server.too.long")
    private String server;

    @Min(value = 1, message = "mail.config.validator.port.invalid")
    @Max(value = 65535, message = "mail.config.validator.port.invalid")
    private int port;

    @Email(message = "mail.config.validator.username.invalid")
    @Length(min = 0, max = 128, message = "mail.config.validator.username.too.long")
    private String username;

    @NotEmpty(message = "mail.config.validator.password.null")
    @Length(min = 0, max = 32, message = "mail.config.validator.server.too.long")
    private String password;

    public MailConfig() {
        port = 25;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 将邮箱配置的密码解密成明文.
     */
    public void decryptPassword() {
        password = SecurityUtil.decryptTripleDes(password);
    }

    /**
     * 加密邮箱配置的密码.
     */
    public void encryptPassword() {
        password = SecurityUtil.encryptTripleDes(password);
    }
}
