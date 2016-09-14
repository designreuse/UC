package com.yealink.uc.web.modules.mail.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.yealink.uc.platform.utils.MailUtil;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.web.modules.common.exception.MailException;
import com.yealink.uc.web.modules.mail.entity.MailConfig;
import com.yealink.uc.web.modules.mail.vo.Mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The Class MailProcess.
 *
 * @author yl0906
 */
@Service
public class MailProcessService {
    @Value("${mail.time.out.establish}")
    private String timeoutEstablish;

    @Value("${mail.time.out.send}")
    private String timeoutSend;

    private static final int DEFAULT_PORT = 25;

    public void validateConnection(MailConfig mailConfig) throws MailException {
        Transport transport = null;
        try {
            transport = getTransport(mailConfig);
        } finally {
            closeTransport(transport);
        }
    }

    private Transport getTransport(MailConfig mailConfig) throws MailException {
        try {
            Session session = getSession(mailConfig.getPort());
            Transport transport = session.getTransport("smtp");
            if (transport == null) {
                throw new MailException(MailException.EXCEPTION_MAIL_UNOPEN_SEND_BOX);
            }
            transport.connect(mailConfig.getServer(), mailConfig.getPort(),
                mailConfig.getUsername(), mailConfig.getPassword());
            if (!transport.isConnected()) {
                throw new MailException(MailException.EXCEPTION_MAIL_UNOPEN_SEND_BOX);

            }
            return transport;
        } catch (MessagingException e) {
            throw new MailException(MailException.EXCEPTION_MAIL);
        }
    }

    private Session getSession(int port) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.connectiontimeout", timeoutEstablish);
        props.setProperty("mail.smtp.timeout", timeoutSend);
        if (port != DEFAULT_PORT) {
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
            props.put("mail.smtp.starttls.enable", "true");
        }
        return Session.getInstance(props);
    }

    private void closeTransport(Transport transport) throws MailException {
        try {
            if (transport != null && transport.isConnected()) {
                transport.close();
            }
        } catch (MessagingException e) {
            throw new MailException(MessageUtil.getMessage(MailException.EXCEPTION_MAIL_UNCLOSE_SEND_BOX));
        }
    }

    public void sendMail(MailConfig mailConfig, Mail mail) {
        validateMailConfig(mailConfig);
        validateMail(mail);
        Transport transport = null;
        try {
            transport = getTransport(mailConfig);
            sendMessage(mailConfig, transport, mail);
        } catch (MessagingException e) {
            throw new MailException(MessageUtil.getMessage(MailException.EXCEPTION_MAIL_SEND_FAILED));
        } finally {
            closeTransport(transport);
        }
    }

    private void validateMail(Mail mail) throws MailException {
        if (mail == null || mail.getReceivers() == null) {
            throw new MailException(MailException.EXCEPTION_MAIL_SEND_FAILED);
        }
        for (String receiver : mail.getReceivers()) {
            if (!MailUtil.isEmail(receiver)) {
                throw new MailException(MailException.EXCEPTION_MAIL_SEND_FAILED);
            }
        }
        if (mail.getContent() == null) {
            mail.setContent("");
        }
        if (mail.getSubject() == null) {
            mail.setSubject("");
        }
    }

    private void validateMailConfig(MailConfig mailConfig) throws MailException {
        if (mailConfig == null || mailConfig.getServer() == null || mailConfig.getPassword() == null) {
            throw new MailException(MailException.EXCEPTION_MAIL);
        }
        if (mailConfig.getPort() < 1 || mailConfig.getPort() > 65535) {
            throw new MailException(MailException.EXCEPTION_MAIL);
        }
        if (mailConfig.getUsername() == null || !MailUtil.isEmail(mailConfig.getUsername())) {
            throw new MailException(MailException.EXCEPTION_MAIL);
        }
    }

    public void batchSendEmail(MailConfig mailConfig, List<Mail> mailList) throws MailException {
        validateMailConfig(mailConfig);
        for (Mail mail : mailList) {
            validateMail(mail);
        }
        Transport transport = null;
        try {
            transport = getTransport(mailConfig);
            int size = mailList.size();
            for (Mail mail : mailList) {
                sendMessage(mailConfig, transport, mail);
            }
        } catch (MessagingException e) {
            throw new MailException(MailException.EXCEPTION_MAIL_SEND_FAILED);
        } finally {
            closeTransport(transport);
        }
    }

    private void sendMessage(MailConfig mailConfig, Transport transport,
                             Mail mail) throws MessagingException {
        MimeMessage message = new MimeMessage(getSession(mailConfig.getPort()));
        message.setFrom(new InternetAddress(mailConfig.getUsername()));
        String subject;
        try {
            subject = MimeUtility.encodeWord(mail.getSubject(), "UTF-8", "Q");
        } catch (UnsupportedEncodingException e) {
            subject = mail.getSubject();
        }
        message.setSubject(subject);
        message.setContent(mail.getContent(), "text/html;charset=utf-8");
        message.setSentDate(new Date());

        String[] receivers = mail.getReceivers();
        Address[] mailReceives = new Address[receivers.length];
        int j = 0;
        for (String to : receivers) {
            Address address = new InternetAddress(to);
            mailReceives[j++] = address;
        }
        transport.sendMessage(message, mailReceives);
    }
}
