package com.yealink.uc.platform.rest.exception;

import com.yealink.uc.platform.utils.MessageUtil;

/**
 * @author ChNan
 */
public class AuthorizationFailedException extends RuntimeException {
    public AuthorizationFailedException() {

    }

    public AuthorizationFailedException(String message) {
        super(MessageUtil.getMessage(message));
    }

    public AuthorizationFailedException(String message, Object... objects) {
        super(MessageUtil.getMessage(message, objects));
    }
}
