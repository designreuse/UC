package com.yealink.uc.platform.exception;

import com.yealink.uc.platform.utils.MessageUtil;

public class PermissionForbiddenException extends RuntimeException {
    public PermissionForbiddenException() {

    }

    public PermissionForbiddenException(String message) {
        super(MessageUtil.getMessage(message));
    }

    public PermissionForbiddenException(String message, Object... objects) {
        super(MessageUtil.getMessage(message, objects));
    }
}