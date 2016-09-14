package com.yealink.uc.service.modules.businessaccount.response;

import java.util.List;

import com.yealink.uc.service.modules.account.vo.BusinessAccountServiceView;

/**
 * @author ChNan
 */
public class BusinessAccountRESTResponse {
    private List<BusinessAccountServiceView> businessAccountServices;

    public List<BusinessAccountServiceView> getBusinessAccountServices() {
        return businessAccountServices;
    }

    public void setBusinessAccountServices(final List<BusinessAccountServiceView> businessAccountServices) {
        this.businessAccountServices = businessAccountServices;
    }
}
