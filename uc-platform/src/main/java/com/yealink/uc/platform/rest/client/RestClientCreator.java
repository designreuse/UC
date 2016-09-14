package com.yealink.uc.platform.rest.client;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class RestClientCreator {
    final String serviceURL;

    public RestClientCreator(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    @SuppressWarnings("unchecked")
    public <T> T build(Class<T> serviceClass) {
        validateServiceClass(serviceClass);

        Map<Method, PathVariableHandler> pathVariableHandlers = Maps.newHashMap();
        Map<Method, RequestBodyHandler> requestBodyHandlers = Maps.newHashMap();
        Map<Method, QueryVariableHandler> queryVariableHandlers = Maps.newHashMap();

        for (Method method : serviceClass.getMethods()) {
            List<ParameterPosition> pathVariablePositions = Lists.newArrayList();
            List<ParameterPosition> queryVariablePositions = Lists.newArrayList();

            Annotation[][] annotations = method.getParameterAnnotations();
            for (int i = 0, length = annotations.length; i < length; i++) {
                Annotation[] paramAnnotations = annotations[i];
                bindPathVariable(pathVariablePositions, i, paramAnnotations);
                bindRequestBody(requestBodyHandlers, method, i, paramAnnotations);
                bindRequestParam(queryVariablePositions, i, paramAnnotations);
            }

            if (!pathVariablePositions.isEmpty())
                pathVariableHandlers.put(method, new PathVariableHandler(pathVariablePositions));

            if (!queryVariablePositions.isEmpty())
                queryVariableHandlers.put(method, new QueryVariableHandler(queryVariablePositions));
        }

        RestClientInterceptor interceptor = new RestClientInterceptor();
        interceptor.serviceURL = serviceURL;
        interceptor.pathVariableHandlers = pathVariableHandlers;
        interceptor.requestBodyHandlers = requestBodyHandlers;
        interceptor.queryVariableHandlers = queryVariableHandlers;
        return (T) Enhancer.create(serviceClass, interceptor);
    }

    private void bindRequestParam(final List<ParameterPosition> queryVariablePositions, final int i, final Annotation[] paramAnnotations) {
        RequestParam requestParam = findRequestParam(paramAnnotations);
        if (requestParam != null) {
            queryVariablePositions.add(new ParameterPosition(i, requestParam.value()));
        }
    }

    private void bindRequestBody(final Map<Method, RequestBodyHandler> requestBodyProcessors, final Method method, final int i, final Annotation[] paramAnnotations) {
        RequestBody requestBody = findRequestBody(paramAnnotations);
        if (requestBody != null) {
            requestBodyProcessors.put(method, new RequestBodyHandler(i));
        }
    }

    private void bindPathVariable(final List<ParameterPosition> pathVariablePositions, final int i, final Annotation[] paramAnnotations) {
        PathVariable pathVariable = findPathVariable(paramAnnotations);
        if (pathVariable != null) {
            pathVariablePositions.add(new ParameterPosition(i, pathVariable.value()));
        }
    }

    private PathVariable findPathVariable(Annotation[] paramAnnotations) {
        for (Annotation annotation : paramAnnotations) {
            if (PathVariable.class.equals(annotation.annotationType())) return (PathVariable) annotation;
        }
        return null;
    }

    private RequestParam findRequestParam(Annotation[] paramAnnotations) {
        for (Annotation annotation : paramAnnotations) {
            if (RequestParam.class.equals(annotation.annotationType())) return (RequestParam) annotation;
        }
        return null;
    }

    private RequestBody findRequestBody(Annotation[] paramAnnotations) {
        for (Annotation annotation : paramAnnotations) {
            if (RequestBody.class.equals(annotation.annotationType())) return (RequestBody) annotation;
        }
        return null;
    }

    private <T> void validateServiceClass(Class<T> serviceClass) {
        Preconditions.checkArgument(serviceClass.isInterface(), "serviceClass must be interface");
        for (Method method : serviceClass.getMethods()) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            Preconditions.checkNotNull(requestMapping, "@RequestMapping is required, method=%s", method);
        }
    }
}
