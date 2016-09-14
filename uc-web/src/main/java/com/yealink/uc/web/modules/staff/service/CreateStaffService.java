package com.yealink.uc.web.modules.staff.service;

import java.util.List;

import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.staff.vo.StaffStatus;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.platform.crypto.AuthFactory;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.web.modules.account.dao.AccountDao;
import com.yealink.uc.web.modules.account.service.CreateAccountService;
import com.yealink.uc.web.modules.businessaccount.BusinessAccountService;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.phone.PhoneService;
import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.staff.dao.UserDao;
import com.yealink.uc.web.modules.staff.producer.StaffMessageProducer;
import com.yealink.uc.web.modules.staff.request.CreateStaffRequest;
import com.yealink.uc.web.modules.staff.service.common.CommonStaffService;
import com.yealink.uc.web.modules.stafforgmapping.service.StaffOrgMappingService;
import com.yealink.uc.web.modules.staffrolemapping.dao.StaffRoleMappingDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class CreateStaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private IdGeneratorDao idGeneratorDao;
    @Autowired
    private StaffMessageProducer staffMessageProducer;
    @Autowired
    private CommonStaffService commonStaffService;
    @Autowired
    private StaffOrgMappingService staffOrgMappingService;
    @Autowired
    private CreateAccountService createAccountService;
    @Autowired
    StaffRoleMappingDao staffRoleMappingDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    PhoneService phoneService;

    @Autowired
    BusinessAccountService businessAccountService;

    /**
     * 1:创建人员基本信息
     * 2：创建UC主账号，用于登录其他的终端
     * 3:创建IM业务账号
     * 4:创建分机业务账号
     * 5:创建话机信息
     *
     * @param createStaffRequest
     * @param enterprise
     * @return
     */
    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes", "orgStaffTreeNodes", "orgTreeNodesWithForbiddenStaff"}, key = "#enterprise.get_id()")
    })
    public boolean createStaff(CreateStaffRequest createStaffRequest, Enterprise enterprise) {
        String createStaffUsername = createStaffRequest.getUsername();
        validateUserNameExist(enterprise, createStaffUsername);
        validateEmailUsed(createStaffRequest.getEmail());
        Staff staff = DataConverter.copy(new Staff(), createStaffRequest);
        long insertStaffCount = saveStaffBaseInfo(enterprise, staff);
        if (insertStaffCount == 0) {
            return false;
        }

        if (createStaffRequest.getIsSelectExtensionSetting()) {
            businessAccountService.saveStaffExtensionAccount(createStaffRequest.getExtensionNumber(), createStaffRequest.getPassword(),
                createStaffRequest.getExtensionPinCode(), staff
            );
        }

        if (createStaffRequest.getIsSelectPhoneSetting()) {
            phoneService.saveStaffPhone(createStaffRequest.getPhoneIP(),
                createStaffRequest.getPhoneMac(),
                createStaffRequest.getPhoneModel(),
                createStaffRequest.getPhoneSettingTemplate(), staff.get_id(), true
            );
        }

        commonStaffService.saveUser(createStaffRequest.getPassword(), createStaffUsername, staff); //todo 暂时为了兼容openfire那边的数据结构，创建一个user，但是需要删除掉UC 1.0版本

        createAccountService.createPrimaryAccount(staff, enterprise.getDomain(),
            createStaffUsername, createStaffRequest.getPassword(), createStaffRequest.getPinCode()
        );

        businessAccountService.saveImAccount(createStaffRequest, createStaffUsername, staff);

        List<StaffOrgMapping> staffOrgMappings = commonStaffService.mergeOrgMappings(staff.get_id(), createStaffRequest.getOrgMappings(), null, enterprise.get_id());
        boolean mappingSaveResult = staffOrgMappingService.batchSave(staffOrgMappings);
        if (mappingSaveResult) {
            List<Long> orgIds = CommonStaffService.getOrgIdsFromMapping(staffOrgMappings);
            commonStaffService.batchEditOrgModification(orgIds, staff.getModificationDate());
            staffMessageProducer.createStaff(staff.get_id());
        }

        return mappingSaveResult;
    }

    private void validateEmailUsed(String email) {
        if (staffDao.findByEmail(email) != null) {
            throw new BusinessHandleException(MessageUtil.getMessage("staff.exception.email.already.used", email));
        }
    }

    public static void main(String[] args) {
        System.out.println(AuthFactory.encryptPassword("Cloud_2016"));
    }
    private void validateUserNameExist(final Enterprise enterprise, final String createStaffUsername) {
        if (accountDao.findByUsername(createStaffUsername, enterprise.get_id()) != null) {
            throw new BusinessHandleException(MessageUtil.getMessage("staff.exception.username.already.in.user", createStaffUsername));
        }

    }

    private long saveStaffBaseInfo(final Enterprise enterprise, final Staff staff) {
        Long staffId = idGeneratorDao.nextId(EntityUtil.getEntityCode(Staff.class));
        staff.set_id(staffId);
        buildCreateOrgInfo(staff, enterprise);
        return staffDao.save(staff);
    }

    private void buildCreateOrgInfo(Staff staff, Enterprise enterprise) {
        staff.setEnterpriseId(enterprise.get_id());
        staff.setCreationDate(System.currentTimeMillis());
        staff.setModificationDate(System.currentTimeMillis());
        staff.setDomain(enterprise.getDomain());
        staff.setStatus(StaffStatus.WORKING.getCode());
    }
}
