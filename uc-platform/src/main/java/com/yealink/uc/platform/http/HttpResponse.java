package com.yealink.uc.platform.http;

import org.apache.http.Header;

/**
 * @author ChNan
 */
public class HttpResponse {

    private String responseContent;

    private int statusCode;

    private Header[] headers;

    public HttpResponse(final String responseContent, final int statusCode, final Header[] headers) {
        this.responseContent = responseContent;
        this.statusCode = statusCode;
        this.headers = headers;
    }

    public String returnResponseContent() {
        return responseContent;
    }

    public Header[] listAllHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;

    }
}
