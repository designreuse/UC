package com.yealink.uc.platform.rest.error;

import com.yealink.uc.platform.utils.MessageUtil;

import org.springframework.http.HttpStatus;


public class ErrorResponseBuilder {

    public static ErrorResponse createErrorResponse(Throwable e, HttpStatus httpStatus) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        error.setExceptionClass(e.getClass().getSimpleName());
        error.setExceptionTrace(MessageUtil.traceExceptionMessage(e));
        error.setStatusCode(httpStatus.value());
        return error;
    }
}