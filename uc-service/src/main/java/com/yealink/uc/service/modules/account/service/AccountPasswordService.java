package com.yealink.uc.service.modules.account.service;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.SecurityUtil;
import com.yealink.uc.service.modules.account.dao.AccountDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class AccountPasswordService {
    public static final int TEN_MINUTES = 10 * 60 * 1000;
    @Autowired
    private AccountDao accountDao;

    public boolean editPassword(Long id, String oldPassword, String newPassword) {
        Account account = saveNewPassword(id, oldPassword, newPassword);
        return updateAccount(account);
    }

    private Account saveNewPassword(Long id, String oldPassword, String newPassword) {
        Account account = accountDao.get(id);
        String encryptPwd = SecurityUtil.encryptPassword(account.getSalt() + account.getSalt(), oldPassword);
        if (!encryptPwd.equals(account.getPassword())) {
            throw new BusinessHandleException("account.error.old.password.incorrect");
        }
        String encryptedNewPwd = SecurityUtil.encryptPassword(account.getSalt() + account.getSalt(), newPassword);
        account.setPassword(encryptedNewPwd);
        return account;
    }


    private boolean updateAccount(Account account) {
        return accountDao.updateAccount(account) == 1;
    }

}
