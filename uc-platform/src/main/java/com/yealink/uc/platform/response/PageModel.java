package com.yealink.uc.platform.response;

import java.io.Serializable;
import java.util.List;

public class PageModel<T> implements Serializable {
    private long total;
    private List<T> records;

    private long pageNo;

    private long pageSize;

    public long getTotal() {
        return total;
    }

    public long getTotalPages() {
        return (total + pageSize - 1) / pageSize;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getTopPageNo() {
        return 1;
    }

    public long getPreviousPageNo() {
        if (pageNo <= 1) {
            return 1;
        }
        return pageNo - 1;
    }

    public long getNextPageNo() {
        if (pageNo >= getTotalPages()) {
            return getTotalPages() == 0 ? 1 : getTotalPages();
        }
        return pageNo + 1;
    }

    public long getBottomPageNo() {
        return getTotalPages() == 0 ? 1 : getTotalPages();
    }

    public static <T> PageModel<T> createPageModel(int pageNo, int pageSize, long totalRecords, List<T> records) {
        PageModel<T> pageModel = new PageModel<T>();
        pageModel.setPageNo(pageNo);
        pageModel.setPageSize(pageSize);
        pageModel.setRecords(records);
        pageModel.setTotal(totalRecords);
        return pageModel;
    }
}
