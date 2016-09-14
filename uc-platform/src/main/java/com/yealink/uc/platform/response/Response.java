package com.yealink.uc.platform.response;

/**
 * @author ChNan
 */
public class Response<T> {
    private boolean success;
    private String message;
    private String url;
    private T data;

    private PageModel pageModel;

    private Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Response(final T data, final boolean success) {
        this.data = data;
        this.success = success;
    }

    public Response(final PageModel pageModel, final boolean success) {
        this.pageModel = pageModel;
        this.success = success;
    }

    private Response(boolean success, String message, String url) {
        this.success = success;
        this.url = url;
        this.message = message;
    }

    public static Response buildResponse(boolean success, String message) {
        return new Response(success, message);
    }

    public static Response buildResponse(boolean success, String message, String url) {
        return new Response(success, message, url);
    }
    public static Response buildResponseURL(boolean success, String url) {
        return new Response(success, "", url);
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Response(final String url) {
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

    public PageModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(final PageModel pageModel) {
        this.pageModel = pageModel;
    }
}
