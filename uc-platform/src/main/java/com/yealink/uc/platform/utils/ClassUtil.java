package com.yealink.uc.platform.utils;

import java.lang.annotation.Annotation;

import org.springframework.web.method.HandlerMethod;

public class ClassUtil {
    /**
     * like full class is : com.yealink.ucweb.utils.ClassUtils
     * generic class name is ClassUtils
     */
    public static String getSimpleClassName(Object o) {
        String fullClassName = o.getClass().getName();
        if (fullClassName.contains("$$EnhancerBySpringCGLIB")) {
            fullClassName = o.getClass().getSuperclass().getName();
        }
        int lastDotIndex = fullClassName.lastIndexOf('.');
        if (lastDotIndex > -1) {
            return fullClassName.substring(lastDotIndex + 1);
        }
        return fullClassName;
    }


    public static <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
        T annotation = handler.getBeanType().getAnnotation(annotationType);
        if (annotation != null) return annotation;
        return handler.getMethodAnnotation(annotationType);
    }
}
