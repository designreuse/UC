package com.yealink.uc.service.modules.login.controller;

import javax.validation.Valid;

import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.service.modules.login.api.LoginRESTService;
import com.yealink.uc.service.modules.login.request.LoginRESTRequest;
import com.yealink.uc.service.modules.login.response.LoginRESTResponse;
import com.yealink.uc.service.modules.login.service.LoginLockService;
import com.yealink.uc.service.modules.login.service.LoginService;
import com.yealink.uc.service.modules.staff.service.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChNan
 */

@RestController
public class LoginRESTController implements LoginRESTService {
    @Autowired
    LoginService loginService;
    @Autowired
    StaffService staffService;
    @Autowired
    LoginLockService loginLockService;
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginRESTResponse login(@Valid @RequestBody LoginRESTRequest loginRequest) {
        LoginRESTResponse response = new LoginRESTResponse();

        UCAccountView ucAccountView = loginService.login(loginRequest);
        response.setUcAccount(ucAccountView);
        return response;
    }


    @RequestMapping(value = "/login/error/count/{userName}", method = RequestMethod.GET)
    public Integer loginErrorCount(@PathVariable(value = "userName") String userName) {
        return loginLockService.getErrorCount(userName);
    }
}
