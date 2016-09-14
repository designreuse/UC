package com.yealink.uc.web.modules.account.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.MailUtil;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.VelocityUtil;
import com.yealink.uc.web.modules.account.dao.AccountDao;
import com.yealink.uc.web.modules.mail.service.MailService;
import com.yealink.uc.web.modules.mail.vo.Mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * @author ChNan
 */
@Service
public class AccountMailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountMailService.class);
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private MailService mailService;

    @Value("${site.host}")
    private String siteHost;

    private static final String RESET_PASSWORD_URL = "%s/account/password/forwardToReset?activeCode=%s";
    private static final String BIND_EMAIL_ACTIVE_URL = "%s/account/bind/activeEmail?activeCode=%s";


    public void sendResetPasswordMail(String mailAddressToSend, String randomPassword, String activeCode) {
        if (!MailUtil.isEmail(mailAddressToSend)) {
            throw new BusinessHandleException("account.tips.mail.format.error");
        }
        try {
            String resetPwdUrl = String.format(RESET_PASSWORD_URL, siteHost, activeCode);
            Map<String, Object> map = buildForgotPasswordMailMap(activeCode, resetPwdUrl, mailAddressToSend, randomPassword);
            String content = VelocityEngineUtils.mergeTemplateIntoString(VelocityUtil.getVelocityEngine(), "forgot_password.vm", "utf-8", map);
            String subject = MessageUtil.getMessage("mail.template.subject.password.forget");
            mailService.sendMail(convertTemplateToMail(mailAddressToSend, content, subject));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void sendBindEmailActiveMail(String receiver, String activeCode) {
        if (!MailUtil.isEmail(receiver)) {
            throw new BusinessHandleException("account.tips.mail.format.error");
        }
        try {
            String bindEmailUrl = String.format(BIND_EMAIL_ACTIVE_URL, siteHost, activeCode);
            Map<String, Object> map = buildBindEmailActiveMailMap(receiver, bindEmailUrl);
            String content = VelocityEngineUtils.mergeTemplateIntoString(VelocityUtil.getVelocityEngine(), "bind_email.vm", "utf-8", map);
            String subject = MessageUtil.getMessage("mail.template.subject.bind.email");
            mailService.sendMail(convertTemplateToMail(receiver, content, subject));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private Map<String,Object> buildBindEmailActiveMailMap(String receiver, String bindEmailUrl) {
        Map<String, Object> map = new HashMap<>();
        map.put("welcomeMsg", MessageUtil.getMessage("mail.template.contact.precede", receiver));
        map.put("bindEmailMsg", MessageUtil.getMessage("mail.template.content.bind.email.line1"));
        map.put("clickUrlMsg", "点击绑定");
        map.put("bindEmailUrl", bindEmailUrl);
        map.put("teamName", MessageUtil.getMessage("mail.template.contact.team"));
        map.put("companyName", MessageUtil.getMessage("system.common.title.copyright", Integer.toString(Calendar.getInstance().get(Calendar.YEAR))));
        return map;
    }

    private Map<String, Object> buildForgotPasswordMailMap(String activeCode, String resetPwdUrl, String mail, String randomPassword) {
        Map<String, Object> map = new HashMap<>();
        map.put("welcomeMsg", MessageUtil.getMessage("mail.template.contact.precede", mail));
        map.put("receivedRequestMsg", MessageUtil.getMessage("mail.template.content.password.forget.line1"));
        map.put("resetNewPasswordMsg", MessageUtil.getMessage("mail.template.content.password.forget.line2"));
        map.put("randomPasswordMsg", MessageUtil.getMessage("mail.template.content.password.forget.line6"));
        map.put("randomPassword", randomPassword);
        map.put("resetPwdUrl", resetPwdUrl);
        map.put("activeCode", activeCode);
        map.put("clickUrlMsg", MessageUtil.getMessage("mail.template.content.password.forget.line3"));
        map.put("copyUrlMsg", MessageUtil.getMessage("mail.template.content.password.forget.line4"));
        map.put("ignoreMessage", MessageUtil.getMessage("mail.template.content.password.forget.line5"));
        map.put("teamName", MessageUtil.getMessage("mail.template.contact.team"));
        map.put("companyName", MessageUtil.getMessage("system.common.title.copyright", Integer.toString(Calendar.getInstance().get(Calendar.YEAR))));
        return map;
    }

    private Mail convertTemplateToMail(String receiver, String content, String subject) {
        Mail mail = new Mail();
        mail.setReceivers(new String[]{receiver});
        mail.setSubject(subject);
        mail.setContent(content);
        return mail;
    }

}
