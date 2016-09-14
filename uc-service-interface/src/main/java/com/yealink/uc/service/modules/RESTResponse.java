package com.yealink.uc.service.modules;

/**
 * @author ChNan
 */
public class RESTResponse<T> {
    private boolean success;
    private String message;
    private String url;
    private T data;

    public RESTResponse() {
    }

    private RESTResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public RESTResponse(final T data, final boolean success) {
        this.data = data;
        this.success = success;
    }

    private RESTResponse(boolean success, String message, String url) {
        this.success = success;
        this.url = url;
        this.message = message;
    }

    public static RESTResponse buildResponse(boolean success, String message) {
        return new RESTResponse(success, message);
    }

    public static RESTResponse buildResponse(boolean success, String message, String url) {
        return new RESTResponse(success, message, url);
    }

    public boolean getIsSuccess() {
        return success;
    }

    public void setIsSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RESTResponse(final String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
    }
}
