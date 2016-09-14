package com.yealink.imweb.modules.common.util;


import com.yealink.imweb.modules.common.annotation.Entity;
import com.yealink.imweb.modules.common.exception.BusinessHandleException;
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
