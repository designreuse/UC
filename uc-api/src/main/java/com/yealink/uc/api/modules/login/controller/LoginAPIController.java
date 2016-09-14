package com.yealink.uc.api.modules.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.api.modules.account.vo.StaffDetail;
import com.yealink.uc.api.modules.account.vo.UCAccount;
import com.yealink.uc.api.modules.login.request.LoginAPIRequest;
import com.yealink.uc.api.modules.login.response.LoginAPIResponse;
import com.yealink.uc.api.modules.login.service.LoginAPIService;
import com.yealink.uc.platform.response.APIResponse;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.service.modules.login.api.LoginRESTService;
import com.yealink.uc.service.modules.login.request.LoginRESTRequest;
import com.yealink.uc.service.modules.login.response.LoginRESTResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChNan
 */
@RestController
public class LoginAPIController {
    @Autowired
    LoginRESTService loginRESTService;

    @Autowired
    LoginAPIService loginAPIService;
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public APIResponse login(HttpServletRequest request, @Valid @RequestBody LoginAPIRequest loginAPIRequest) {
        LoginRESTRequest loginRESTRequest = DataConverter.copy(new LoginRESTRequest(), loginAPIRequest);
        LoginRESTResponse loginRESTResponse = loginRESTService.login(loginRESTRequest);
        LoginAPIResponse response = loginAPIService.buildBusinessAccountService(loginRESTResponse.getUcAccount().getStaffId(), loginAPIRequest.getBusinessTypes());

        UCAccount ucAccount = DataConverter.copy(new UCAccount(), loginRESTResponse.getUcAccount());
        ucAccount.setAccountId(loginRESTResponse.getUcAccount().get_id());
        response.setUcAccount(ucAccount);
        response.setStaff(new StaffDetail(loginRESTResponse.getUcAccount().getStaffId(), loginRESTResponse.getUcAccount().getEnterpriseId()));
        return APIResponse.buildResponse(response);
    }
}
