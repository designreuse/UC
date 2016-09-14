package com.yealink.uc.platform.utils;

import com.yealink.uc.platform.annotations.Entity;
import com.yealink.uc.platform.exception.BusinessHandleException;

import org.springframework.util.StringUtils;

/**
 * @author ChNan
 */
public class EntityUtil {
    public static String getEntityName(Class<?> entity) {
        String name = entity.getAnnotation(Entity.class).name();
        if (!StringUtils.hasText(name)) throw new BusinessHandleException("system.entity.get.error.null", entity.getSimpleName());
        return name;
    }

    public static int getEntityCode(Class<?> entity) {
        return entity.getAnnotation(Entity.class).code();
    }
}
