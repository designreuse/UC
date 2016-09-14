package com.yealink.uc.api.modules.account.service;

import com.yealink.uc.api.modules.account.request.EditAccountPwdAPIRequest;
import com.yealink.uc.platform.crypto.AES;
import com.yealink.uc.platform.crypto.BASE64;
import com.yealink.uc.platform.crypto.RSA;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.ResourceUtil;
import com.yealink.uc.platform.utils.SecurityUtil;
import com.yealink.uc.service.modules.account.api.AccountRESTService;
import com.yealink.uc.service.modules.account.request.EditAccountPasswordRESTRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class AccountAPIService {
    @Autowired
    AccountRESTService accountRESTService;

    public boolean editPassword(EditAccountPwdAPIRequest request) {
        try {
            // 需要加密的地方，客户端随机生成一个AES的key，并且用事先分配的RSA public key加密，并且BASE64加密成字符串放到请求参数中传递过来，服务器端用RSA的私钥解密出AES密钥
            //String aesKey = "ahkt0OgklBSCz1DY3lZVtw==";
            String aesKey = BASE64.encrypt(RSA.decryptByPrivateKey(BASE64.decrypt(request.getRandomAESEncryptKey()), ResourceUtil.getTextContent("key/private.pem")));
            String decryptOldPassword = new String(AES.decrypt(BASE64.decrypt(request.getOldPassword()), aesKey));
            String decryptNewPassword = new String(AES.decrypt(BASE64.decrypt(request.getNewPassword()), aesKey));
            String md5OldPassword = SecurityUtil.encryptMd5(decryptOldPassword);
            String md5NewPassword = SecurityUtil.encryptMd5(decryptNewPassword);
            return accountRESTService.editPassword(new EditAccountPasswordRESTRequest(request.getAccountId(), md5OldPassword, md5NewPassword));
        } catch (Exception e) {
            throw new BusinessHandleException(e);
        }
    }

}
