package com.yealink.uc.platform.rest.exception;

import com.yealink.uc.platform.utils.MessageUtil;

/**
 * @author ChNan
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {

    }

    public ResourceNotFoundException(String message) {
        super(MessageUtil.getMessage(message));
    }

    public ResourceNotFoundException(String message, Object... objects) {
        super(MessageUtil.getMessage(message, objects));
    }
}
