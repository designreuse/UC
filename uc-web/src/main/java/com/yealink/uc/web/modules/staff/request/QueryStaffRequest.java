package com.yealink.uc.web.modules.staff.request;

import java.util.List;

import com.yealink.uc.platform.web.pager.PagerRequest;

/**
 * Created by yewl on 2016/6/24.
 */
public class QueryStaffRequest extends PagerRequest {
    private List<Integer> initStatusList;
    private Integer selectedStatus;
    private String searchKey;
    private List<Long> staffIds;

    public QueryStaffRequest() {
        setOrderByField("modificationDate");
    }

    public List<Integer> getInitStatusList() {
        return initStatusList;
    }

    public void setInitStatusList(final List<Integer> initStatusList) {
        this.initStatusList = initStatusList;
    }

    public Integer getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(final Integer selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public List<Long> getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(final List<Long> staffIds) {
        this.staffIds = staffIds;
    }
}
