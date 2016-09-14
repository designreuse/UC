package com.yealink.uc.service.modules.account.controller;

import javax.validation.Valid;

import com.yealink.uc.service.modules.account.api.AccountRESTService;
import com.yealink.uc.service.modules.account.request.EditAccountPasswordRESTRequest;
import com.yealink.uc.service.modules.account.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChNan
 */

@RestController
public class AccountRESTController implements AccountRESTService {
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/accounts/password", method = RequestMethod.PUT)
    public boolean editPassword(@Valid @RequestBody EditAccountPasswordRESTRequest request) {
        return accountService.editPassword(request.getAccountId(), request.getOldPassword(), request.getNewPassword());
    }

}
