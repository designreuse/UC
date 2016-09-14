package com.yealink.uc.web.modules.staff.request;

import java.util.List;

import com.yealink.uc.platform.web.pager.PagerRequest;

/**
 * Created by yewl on 2016/6/24.
 */
public class SearchStaffRequest extends PagerRequest {
    private List<Integer> initStatusList;
    private Integer selectedStatus;
    private String searchKey;
    private Long orgIdFilter;
    private Long staffIdFilter;
    private Long orgIdDetail;

    public SearchStaffRequest() {
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

    public Long getOrgIdFilter() {
        return orgIdFilter;
    }

    public void setOrgIdFilter(final Long orgId) {
        this.orgIdFilter = orgId;
    }

    public Long getStaffIdFilter() {
        return staffIdFilter;
    }

    public void setStaffIdFilter(final Long staffId) {
        this.staffIdFilter = staffId;
    }

    public Long getOrgIdDetail() {
        return orgIdDetail;
    }

    public void setOrgIdDetail(final Long orgIdDetail) {
        this.orgIdDetail = orgIdDetail;
    }
}
