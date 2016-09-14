package com.yealink.uc.service.modules.businessaccount.controller;

import java.util.List;
import javax.validation.Valid;

import com.yealink.uc.service.modules.account.vo.BusinessAccountServiceView;
import com.yealink.uc.service.modules.businessaccount.api.BusinessAccountRESTService;
import com.yealink.uc.service.modules.businessaccount.request.BusinessAccountRESTRequest;
import com.yealink.uc.service.modules.businessaccount.response.BusinessAccountRESTResponse;
import com.yealink.uc.service.modules.businessaccount.service.BusinessAccountService;
import com.yealink.uc.service.modules.staff.service.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChNan
 */

@RestController
public class BusinessAccountRESTController implements BusinessAccountRESTService {
    @Autowired
    BusinessAccountService businessAccountService;
    @Autowired
    StaffService staffService;


    @RequestMapping(value = "/businessAccounts/staff", method = RequestMethod.POST)
    public BusinessAccountRESTResponse findStaffAccounts(@Valid @RequestBody BusinessAccountRESTRequest request) {
        BusinessAccountRESTResponse response = new BusinessAccountRESTResponse();
        List<BusinessAccountServiceView> businessAccountServices = businessAccountService.createBusinessAccountServices(request.getBusinessTypes(), staffService.getStaff(request.getStaffId()));
        response.setBusinessAccountServices(businessAccountServices);
        return response;
    }
}
