package com.yealink.uc.service.modules.account.api;

import javax.validation.Valid;

import com.yealink.uc.service.modules.account.request.EditAccountPasswordRESTRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ChNan
 */
public interface AccountRESTService {
    @RequestMapping(value = "/accounts/password", method = RequestMethod.PUT)
    public boolean editPassword(@Valid @RequestBody EditAccountPasswordRESTRequest editAccountPasswordRequest);
}
