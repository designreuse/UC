package com.yealink.uc.service.modules.login.service;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.staff.vo.StaffStatus;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.SecurityUtil;
import com.yealink.uc.platform.utils.StringUtil;
import com.yealink.uc.service.modules.account.dao.AccountDao;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.service.modules.businessaccount.service.BusinessAccountService;
import com.yealink.uc.service.modules.login.request.LoginRESTRequest;
import com.yealink.uc.service.modules.staff.dao.StaffDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class LoginService {
    @Autowired
    AccountDao accountDao;
    @Autowired
    LoginLockService lockService;
    @Autowired
    StaffDao staffDao;
    @Autowired
    BusinessAccountService businessAccountService;

    public UCAccountView login(LoginRESTRequest loginRequest) {
        if (lockService.isLocked(loginRequest.getUsername()))
            throw new BusinessHandleException("login.tips.auth.error.locked", lockService.getErrorCount(loginRequest.getUsername()));
        Account ucAccount;
        if (StringUtil.hasText(loginRequest.getDomain())) {
            ucAccount = accountDao.findByUsernameDomain(loginRequest.getUsername(), loginRequest.getDomain());
        } else { // without domain , means onprimise
            ucAccount = accountDao.findByUsername(loginRequest.getUsername());
        }
        handleAccountNotExist(loginRequest.getUsername(), ucAccount);
        handleStaffStatusError(ucAccount);
        validatePassword(loginRequest.getUsername(), loginRequest.getPassword(), ucAccount);
        lockService.removeLoginErrorLock(loginRequest.getUsername());
        return DataConverter.copy(new UCAccountView(), ucAccount);
    }

    private void handleStaffStatusError(final Account ucAccount) {
        Staff staff = staffDao.get(ucAccount.getStaffId());
        if (StaffStatus.WORKING.getCode() != staff.getStatus()) {
            throw new BusinessHandleException("login.tips.status.error");
        }
    }

    private void validatePassword(String userName, String password, Account account) {
        String encryptedPassword = SecurityUtil.encryptPassword(account.getSalt() + account.getSalt(), password);
        if (!account.getPassword().equals(encryptedPassword)) {
            handleLoginError(userName);
        }
    }

    private void handleAccountNotExist(String userName, Account account) {
        if (account == null) {
            handleLoginError(userName);
        }
    }

    private void handleLoginError(final String userName) {
        Integer errorCount = lockService.recordLoginError(userName);
        if (lockService.isLocked(userName)) {
            throw new BusinessHandleException("login.tips.auth.error.locked", errorCount);
        } else {
            throw new BusinessHandleException("login.tips.auth.error.username.password");
        }
    }


}
