package com.yealink.uc.web.modules.staff.response;

import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.platform.response.PageModel;

/**
 * @author ChNan
 */
public class SearchStaffResponse {
    private PageModel staffPageModel;

    private Org org;

    public PageModel getStaffPageModel() {
        return staffPageModel;
    }

    public void setStaffPageModel(final PageModel staffPageModel) {
        this.staffPageModel = staffPageModel;
    }

    public Org getOrg() {
        return org;
    }

    public void setOrg(final Org org) {
        this.org = org;
    }
}
