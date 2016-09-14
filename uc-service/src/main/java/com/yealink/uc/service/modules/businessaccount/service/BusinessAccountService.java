package com.yealink.uc.service.modules.businessaccount.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.yealink.uc.common.modules.businessaccount.entity.BusinessAccount;
import com.yealink.uc.common.modules.businessaccount.vo.BusinessType;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.crypto.AuthFactory;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.service.modules.account.vo.BusinessAccountServiceView;
import com.yealink.uc.service.modules.businessaccount.dao.BusinessAccountDao;
import com.yealink.uc.service.modules.utils.AccountUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class BusinessAccountService {
    @Autowired
    BusinessAccountDao businessAccountDao;
    //todo to make service find
    @Value("${im.service.url}")
    String imServiceUrl;

    public List<BusinessAccount> findListByStaffId(Long staffId) {
        return businessAccountDao.findListByStaffId(staffId);
    }

    //todo edit to business account
    public List<BusinessAccountServiceView> createBusinessAccountServices(List<String> businessTypes, Staff staff) {
        List<BusinessAccountServiceView> businessAccountServices = new ArrayList<>();
        for (String businessType : businessTypes) {
            BusinessAccountServiceView businessAccountServiceView;
            List<BusinessAccount> businessAccount = findListByStaffId(staff.get_id());
            Map<String, BusinessAccount> businessAccountMap = Maps.uniqueIndex(businessAccount.iterator(), new Function<BusinessAccount, String>() {
                @Override
                public String apply(final BusinessAccount input) {
                    return input.getBusinessType();
                }
            });
            if (BusinessType.IM.getCode().equals(businessType)) {
                BusinessAccount imAccount = businessAccountMap.get(BusinessType.IM.getCode());
                if (imAccount == null) throw new BusinessHandleException("不存在IM账号");
                businessAccountServiceView = new BusinessAccountServiceView();
                businessAccountServiceView.setBusinessServiceUrl(imServiceUrl);
                businessAccountServiceView.setPassword(imAccount.getEncryptedPassword());
                businessAccountServiceView.setUsername(AccountUtil.createFullUserName(imAccount.getUsername(), staff.getDomain()));
                businessAccountServiceView.setStaffId(staff.get_id());
                businessAccountServiceView.setBusinessType(businessType);
                businessAccountServices.add(businessAccountServiceView);
            } else {
                // todo sip ,voip,etc...
            }
        }
        return businessAccountServices;
    }
}
