package com.yealink.uc.web.modules.account.service;

import java.util.List;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.platform.utils.StringUtil;
import com.yealink.uc.web.modules.account.dao.AccountDao;
import com.yealink.uc.web.modules.account.request.EditAccountRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class AccountService {
    @Autowired
    AccountPasswordService accountPasswordService;
    @Autowired
    AccountMailService accountMailService;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private BindAccountService accountBindService;

    public List<Account> findAccountListByMail(String mail) {
        return accountPasswordService.findAccountListByMail(mail);
    }

    public Account getAccount(Long id) {
        return accountPasswordService.getAccount(id);
    }

    public void submitResetPasswordApply(List<Account> accountList, String mail) {
        String activeCode = StringUtil.generateUUID();
        String randomPassword = accountPasswordService.createRandomPassword();
        accountPasswordService.updateToRandomPassword(accountList, randomPassword, activeCode);
        accountMailService.sendResetPasswordMail(mail, randomPassword, activeCode);
    }

    public boolean editAccount(EditAccountRequest editAccountRequest, Account account, Enterprise enterprise) {
        return accountPasswordService.editAccount(editAccountRequest, account, enterprise);
    }

    public boolean checkForgotPwdActiveCode(String activeCode) {
        if (!accountPasswordService.isActiveCode(activeCode)) return false;
        return accountPasswordService.checkForgotPwdActiveCode(activeCode);
    }

    public boolean resetPassword(String activeCode, String newPassword) {
        return accountPasswordService.resetPassword(activeCode, newPassword);
    }

    public List<Account> findAccountListByStaffId(long staffId) {
        return accountDao.findByStaffId(staffId);
    }

    public void bindEmail(long staffId, String email) {
        String activeCode = StringUtil.generateUUID();
        long effectRow = accountBindService.bindEmail(staffId, email, activeCode);
        if (effectRow >= 1) {
            accountMailService.sendBindEmailActiveMail(email, activeCode);
        }
    }

    public void activeBindEmail(String activeCode) {
        accountBindService.activeBindEmail(activeCode);
    }

    public Account getByStaffIdAndType(long staffId, String type) {
        return accountDao.getByStaffAndType(staffId, type);
    }
}
