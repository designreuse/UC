package com.yealink.uc.web.modules.common.exception;


import com.yealink.uc.platform.utils.MessageUtil;

public class MailException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public static final String EXCEPTION_MAIL = "exception.mail";
    public static final String EXCEPTION_MAIL_SEND_FAILED = "exception.mail.send.failed";
    public static final String EXCEPTION_MAIL_UNOPEN_SEND_BOX = "exception.mail.unopen.send.box";
    public static final String EXCEPTION_MAIL_UNCLOSE_SEND_BOX = "exception.mail.unclose.send.box";
    public static final String EXCEPTION_MAIL_ADDRESS_NOT_FOUND = "exception.mail.address.not.found";
    public static final String EXCEPT_EMAIL_SEND_TOO_MANY = "exception.mail.send.too.many.times";

    public MailException(String message) {
        super(MessageUtil.getMessage(message));
    }

    public MailException(String message, Exception e) {
        super(MessageUtil.getMessage(message), e);
    }
}
