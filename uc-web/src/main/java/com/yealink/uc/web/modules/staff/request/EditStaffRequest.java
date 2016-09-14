package com.yealink.uc.web.modules.staff.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class EditStaffRequest extends StaffCommonRequest {
    @NotNull
    @Min(value = 1)
    private Long staffId;
    @NotEmpty
    private List<Long> orgMappings;

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public List<Long> getorgMappings() {
        return orgMappings;
    }

    public void setorgMappings(final List<Long> orgMappings) {
        this.orgMappings = orgMappings;
    }
}
