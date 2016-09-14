package com.yealink.uc.platform.exception;

import com.yealink.uc.platform.utils.MessageUtil;

public class SessionTimeoutException extends RuntimeException {
    public SessionTimeoutException() {

    }

    public SessionTimeoutException(String message) {
        super(MessageUtil.getMessage(message));
    }

    public SessionTimeoutException(String message, Object... objects) {
        super(MessageUtil.getMessage(message, objects));
    }
}