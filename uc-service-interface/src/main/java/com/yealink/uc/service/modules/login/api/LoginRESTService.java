package com.yealink.uc.service.modules.login.api;

import javax.validation.Valid;

import com.yealink.uc.service.modules.login.request.LoginRESTRequest;
import com.yealink.uc.service.modules.login.response.LoginRESTResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ChNan
 */
public interface LoginRESTService {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginRESTResponse login(@Valid @RequestBody LoginRESTRequest loginRequest);

    @RequestMapping(value = "/login/error/count/{userName}", method = RequestMethod.GET)
    public Integer loginErrorCount(@PathVariable(value = "userName") String userName);
}
