package com.yealink.uc.platform.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static final String ALGORITHM_AES = "AES";
    private static final String CIPER_MODE = "AES";

    public static byte[] generateKey(int keySize) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM_AES);
            generator.init(keySize);
            return generator.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] encrypt(String value, String key) throws Exception {
        Cipher c = Cipher.getInstance(CIPER_MODE);

        SecretKeySpec keySpec = new SecretKeySpec(BASE64.decrypt(key), ALGORITHM_AES);

        c.init(Cipher.ENCRYPT_MODE, keySpec, new SecureRandom());

        return c.doFinal(value.getBytes());
    }

    public static byte[] encrypt(String value, byte[] key) throws Exception {
        Cipher c = Cipher.getInstance(CIPER_MODE);

        SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM_AES);

        c.init(Cipher.ENCRYPT_MODE, keySpec, new SecureRandom());

        return c.doFinal(value.getBytes());
    }

    public static byte[] decrypt(byte[] buff, String key) throws Exception {
        Cipher c = Cipher.getInstance(CIPER_MODE);

        SecretKeySpec keySpec = new SecretKeySpec(BASE64.decrypt(key), ALGORITHM_AES);

        c.init(Cipher.DECRYPT_MODE, keySpec, new SecureRandom());
        return c.doFinal(buff);
    }

    public static byte[] decrypt(byte[] buff, byte[] key) throws Exception {
        Cipher c = Cipher.getInstance(CIPER_MODE);

        SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM_AES);

        c.init(Cipher.DECRYPT_MODE, keySpec, new SecureRandom());
        return c.doFinal(buff);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(BASE64.encrypt(AES.generateKey(128)));
        System.out.println(BASE64.encrypt(AES.generateKey(256)));
        String msg = "Cloud_2016";
        String key = "ahkt0OgklBSCz1DY3lZVtw==";
        byte[] encontent = AES.encrypt(msg, key);
        byte[] decontent = AES.decrypt(encontent, key);
        System.out.println("明文是:" + msg);
        System.out.println("key:" + key);
        System.out.println("加密后:" + BASE64.encrypt(encontent));
        System.out.println("解密后:" + new String(decontent));
    }

}
