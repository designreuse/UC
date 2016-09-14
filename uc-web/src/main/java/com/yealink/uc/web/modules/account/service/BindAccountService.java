package com.yealink.uc.web.modules.account.service;

import java.util.List;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.account.vo.AccountStatus;
import com.yealink.uc.common.modules.account.vo.AccountType;
import com.yealink.uc.common.modules.common.CommonConstant;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.platform.utils.StringUtil;
import com.yealink.uc.web.modules.account.dao.AccountDao;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yewl on 2016/8/9.
 */
@Service
public class BindAccountService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    IdGeneratorDao idGeneratorDao;

    public static final int TEN_MINUTES = 10 * 60 * 1000;

    public long bindEmail(long staffId, String email, String activeCode) {
        if (accountDao.findByUsername(email) != null) {
            throw new BusinessHandleException("account.bind.has.email.account");
        }
        List<Account> accountList = accountDao.findByStaffId(staffId);

        Account emailAccount = accountList.get(0);
        emailAccount.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(Account.class)));
        emailAccount.setUsername(email);
        emailAccount.setActiveCode(activeCode);
        emailAccount.setActiveCodeExpiredDate(System.currentTimeMillis() + TEN_MINUTES);
        emailAccount.setStatus(AccountStatus.IN_ACTIVE.getCode());
        emailAccount.setType(AccountType.EMAIL.getCode());
        return accountDao.save(emailAccount);
    }

    public void activeBindEmail(String activeCode) {
        if (!StringUtil.isRegular(activeCode, CommonConstant.ACTIVE_CODE_REGULAR)) {
            throw new BusinessHandleException("account.bind.email.invalidate.url");
        }
        List<Account> accountList = accountDao.findByActiveCodeAndResetDate(activeCode, System.currentTimeMillis());
        if (accountList == null || accountList.isEmpty() || !accountList.get(0).getType().equals(AccountType.EMAIL.getCode())) {
            throw new BusinessHandleException("account.bind.email.invalidate.url");
        }
        Account account = accountList.get(0);
        account.setActiveCode(null);
        account.setActiveCodeExpiredDate(null);
        account.setStatus(AccountStatus.ACTIVE.getCode());
        accountDao.updateAccount(account);
    }
}
