package com.yealink.uc.platform.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.yealink.uc.platform.rest.error.ErrorResponse;

/**
 * @author ChNan
 */
public class APIResponse<T> {
    private boolean success;
    private T data;
    private ErrorResponse error;

    private APIResponse(boolean success, ErrorResponse error) {
        this.success = success;
        this.error = error;
    }

    private APIResponse(final T data, final boolean success) {
        this.data = data;
        this.success = success;
    }


    public static <T> APIResponse buildResponse(T data) {
        return new APIResponse<>(data, true);
    }

    public static APIResponse buildErrorResponse(ErrorResponse errorResponse) {
        return new APIResponse(false, errorResponse);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(final ErrorResponse error) {
        this.error = error;
    }
}
