package com.yealink.uc.web.modules.org.request;

/**
 * @author ChNan
 */
public class EditOrgRequestItem {
    private String title;

    private Long staffId;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }
}
