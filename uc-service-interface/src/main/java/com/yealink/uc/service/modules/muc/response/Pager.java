package com.yealink.uc.service.modules.muc.response;

/**
 * Created by yl1227 on 2016/8/23.
 */
public class Pager {

    private int pageNo;
    private int pageSize;
    private int total;
    private int totalPage;

    public Pager() {
    }

    public Pager(int pageNo, int pageSize, int total, int totalPage) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPage = totalPage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
