package com.yealink.imweb.modules.common.util;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

/**
 * List排序工具
 *
 * @author Ken.xu(mailto:xzknet@gmail.com)
 * @version 1.0 Copyright 2012-9-18 下午04:37:17
 * @param <T>
 */
public class ListSortUtil<T> {
    private Map<Method, Direction> sortField = new LinkedHashMap<Method, Direction>();
    private Map<String, Method> propertyMethodMap = null;

    // Method[] methods
    public ListSortUtil(final Class clazz) {
        PropertyDescriptor[] propertyDescriptor = BeanUtils.getPropertyDescriptors(clazz);
        Map<String, Method> propertyMethodMap = new HashMap<String, Method>();
        for (PropertyDescriptor pd : propertyDescriptor) {
            String key = pd.getName();
            Method value = pd.getReadMethod();
            propertyMethodMap.put(key, value);
        }
        this.propertyMethodMap = propertyMethodMap;
    }

    public void clear() {
        sortField.clear();
    }

    /**
     * 增加一个降序
     *
     * @param fieldName
     * @throws NoSuchMethodException
     * @author Ken_xu
     */
    public void addDesc(String fieldName) throws NoSuchMethodException {
        addFieldMethod(fieldName, Direction.DESC);
    }

    /**
     * 增加一个升序
     *
     * @param fieldName
     * @throws NoSuchMethodException
     * @author Ken_xu
     */
    public void addAsc(String fieldName) throws NoSuchMethodException {
        addFieldMethod(fieldName, Direction.ASC);
    }

    /**
     * 增加一个字段排序模式
     *
     * @param fieldName
     * @param direction
     * @throws NoSuchMethodException
     * @author Ken_xu
     */
    private void addFieldMethod(String fieldName, Direction direction) throws NoSuchMethodException {
        Method method = propertyMethodMap.get(fieldName);
        if (method == null) {
            throw new NoSuchMethodException(fieldName);
        } else {
            sortField.put(method, direction);
        }
    }

    public List<T> sortList(List<T> list) {
        if (sortField.isEmpty() == false) {
            Comparator<T> comparator = new Comparator<T>() {
                public int compare(T o1, T o2) {
                    int flag = 0;
                    for (Map.Entry<Method, Direction> entry : sortField.entrySet()) {
                        Method method = entry.getKey();
                        Direction direction = entry.getValue();
                        if (direction == Direction.ASC) {
                            // DESC:降序
                            flag = this.compareByFlag(method, o1, o2);
                        } else {
                            // ASC:升序
                            flag = this.compareByFlag(method, o2, o1);
                        }
                        if (flag != 0) {
                            break;
                        }
                    }
                    if (flag > 0) {
                        flag = 1;
                    } else if (flag < 0) {
                        flag = -1;
                    }
                    return flag;
                }

                /**
                 * 如果t1大于t2:1<br>
                 * t1等于t2:0<br>
                 * t1小于t2:-1
                 *
                 * @param flag
                 * @param t1
                 * @param t2
                 * @return
                 * @author Ken_xu
                 */
                private int compareByFlag(Method method, T t1, T t2) {
                    int flag = 0;
                    try {
                        String methodReturn1 = method.invoke(t1).toString();
                        String methodReturn2 = method.invoke(t2).toString();
                        flag = methodReturn1.compareTo(methodReturn2);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return flag;
                }

            };
            Collections.sort(list, comparator);
        }
        return list;
    }

    /**
     * 排序方式：
     * <p>
     * ASC:升序<br/> DESC:降序
     *
     */
    enum Direction {
        ASC, DESC
    };


}