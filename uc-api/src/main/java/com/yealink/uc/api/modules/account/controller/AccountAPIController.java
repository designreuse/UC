package com.yealink.uc.api.modules.account.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.api.modules.account.request.EditAccountPwdAPIRequest;
import com.yealink.uc.api.modules.account.response.EditAccountPasswordResponse;
import com.yealink.uc.api.modules.account.service.AccountAPIService;
import com.yealink.uc.platform.response.APIResponse;

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
public class AccountAPIController {
    @Autowired
    AccountAPIService accountAPIService;

    @RequestMapping(value = "/account/password", method = RequestMethod.PUT)
    @ResponseBody
    public APIResponse editPassword(HttpServletRequest request, @Valid @RequestBody EditAccountPwdAPIRequest editAccountPwdAPIRequest) {
        boolean editSuccess = accountAPIService.editPassword(editAccountPwdAPIRequest);
        return APIResponse.buildResponse(new EditAccountPasswordResponse(editSuccess));
    }
}
