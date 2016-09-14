package com.yealink.uc.service.modules.businessaccount.api;

import javax.validation.Valid;

import com.yealink.uc.service.modules.businessaccount.request.BusinessAccountRESTRequest;
import com.yealink.uc.service.modules.businessaccount.response.BusinessAccountRESTResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ChNan
 */
public interface BusinessAccountRESTService {
    @RequestMapping(value = "/businessAccounts/staff", method = RequestMethod.POST)
    public BusinessAccountRESTResponse findStaffAccounts(@Valid @RequestBody BusinessAccountRESTRequest businessAccountRequest);

}
