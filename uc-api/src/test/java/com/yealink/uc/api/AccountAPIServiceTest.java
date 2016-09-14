package com.yealink.uc.api;

import com.yealink.uc.platform.crypto.AES;
import com.yealink.uc.platform.crypto.BASE64;
import com.yealink.uc.platform.crypto.RSA;
import com.yealink.uc.platform.utils.ResourceUtil;

/**
 * @author ChNan
 */
public class AccountAPIServiceTest {

    public static void main(String[] args) throws Exception {
//        String aesKey = BASE64.encrypt(AES.generateKey(128));
//        System.out.println("Plain AES key  = " + aesKey);
//        byte[] bytes = RSA.encryptByPublicKey(BASE64.decrypt(aesKey), ResourceUtil.getTextContent("key/public.key"));
//        String randomAESEncryptKey = BASE64.encrypt(bytes);
//        System.out.println("Encrypt  size is  = " + bytes.length);
//        System.out.println("Encrypt  AES key  = " + randomAESEncryptKey);
//
//        String oldPassword = BASE64.encrypt(AES.encrypt("Cloud_2016", aesKey));
//
//        System.out.println("Old password  = " + oldPassword);
//
//        String newPassword = BASE64.encrypt(AES.encrypt("Cloud_2017", aesKey));
//
//        System.out.println("New password  = " + newPassword);
//
//        String aesKey2 = BASE64.encrypt(RSA.decryptByPrivateKey(BASE64.decrypt(randomAESEncryptKey), ResourceUtil.getTextContent("key/private.key")));
//
            byte[] aesKey = AES.generateKey(128);
            System.out.println("aesKey: " + BASE64.encrypt(aesKey));
            String passwordAfterAESEncrypt = BASE64.encrypt(AES.encrypt("Cloud_2016", aesKey));
            System.out.println("passwordAfterAESEncrypt: " + passwordAfterAESEncrypt);
            byte[] keyBytes = RSA.encryptByPrivateKey(aesKey, ResourceUtil.getTextContent("key/private.pem"));
            String randomAESEncryptKey = BASE64.encrypt(keyBytes);
            System.out.println("randomAESEncryptKey: " + randomAESEncryptKey);

            byte[] aesKey3 = RSA.decryptByPublicKey(BASE64.decrypt(randomAESEncryptKey),
                ResourceUtil.getTextContent("key/public.pem"));
            String pwd = new String(AES.decrypt(BASE64.decrypt(passwordAfterAESEncrypt), aesKey3));
            System.out.println("pwd  = " + pwd);
    }
}
