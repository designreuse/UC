package com.yealink.uc.platform.rest.client;

import java.lang.reflect.Method;
import java.util.Map;

import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.http.HttpClient;
import com.yealink.uc.platform.http.HttpRequest;
import com.yealink.uc.platform.http.HttpResponse;
import com.yealink.uc.platform.rest.error.FieldError;
import com.yealink.uc.platform.rest.error.ErrorResponse;
import com.yealink.uc.platform.rest.exception.BadRequestException;
import com.yealink.uc.platform.rest.exception.RemoteServiceErrorException;
import com.yealink.uc.platform.utils.JSONConverter;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class RestClientInterceptor implements MethodInterceptor {
    private final Logger logger = LoggerFactory.getLogger(RestClientInterceptor.class);

    String serviceURL;
    Map<Method, PathVariableHandler> pathVariableHandlers;
    Map<Method, RequestBodyHandler> requestBodyHandlers;
    Map<Method, QueryVariableHandler> queryVariableHandlers;
    HttpClient httpClient = HttpClient.get();

    @Override
    public Object intercept(Object object, Method method, Object[] params, MethodProxy proxy) throws Throwable {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping == null)
            return proxy.invokeSuper(object, params);

        HttpRequest request = createHttpRequest(method, params, requestMapping);
        HttpResponse response = httpClient.execute(request);
        validateResponse(response);
        return response(method, response.returnResponseContent());
    }

    private HttpRequest createHttpRequest(final Method method, final Object[] params, final RequestMapping requestMapping) {
        HttpRequest request;
        String url = url(method, params, requestMapping.value()[0]);
        if (requestMapping.method().length == 0) {
            request = new HttpRequest(RequestBuilder.create(RequestMethod.POST.name()));
        } else {
            request = new HttpRequest(RequestBuilder.create(requestMapping.method()[0].name()));
        }
        request.uri(url);
        request.header(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString());
        RequestBodyHandler requestBodyHandler = requestBodyHandlers.get(method);
        if (requestBodyHandler != null) {
            request.entity(new StringEntity(requestBodyHandler.body(params), ContentType.APPLICATION_JSON));
        }
        return request;
    }

    private String url(Method method, Object[] params, String urlPattern) {
        StringBuilder url = new StringBuilder().append(serviceURL);
        PathVariableHandler pathVariableHandler = pathVariableHandlers.get(method);
        QueryVariableHandler queryVariableHandler = queryVariableHandlers.get(method);

        if (pathVariableHandler != null) {
            url.append(pathVariableHandler.url(urlPattern, params));
        } else {
            url.append(urlPattern);
        }

        if (queryVariableHandler != null) {
            url.append(queryVariableHandler.urlParams(params));
        }

        return url.toString();
    }


    private void validateResponse(HttpResponse response) {
        int statusCode = response.getStatusCode();
        if (statusCode >= HttpStatus.OK.value() && statusCode <= HttpStatus.IM_USED.value()) return;
        ErrorResponse ErrorResponse = JSONConverter.fromJSON(ErrorResponse.class, response.returnResponseContent());
        if (statusCode == HttpStatus.BAD_REQUEST.value()) {
            FieldError requestErrorItem = ErrorResponse.getFieldErrors().get(0);
            throw new BadRequestException(requestErrorItem.getErrorMessage());
        } else if (ErrorResponse.getExceptionClass().equals(BusinessHandleException.class.getSimpleName())) {
            throw new BusinessHandleException(ErrorResponse.getMessage());
        } else {
            throw new RemoteServiceErrorException(ErrorResponse.getMessage());
        }
    }


    private Object response(Method method, String responseBody) {
        Class<?> responseClass = method.getReturnType();
        if (Void.TYPE.equals(responseClass)) return null;
        return JSONConverter.fromJSON(responseClass, responseBody);
    }

}
