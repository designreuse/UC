package com.yealink.uc.platform.web.pager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yewl
 */
public class PagerRequest {

    private static final int CURRENT_PAGE_NO = 1;

    private static final int DEFAULT_PAGE_SIZE = 10;

    public static final int ORDER_BY_TYPE_ASC = 1;

    public static final int ORDER_BY_TYPE_DESC = -1;

    /**
     * 页码.
     */
    private int pageNo = CURRENT_PAGE_NO;

    /**
     * 查询的总记录数.
     */
    private int total;

    /**
     * 每个分页的页大小
     */
    private int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 排序字段.
     */
    private String orderByField;

    /**
     * 列表的排序方式，默认为降序.
     */
    private int orderByType = ORDER_BY_TYPE_DESC;

    /**
     * 存储有效的排序字段，相当于一个白名单的作用，orderbyField只有在该集合中才算是有效的.
     */
    private final List<String> validateFileds = new ArrayList<String>();

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    public String getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(String orderByField) throws RuntimeException {
        if (!validateFileds.isEmpty() && !validateFileds.contains(orderByField)) {
            throw new RuntimeException("Invalid Parameter orderByField");
        } else {
            this.orderByField = orderByField;
        }
    }

    public int getOrderByType() {
        return orderByType;
    }

    public void setOrderbyType(int orderbyType) throws RuntimeException {
        if (orderbyType == ORDER_BY_TYPE_ASC || orderbyType == ORDER_BY_TYPE_DESC) {
            this.orderByType = orderbyType;
        } else {
            throw new RuntimeException("Invalid Parameter orderByType");
        }
    }

    public int getSkip() {
        if (pageNo <= 0) {
            return 0;
        }
        return getPageSize() * (pageNo - 1);
    }

    public void addValidateFiled(String validateFiled) {
        this.validateFileds.add(validateFiled);
    }

}
