package com.yealink.uc.platform.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

/**
 * @author ChNan
 */
public class HttpRequest {
    RequestBuilder requestBuilder;

    public HttpRequest(RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public void uri(String uri) {
        requestBuilder.setUri(uri);
    }

    public void header(String headerName, String headerValue) {
        requestBuilder.setHeader(headerName, headerValue);
    }

    public void entity(HttpEntity entity) {
        requestBuilder.setEntity(entity);
    }

    public HttpUriRequest build(){
        return requestBuilder.build();
    }
}
