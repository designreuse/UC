package com.yealink.uc.platform.utils;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ChNan
 */
public class DataConverter {
    Logger logger = LoggerFactory.getLogger(DataConverter.class);

    private static  List<Field> sourceFields;
    private static  List<Field> targetFields;

    private <T> T doConvertFromMap(T t, Map<String, Object> s) {
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field tarField : fields) {
            tarField.setAccessible(true);
            for (String srcField : s.keySet()) {
                if (tarField.getName().equalsIgnoreCase(srcField)) {
                    try {
                        if (tarField.getType().getSimpleName().equals("Long") && s.get(srcField) != null) {
                            tarField.set(t, Long.parseLong(String.valueOf(s.get(srcField))));
                        } else {
                            tarField.set(t, s.get(srcField));
                        }
                    } catch (Exception e) {
                        logger.error(MessageUtil.traceExceptionMessage(e));
                        continue;
                    }
                }
            }
        }
        return t;
    }

    public static <T> T copyFromMap(final T t, final Map<String, Object> s) {
        if (s == null) return null;
        return AccessController.doPrivileged(new PrivilegedAction<T>() {
            @Override
            public T run() {
                return new DataConverter().doConvertFromMap(t, s);
            }
        });
    }

    private <T, S> T doConvert(final T t, final S s) {
        for (Field tarField : targetFields) {
            tarField.setAccessible(true);
            for (Field srcField : sourceFields) {
                srcField.setAccessible(true);
                if (tarField.getName().equals(srcField.getName()) && tarField.getType().equals(srcField.getType())) {
                    try {
                        tarField.set(t, srcField.get(s));
                    } catch (Exception e) {
                        logger.error(MessageUtil.traceExceptionMessage(e));
                        continue;
                    }
                }
            }
        }

        return t;
    }

    public static <T, S> T copy(final T target, final S source) {
        if (source == null) return null;
        sourceFields= new ArrayList<>();
        targetFields= new ArrayList<>();
        return AccessController.doPrivileged(new PrivilegedAction<T>() {
            @Override
            public T run() {
                listAllFields(source.getClass(), sourceFields);
                listAllFields(target.getClass(), targetFields);
                return new DataConverter().doConvert(target, source);
            }
        });
    }

    private static void listAllFields(Class clazz, List<Field> fields) {
        List<Field> declaredFields = Arrays.asList(clazz.getDeclaredFields());
        fields.addAll(declaredFields);
        if (clazz.getSuperclass() != null && !clazz.getSuperclass().getSimpleName().equals("Object")) { // for super class
            listAllFields(clazz.getSuperclass(), fields);
        }
    }
}
