package com.yealink.uc.web.modules.businessaccount;

import java.util.List;

import com.yealink.uc.common.modules.businessaccount.entity.BusinessAccount;
import com.yealink.uc.common.modules.businessaccount.vo.BusinessType;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.crypto.AuthFactory;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.staff.request.CreateStaffRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class BusinessAccountService {
    @Autowired
    BusinessAccountDao businessAccountDao;

    @Autowired
    IdGeneratorDao idGeneratorDao;

    public void saveImAccount(final CreateStaffRequest createStaffRequest, final String createStaffUsername, final Staff staff) {
        BusinessAccount imAccount = new BusinessAccount();
        imAccount.setBusinessType(BusinessType.IM.getCode());
        imAccount.setUsername(createStaffUsername);
        imAccount.setEncryptedPassword(AuthFactory.encryptPassword(createStaffRequest.getPassword()));
        imAccount.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(BusinessAccount.class)));
        imAccount.setStaffId(staff.get_id());
        imAccount.setStatus(true);
        businessAccountDao.save(imAccount);
    }

    public void saveStaffExtensionAccount(String extensionNumber, String password, Integer pincode, Staff staff) {
        BusinessAccount extensionAccount = new BusinessAccount();
        extensionAccount.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(BusinessAccount.class)));
        extensionAccount.setUsername(extensionNumber);
        extensionAccount.setEncryptedPassword(password);
        extensionAccount.setStaffId(staff.get_id());
        extensionAccount.setBusinessType(BusinessType.EXTENSION.getCode());
        extensionAccount.setStatus(true);
        extensionAccount.setPinCode(pincode);
        businessAccountDao.save(extensionAccount);
    }

    public void editStaffExtensionAccount(String extensionNumber, String password, boolean status, Integer pincode, BusinessAccount extensionAccount) {
        extensionAccount.setUsername(extensionNumber);
        extensionAccount.setEncryptedPassword(password);
        extensionAccount.setStatus(status);
        extensionAccount.setPinCode(pincode);
        businessAccountDao.update(extensionAccount);
    }

    public BusinessAccount getByStaffAndType(Long staffId, String type) {
        return businessAccountDao.getByStaffAndType(staffId, type);
    }

    public List<BusinessAccount> findByStaffIdsAndType(List<Long> staffIds, String type) {
        return businessAccountDao.findByStaffIdsAndType(staffIds, type);
    }
}
