package com.yealink.ofweb.modules.fileshare.request;

import java.util.List;

/**
 * 分页请求对象
 * Created by pzy on 2016/7/29.
 */
public class PageRequest<T> {
    private Long total; //总记录数

    private List<T> rows; //返回的结果集

    private int pageSize=0; //页大小

    private int pageNumber=0; //页数

    private String sortOrder ="asc"; //排序

    private String defaultName;

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String order) {
        this.sortOrder = order;
    }
}
