package com.yealink.uc.service.modules.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class AccountService {
    @Autowired
    AccountPasswordService accountPasswordService;

    public boolean editPassword(Long id, String oldPassword, String newPassword) {
        return accountPasswordService.editPassword(id, oldPassword, newPassword);
    }
}
