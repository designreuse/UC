package com.yealink.uc.platform.rest.exception;

import com.yealink.uc.platform.utils.MessageUtil;

/**
 * @author ChNan
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException() {

    }

    public BadRequestException(String message) {
        super(MessageUtil.getMessage(message));
    }

    public BadRequestException(String message, Object... objects) {
        super(MessageUtil.getMessage(message, objects));
    }
}
