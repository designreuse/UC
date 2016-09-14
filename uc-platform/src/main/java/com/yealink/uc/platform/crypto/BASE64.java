package com.yealink.uc.platform.crypto;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

/**
 * @author ChNan
 */
public class BASE64 {
    public static byte[] decrypt(String key) throws Exception {
        return Base64.decodeBase64(key);
    }
    public static byte[] decrypt(byte[] key) throws Exception {
        return Base64.decodeBase64(key);
    }
    public static String encrypt(byte[] bytes) throws Exception {
        return new String(Base64.encodeBase64(bytes), Charset.forName("UTF-8"));
    }
}
