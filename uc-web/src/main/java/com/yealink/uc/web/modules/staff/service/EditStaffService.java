package com.yealink.uc.web.modules.staff.service;

import java.util.List;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.businessaccount.entity.BusinessAccount;
import com.yealink.uc.common.modules.businessaccount.vo.BusinessType;
import com.yealink.uc.common.modules.phone.Phone;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.platform.crypto.AuthFactory;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.DateUtil;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SecurityUtil;
import com.yealink.uc.web.modules.account.dao.AccountDao;
import com.yealink.uc.web.modules.businessaccount.BusinessAccountDao;
import com.yealink.uc.web.modules.businessaccount.BusinessAccountService;
import com.yealink.uc.web.modules.phone.PhoneDao;
import com.yealink.uc.web.modules.phone.PhoneService;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.staff.producer.StaffMessageProducer;
import com.yealink.uc.web.modules.staff.request.EditStaffRequest;
import com.yealink.uc.web.modules.staff.service.common.CommonStaffService;
import com.yealink.uc.web.modules.stafforgmapping.dao.StaffOrgMappingDao;
import com.yealink.uc.web.modules.stafforgmapping.service.StaffOrgMappingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * Created by yewl on 2016/6/20.
 */
@Service
public class EditStaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private CommonStaffService commonStaffService;
    @Autowired
    private StaffMessageProducer staffMessageProducer;
    @Autowired
    private StaffOrgMappingService staffOrgMappingService;
    @Autowired
    private StaffOrgMappingDao staffOrgMappingDao;

    @Autowired
    AccountDao accountDao;
    @Autowired
    BusinessAccountDao businessAccountDao;

    @Autowired
    BusinessAccountService businessAccountService;

    @Autowired
    PhoneService phoneService;
    @Autowired
    PhoneDao phoneDao;

    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean editStaff(EditStaffRequest editStaffRequest, Long enterpriseId) {
        Staff currentStaff = staffDao.get(editStaffRequest.getStaffId(), enterpriseId);
        if (currentStaff == null) {
            throw new BusinessHandleException(MessageUtil.getMessage("staff.exception.not.found", editStaffRequest.getStaffId()));
        }
        validateEmailUsed(editStaffRequest, currentStaff);
        currentStaff = buildEditStaff(currentStaff, editStaffRequest);
        Long updateCount = staffDao.updateStaff(currentStaff);

        updateUCAccounts(editStaffRequest);

        updateIMAccount(editStaffRequest, currentStaff);

        handleExtensionAccount(editStaffRequest, currentStaff);

        handlePhone(editStaffRequest, currentStaff);

        commonStaffService.updateUser(editStaffRequest.getPassword(), currentStaff);

        if (updateCount == 1) {
            staffMessageProducer.editStaff(currentStaff.get_id());
        }

        staffOrgMappingDao.deleteByStaff(editStaffRequest.getStaffId()); // remove old mapping
        List<StaffOrgMapping> staffOrgMappings = commonStaffService.mergeOrgMappings(editStaffRequest.getStaffId(), editStaffRequest.getorgMappings(), null, enterpriseId);
        boolean mappingSaveResult = staffOrgMappingService.batchSave(staffOrgMappings);
        if (mappingSaveResult) {
            List<Long> staffInOrgIds = CommonStaffService.getOrgIdsFromMapping(staffOrgMappings);
            commonStaffService.batchEditOrgModification(staffInOrgIds, currentStaff.getModificationDate());
        }

        return updateCount == 1;
    }

    private void validateEmailUsed(final EditStaffRequest editStaffRequest, final Staff currentStaff) {
        Staff emailUsed = staffDao.findByEmail(editStaffRequest.getEmail());
        if (emailUsed != null && !emailUsed.get_id().equals(currentStaff.get_id())) {
            throw new BusinessHandleException(MessageUtil.getMessage("staff.exception.email.already.used", editStaffRequest.getEmail()));
        }
    }

    private void updateIMAccount(final EditStaffRequest editStaffRequest, final Staff currentStaff) {
        BusinessAccount imAccount = businessAccountDao.getByStaffAndType(currentStaff.get_id(), BusinessType.IM.getCode());
        if (!imAccount.getEncryptedPassword().equals(AuthFactory.encryptPassword(editStaffRequest.getPassword()))) {
            imAccount.setEncryptedPassword(AuthFactory.encryptPassword(editStaffRequest.getPassword()));
            businessAccountDao.update(imAccount);
        }
    }

    private void updateUCAccounts(final EditStaffRequest editStaffRequest) {
        List<Account> accounts = accountDao.findByStaffId(editStaffRequest.getStaffId());
        for (Account account : accounts) {
            account.setPassword(SecurityUtil.encryptPassword(account.getSalt() + account.getSalt(), SecurityUtil.encryptMd5(editStaffRequest.getPassword())));
            account.setPinCode(editStaffRequest.getPinCode());
        }
        accountDao.updateAccounts(accounts);
    }

    private void handlePhone(final EditStaffRequest editStaffRequest, final Staff currentStaff) {
        Phone phone = phoneDao.getByStaff(currentStaff.get_id());
        if (phone == null) {
            if (editStaffRequest.getIsSelectPhoneSetting()) {
                phoneService.saveStaffPhone(editStaffRequest.getPhoneIP(),
                    editStaffRequest.getPhoneMac(),
                    editStaffRequest.getPhoneModel(),
                    editStaffRequest.getPhoneSettingTemplate(), currentStaff.get_id(), true
                );
            }
        } else {
            phoneService.editStaffPhone(editStaffRequest.getPhoneIP(),
                editStaffRequest.getPhoneMac(),
                editStaffRequest.getPhoneModel(),
                editStaffRequest.getPhoneSettingTemplate(), editStaffRequest.getIsSelectPhoneSetting(), phone
            );
        }
    }

    private void handleExtensionAccount(final EditStaffRequest editStaffRequest, final Staff currentStaff) {
        BusinessAccount extensionAccount = businessAccountDao.getByStaffAndType(currentStaff.get_id(), BusinessType.EXTENSION.getCode());
        if (extensionAccount == null) {
            if (editStaffRequest.getIsSelectExtensionSetting()) {
                businessAccountService.saveStaffExtensionAccount(editStaffRequest.getExtensionNumber(),
                    editStaffRequest.getExtensionPassword(), editStaffRequest.getExtensionPinCode(), currentStaff
                );
            }
        } else {
            businessAccountService.editStaffExtensionAccount(editStaffRequest.getExtensionNumber(),
                editStaffRequest.getExtensionPassword(), editStaffRequest.getIsSelectExtensionSetting(),
                editStaffRequest.getExtensionPinCode(), extensionAccount
            );
        }
    }


    private Staff buildEditStaff(Staff currentStaff, EditStaffRequest editStaffRequest) {
        currentStaff = DataConverter.copy(currentStaff, editStaffRequest);
        if (editStaffRequest.getEntryDate() != null) {
            currentStaff.setEntryDate(editStaffRequest.getEntryDate());
        }
        if (editStaffRequest.getBirthday() != null) {
            currentStaff.setBirthday(editStaffRequest.getBirthday());
        }
        currentStaff.setModificationDate(DateUtil.currentDate().getTime());
        return currentStaff;
    }
}

