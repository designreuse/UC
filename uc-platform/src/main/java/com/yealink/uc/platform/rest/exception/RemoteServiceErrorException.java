package com.yealink.uc.platform.rest.exception;

import com.yealink.uc.platform.utils.MessageUtil;

/**
 * @author ChNan
 */
public class RemoteServiceErrorException extends RuntimeException {
    public RemoteServiceErrorException() {

    }

    public RemoteServiceErrorException(String message) {
        super(MessageUtil.getMessage(message));
    }

    public RemoteServiceErrorException(String message, Object... objects) {
        super(MessageUtil.getMessage(message, objects));
    }
}
