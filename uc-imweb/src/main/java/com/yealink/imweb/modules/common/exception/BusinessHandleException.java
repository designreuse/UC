package com.yealink.imweb.modules.common.exception;

import com.yealink.uc.platform.utils.MessageUtil;

public class BusinessHandleException extends RuntimeException {
    public BusinessHandleException() {

    }

    public BusinessHandleException(String message) {
        super(MessageUtil.getMessage(message));
    }

    public BusinessHandleException(String message, Object... objects) {
        super(MessageUtil.getMessage(message, objects));
    }
}