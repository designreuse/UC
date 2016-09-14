package com.yealink.uc.web.modules.org.request;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */

public class EditOrgRequest extends OrgCommonRequest {
    @Min(1)
    @NotNull
    private Long orgId;
    @Min(1)
    @NotNull
    private Long parentId;
    private List<EditOrgRequestItem> editOrgRequestItemList = new ArrayList<>();

    public List<EditOrgRequestItem> getEditOrgRequestItemList() {
        return editOrgRequestItemList;
    }

    public void setEditOrgRequestItemList(final List<EditOrgRequestItem> editOrgRequestItemList) {
        this.editOrgRequestItemList = editOrgRequestItemList;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }
}
