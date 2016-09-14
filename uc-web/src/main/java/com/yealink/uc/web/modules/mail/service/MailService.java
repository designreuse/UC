package com.yealink.uc.web.modules.mail.service;

import com.yealink.uc.web.modules.mail.entity.MailConfig;
import com.yealink.uc.web.modules.mail.vo.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private MailProcessService mailProcess;
    // todo need change to mailconfig
    public void sendMail(Mail mail) {
        MailConfig mailConfig = new MailConfig();
        mailConfig.setServer("mail.yealink.com");mailConfig.setServer("mail.yealink.com");
        mailConfig.setPort(25);
        mailConfig.setPassword("FA230DB58B80E0825498D6A837C3ADFC");
        mailConfig.setUsername("vccloud@yealink.com");
        mailConfig.decryptPassword();
        mailProcess.sendMail(mailConfig, mail);
    }


}
