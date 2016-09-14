package com.yealink.uc.web.modules.account.service;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.MailUtil;
import com.yealink.uc.platform.utils.SecurityUtil;
import com.yealink.uc.platform.utils.StringUtil;
import com.yealink.uc.web.modules.account.dao.AccountDao;
import com.yealink.uc.web.modules.account.request.EditAccountRequest;
import com.yealink.uc.common.modules.common.CommonConstant;
import com.yealink.uc.web.modules.enterprise.dao.EnterpriseDao;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author ChNan
 */
@Service
public class AccountPasswordService {
    public static final int TEN_MINUTES = 10 * 60 * 1000;
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private StaffDao staffDao;

    public List<Account> findAccountListByMail(String mail) {
        if (mail == null || !MailUtil.isEmail(mail)){
            return null;
        }
        Staff staff = staffDao.findByEmail(mail);
        if(staff == null){
            return null;
        }
        return accountDao.findByStaffId(staff.get_id());
    }

    public Account getAccount(Long id) {
        Account account = accountDao.get(id);
        if (account == null)
            throw new BusinessHandleException("account.tips.user.not.exist");
        return account;
    }


    public boolean editAccount(EditAccountRequest editAccountRequest, Account account, Enterprise enterprise) {
        account.setName(editAccountRequest.getName());
        account.setUsername(editAccountRequest.getUsername());
        if (StringUtils.hasText(editAccountRequest.getOldPassword())) {
            account = saveNewPassword(account.get_id(), editAccountRequest.getOldPassword(), editAccountRequest.getNewPassword());
        }
        Enterprise enterpriseUpdated = enterpriseDao.findOne(enterprise.get_id());
        enterpriseUpdated.setName(editAccountRequest.getEnterpriseName());
        enterpriseDao.update(enterpriseUpdated);
        return updateAccount(account);
    }

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


    public String createRandomPassword() {
        return SecurityUtil.generalRandomPassword(10, true);
    }

    public boolean updateToRandomPassword(List<Account> accountList, String plainTextPassword, String activeCode) {
        String md5Password = SecurityUtil.encryptMd5(plainTextPassword);
        for(Account account : accountList){
            account.setPassword(SecurityUtil.encryptPassword(account.getSalt() + account.getSalt(), md5Password));
            account.setActiveCodeExpiredDate(System.currentTimeMillis() + TEN_MINUTES);
            account.setActiveCode(activeCode);
        }
        return updateAccountList(accountList);
    }

    public boolean checkForgotPwdActiveCode(String activeCode) {
        List<Account> accountList = accountDao.findByActiveCodeAndResetDate(activeCode, System.currentTimeMillis());
        return accountList != null && !accountList.isEmpty();
    }

    public boolean isActiveCode(String activeCode) {
        return StringUtil.isRegular(activeCode, CommonConstant.ACTIVE_CODE_REGULAR);
    }

    public boolean resetPassword(String activeCode, String newPassword) {
        List<Account> accountList = accountDao.findByActiveCodeAndResetDate(activeCode, System.currentTimeMillis());
        for (Account account : accountList){
            account.setActiveCode("");
            account.setPassword(SecurityUtil.encryptPassword(account.getSalt() + account.getSalt(), newPassword));
        }
        return updateAccountList(accountList);
    }

    private boolean updateAccount(Account account) {
        return accountDao.updateAccount(account) == 1;
    }

    private boolean updateAccountList(List<Account> accountList){
        return accountDao.updateAccounts(accountList) == accountList.size();
    }
}
