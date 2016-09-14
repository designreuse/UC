package com.yealink.uc.api.modules.login.service;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yealink.uc.api.modules.account.vo.BusinessAccountService;
import com.yealink.uc.api.modules.login.response.LoginAPIResponse;
import com.yealink.uc.platform.crypto.AES;
import com.yealink.uc.platform.crypto.AuthFactory;
import com.yealink.uc.platform.crypto.BASE64;
import com.yealink.uc.platform.crypto.RSA;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.ResourceUtil;
import com.yealink.uc.service.modules.account.vo.BusinessAccountServiceView;
import com.yealink.uc.service.modules.businessaccount.api.BusinessAccountRESTService;
import com.yealink.uc.service.modules.businessaccount.request.BusinessAccountRESTRequest;
import com.yealink.uc.service.modules.businessaccount.response.BusinessAccountRESTResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class LoginAPIService {
    @Autowired
    BusinessAccountRESTService businessAccountRESTService;


    public LoginAPIResponse buildBusinessAccountService(Long staffId, List<String> businessTypes) {
        LoginAPIResponse loginAPIResponse = new LoginAPIResponse();
        BusinessAccountRESTResponse businessAccountRESTResponse = businessAccountRESTService.findStaffAccounts(
            new BusinessAccountRESTRequest(staffId, businessTypes)
        );
        List<BusinessAccountService> businessAccountServices = Lists.transform(
            businessAccountRESTResponse.getBusinessAccountServices(), new Function<BusinessAccountServiceView, BusinessAccountService>() {
                @Override
                public BusinessAccountService apply(final BusinessAccountServiceView input) {
                    BusinessAccountService businessAccountService = DataConverter.copy(new BusinessAccountService(), input);

                    try {
                        String aesKeyCoding ="ltb2Qbet+J0waeGgfprUmA==";
                        String plainPassword = AuthFactory.decryptPassword(input.getPassword());
//                        byte[] aesKey = AES.generateKey(128);
                        byte[] aesKey = BASE64.decrypt(aesKeyCoding);
                        String passwordAfterAESEncrypt = BASE64.encrypt(AES.encrypt(plainPassword, aesKey));
                        businessAccountService.setPassword(passwordAfterAESEncrypt);

                        byte[] keyBytes = RSA.encryptByPrivateKey(aesKey, ResourceUtil.getTextContent("key/private.pem"));
                        String randomAESEncryptKey = BASE64.encrypt(keyBytes);
                        businessAccountService.setRandomAESEncryptKey(randomAESEncryptKey);
                        return businessAccountService;
                    } catch (Exception e) {
                        throw new BusinessHandleException("login.error.password.encrypt");
                    }
                }
            }
        );
        loginAPIResponse.setBusinessAccountServices(businessAccountServices);
        return loginAPIResponse;
    }

}
