package com.yealink.uc.service.modules.businessaccount.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author ChNan
 */
public class BusinessAccountRESTRequest {
    @NotNull
    @Min(1)
    private Long staffId;

    @NotEmpty(message = "{login.validator.businessType.null}")
    private List<String> businessTypes;

    public BusinessAccountRESTRequest() {
    }

    public BusinessAccountRESTRequest(final Long staffId, final List<String> businessTypes) {
        this.staffId = staffId;
        this.businessTypes = businessTypes;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(final Long staffId) {
        this.staffId = staffId;
    }

    public List<String> getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(final List<String> businessTypes) {
        this.businessTypes = businessTypes;
    }
}
