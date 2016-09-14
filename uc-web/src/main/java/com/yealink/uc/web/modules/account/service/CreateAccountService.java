package com.yealink.uc.web.modules.account.service;

import java.util.List;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.account.vo.AccountStatus;
import com.yealink.uc.common.modules.account.vo.AccountType;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.platform.utils.SecurityUtil;
import com.yealink.uc.web.modules.account.dao.AccountDao;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yewl on 2016/8/10.
 */
@Service
public class CreateAccountService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private IdGeneratorDao idGeneratorDao;

    public long createPrimaryAccount(Staff staff, String domain, String username, String password, Integer pin) {
        Account account = convertToAccount(staff, domain, username, password, pin);
        return accountDao.save(account);
    }

    private Account convertToAccount(Staff staff, String domain, String username, String plainPassword, Integer pin) {
        if (staff == null) {
            return null;
        }
        return createAccount(staff.getEnterpriseId(), staff.get_id(), domain, username, plainPassword, pin);
    }

    public Account createAccount(Long enterpriseId, Long staffId, String domain, String username, String plainPassword, Integer pin) {
        SecurityUtil.SecurityCredential securityCredential = SecurityUtil.encrypt(SecurityUtil.encryptMd5(plainPassword));
        Account account = new Account();
        account.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(Account.class)));
        account.setUsername(username);
        account.setSalt(securityCredential.getSalt());
        account.setPassword(securityCredential.getEncryptedPassword());
        account.setEnterpriseId(enterpriseId);
        account.setStatus(AccountStatus.ACTIVE.getCode());
        account.setType(AccountType.PRIMARY.getCode());
        account.setStaffId(staffId);
        account.setDomain(domain);
        account.setPinCode(pin);
        return account;
    }

    public long batchCreate(List<Account> accountList) {
        return accountDao.batchSave(accountList);
    }


}
