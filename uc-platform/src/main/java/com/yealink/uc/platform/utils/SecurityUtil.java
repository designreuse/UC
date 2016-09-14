package com.yealink.uc.platform.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * Copy from vcCloud
 * The Class EncryptUtils.
 */
public class SecurityUtil {

    /**
     * The Constant AES_TRANSFORMATION.
     */
    private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * The Constant DEFAULT_RANDOM_PWD_LENGTH.
     */
    private static final int DEFAULT_RANDOM_PWD_LENGTH = 8;

    /**
     * The Constant UUID_SEPERATOR_STR.
     */
    private static final String UUID_SEPERATOR_STR = "-";

    /**
     * The Constant ALGORITHM_MD5.
     */
    private static final String ALGORITHM_MD5 = "MD5";

    /**
     * The Constant ALOGRITHM_AES.
     */
    private static final String ALOGRITHM_AES = "AES";

    /**
     * Hash迭代的次数，例如使用MD5时，则迭代2次.
     */
    private static final int HASH_ITERATIONS = 2;

    /**
     * The Constant TRANSFORMATION.
     */
    private static final String DES_TRANSFORMATION = "DESede/ECB/PKCS5Padding";

    /**
     * The Constant ALGORITHM_TRIPLE_DES.
     */
    private static final String ALGORITHM_TRIPLE_DES = "DESede";

    /**
     * The triple des key.
     */
    private static final byte[] TRIPLE_DES_KEY = "This_is_Yealink_1105_KEY"
        .getBytes();

    /**
     * The Constant AES_KEY.
     */
    private static final byte[] AES_KEY = "1105_VCCloud_Key".getBytes();

    /**
     * 默认的Hash迭代次数.
     */
    private static final int DEFAULT_ITERATIONS = 1;

    /**
     * 可用生成随机密码的英文字符，去掉o l I 3个容易混淆的字符.
     */
    private static final char[] PWD_LETTER_CHARS = new char[]{'a', 'b', 'c',
        'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E',
        'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * The Constant PWD_NUMBER_CHARS.
     */
    private static final char[] PWD_NUMBER_CHARS = new char[]{'0', '1', '2',
        '3', '4', '5', '6', '7', '8', '9'};

    /**
     * The Constant PWD_SPECIAL_CHARS.
     */
    private static final char[] PWD_SPECIAL_CHARS = new char[]{'!', '@', '#',
        '$', '%', '&', '*'};


    /**
     * 对字符串进行MD5加密，加密之后生成十六进制的字符串（全部小写）<br/>
     * 如果明文为空或者空内容，则返回空内容<br/>
     * 如果加密失败（未找到算法），则返回原来的内容.
     *
     * @param plainText 明文内容
     * @return MD5加密后的密文
     * @author lbt
     */
    public static String encryptMd5(final String plainText) {
        if (plainText == null || plainText.trim().isEmpty()) {
            return "";
        }

        try {
            return hash(plainText.getBytes("UTF-8"), null, DEFAULT_ITERATIONS);
        } catch (final UnsupportedEncodingException e) {
            return plainText;
        }
    }

    /**
     * 将字节转化成十六进制字符串（全部小写）.
     *
     * @param bytes the bytes
     * @return the string
     */
    private static String toHex(final byte[] bytes) {
        final StringBuffer hexValue = new StringBuffer();
        final int length = bytes.length;
        for (int i = 0; i < length; i++) {
            final int val = (bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString().toLowerCase();
    }

    /**
     * 根据传入的字符串返回一个Token，Token会采用Base64的方式进行加密<br/>
     * 如果字符串为空或为空字符串返回一个空字符串.
     *
     * @param str the str
     * @return token Base64加密之后的Token
     */
    public static String createCsrfToken(final String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        return Base64.encodeBase64String(str.getBytes());
    }

    /**
     * 生成一个16进制的随机盐.
     *
     * @return the string
     */
    private static String randSaltHex() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll(UUID_SEPERATOR_STR, "");
    }

    /**
     * 加密密码，如果盐或者密码为null或者空内容，则不进行加密，直接返回明文密码.
     *
     * @param saltHex       16进制盐
     * @param plainPassword 明文密码
     * @return 密文密码
     */
    public static String encryptPassword(final String saltHex,
                                         final String plainPassword) {
        if (saltHex == null || saltHex.isEmpty() || plainPassword == null
            || plainPassword.isEmpty()) {
            return plainPassword;
        }

        return hash(plainPassword.getBytes(), saltHex.getBytes(),
            HASH_ITERATIONS);
    }

    /**
     * 生成随机的密码(8位).
     *
     * @return the string
     */
    public static String generalRandomPassword() {
        return generalRandomPassword(DEFAULT_RANDOM_PWD_LENGTH, false)
            .toLowerCase();
    }

    /**
     * 生成随机的密码，随便生成英文字母、数字，字符数字通过随机生成：<br/>
     * 每次随机1和0的字母，如果为0则拼接英文，否则拼接数字.
     *
     * @param passwordLength 随机密码的长度
     * @param isNeedSpecChar 是否需要特殊字符包含，特殊字符包括'!', '@', '#', '$', '%', '&', '*'
     * @return the string
     */
    public static String generalRandomPassword(int passwordLength, boolean isNeedSpecChar) {
        if (isNeedSpecChar) {
            passwordLength--;
        }
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(generateRandomChars(passwordLength));

        if (isNeedSpecChar) {
            Random random = new Random();
            int specialChars = PWD_SPECIAL_CHARS.length;
            char specialChar = PWD_SPECIAL_CHARS[random.nextInt(specialChars) % specialChars];
            stringBuffer.insert(random.nextInt(passwordLength - 1), specialChar);
        }
        return stringBuffer.toString();
    }

    /**
     * 生成随机的，随便生成英文字母、数字，其中特殊符号为1个，其余字符数量通过随机生成：<br/>
     * 每次随机1和0的字母，如果为0则拼接英文，否则拼接数字.
     *
     * @param length 随机的长度字符串
     * @return the string
     */
    public static String generateRandomChars(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        int letterChars = PWD_LETTER_CHARS.length;
        int numberChars = PWD_NUMBER_CHARS.length;
        for (int i = 0; i < length; i++) {
            boolean isLetter = random.nextInt(2) == 0;
            if (isLetter) {
                builder.append(PWD_LETTER_CHARS[random.nextInt(letterChars) % letterChars]);
            } else {
                builder.append(PWD_NUMBER_CHARS[random.nextInt(numberChars) % numberChars]);
            }
        }

        return builder.toString();
    }

    /**
     * Hash加密明文.
     *
     * @param source         加密源
     * @param salt           盐.如果为空，则不进行盐加密
     * @param hashIterations hash迭代次数，默认迭代一次
     * @return 加密后的密文
     */
    private static String hash(final byte[] source, final byte[] salt,
                               final int hashIterations) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(ALGORITHM_MD5);
        } catch (final NoSuchAlgorithmException e) {
            return toHex(source);
        }
        if (salt != null) {
            digest.reset();
            digest.update(salt);
        }
        byte[] hashed = digest.digest(source);
        final int iterations = hashIterations - DEFAULT_ITERATIONS;
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return toHex(hashed);
    }

    /**
     * 加密用户名和密码，在加密密码时会通过随机生成盐<br/>
     * 盐+盐作为加密的凭证，然后与密码进行Hash迭代算法加密生成最终的密码.
     *
     * @param plainPassword 明文密码
     * @return 安全凭证
     */
    public static SecurityCredential encrypt(final String plainPassword) {
        final String salt = randSaltHex();
        final String credentialSalt = salt + salt;
        final String encryptedPassword = encryptPassword(credentialSalt,
            plainPassword);

        final SecurityCredential securityCredential = new SecurityCredential();
        securityCredential.setSalt(salt);
        securityCredential.setEncryptedPassword(encryptedPassword);
        return securityCredential;
    }

    /**
     * The Class SecurityCredential.
     */
    public static class SecurityCredential {

        /**
         * The salt.
         */
        private String salt;

        /**
         * The encrypted password.
         */
        private String encryptedPassword;

        /**
         * Gets the salt.
         *
         * @return the salt
         */
        public String getSalt() {
            return salt;
        }

        /**
         * Sets the salt.
         *
         * @param salt the new salt
         */
        public void setSalt(String salt) {
            this.salt = salt;
        }

        /**
         * Gets the encrypted password.
         *
         * @return the encrypted password
         */
        public String getEncryptedPassword() {
            return encryptedPassword;
        }

        /**
         * Sets the encrypted password.
         *
         * @param encryptedPswword the new encrypted password
         */
        public void setEncryptedPassword(String encryptedPswword) {
            this.encryptedPassword = encryptedPswword;
        }
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String args[]) {
        System.out.println(encryptMd5("fangweiquan"));
        System.out.println(generalRandomPassword(10, true));
        System.out.println(generalRandomPassword(10, false));
//        System.out.println(encryptPassword("Administrator"
//            + "f09a4424f3f375101bb9a5ce1b3ae341", encryptMd5("dms_2015")));
//
//        String content = "Te#22@st_3L23421bt";
//        // 加密
//        System.out.println("加密前：" + content);
//        String encryptResult = encryptAes(content);
//        System.out.println(encryptResult);
//        // 解密
//        String decryptResult = decryptAes(encryptResult);
//        System.out.println("解密后：" + decryptResult);
//        // 加密
//        String phone = "phone=240951220&random=1234646436464645464";
//        System.out.println("加密前：" + phone);
//        String encryptResult1 = encryptAes2(phone);
//        System.out.println(encryptResult1);
//        // 解密
//        String decryptResult1 = decryptAes2(encryptResult1);
//        System.out.println("解密后：" + decryptResult1);
//
//        String pin = "pin=596815830888&random=1234646436464645464";
//        System.out.println("加密前：" + pin);
//        String encryptResult2 = encryptAes2(pin);
//        System.out.println(encryptResult2);
//        decryptResult1 = decryptAes2(encryptResult2);
//        System.out.println("解密后：" + decryptResult1);
//
//        pin = "pin=355303789wre&random=1234646436464645464";
//        System.out.println("加密前：" + pin);
//        encryptResult2 = encryptAes2(pin);
//        System.out.println(encryptResult2);
//        decryptResult1 = decryptAes2(encryptResult2);
//        System.out.println("解密后：" + decryptResult1);
//        pin = "pin=123123123123&random=1234646436464645464";
//        System.out.println("加密前：" + pin);
//        encryptResult2 = encryptAes2(pin);
//        System.out.println(encryptResult2);
//        decryptResult1 = decryptAes2(encryptResult2);
//        System.out.println("解密后：" + decryptResult1);
    }

    /**
     * Encode base64.
     *
     * @param bytes the bytes
     * @return the string
     */
    private static String encodeBase64(byte[] bytes) {
        return new String(new Base64().encode(bytes));
    }

    /**
     * Decode base64.
     *
     * @param encodeStr the encode str
     * @return the byte[]
     */
    public static byte[] decodeBase64(String encodeStr) {
        return decodeBase64(encodeStr.getBytes());
    }

    /**
     * Decode base64.
     *
     * @param bytes the bytes
     * @return the byte[]
     */
    private static byte[] decodeBase64(byte[] bytes) {
        return new Base64().decode(bytes);
    }

    /**
     * 使用AES算法加密数据，密钥采用默认的（1105_VCCloud_Key），采用AES/ECB/PKCS5Padding加密<br/>
     * 加密之后采用Base64进行编码返回<br/>
     * 如果内容为空或者为空内容，则不加密返回空内容，<br/>
     * 如果加密中出现了异常，则不加密，返回原来的内容.
     *
     * @param content 需要加密的内容
     * @return 加密后的字符串
     * @author yl0167
     */
    public static String encryptAes(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        SecretKeySpec skeySpec = new SecretKeySpec(AES_KEY, ALOGRITHM_AES);
        try {
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return encodeBase64(cipher.doFinal(content.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            return content;
        } catch (NoSuchPaddingException e) {
            return content;
        } catch (InvalidKeyException e) {
            return content;
        } catch (IllegalBlockSizeException e) {
            return content;
        } catch (BadPaddingException e) {
            return content;
        }
    }

    /**
     * 解密AES内容，密钥采用默认的（1105_VCCloud_Key），采用AES/ECB/PKCS5Padding解密<br/>
     * 解密密之前采用Base64进行解码返回<br/>
     * 如果内容为空或者为空内容，则不解密返回空内容，<br/>
     * 如果解密中出现了异常，则不解密，返回原来的内容.
     *
     * @param content 要求内容采用Base64进行编码
     * @return 解密的内容
     * @author yl0167
     * @see SecurityUtil#encryptAes
     */
    public static String decryptAes(String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(AES_KEY, ALOGRITHM_AES);
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] original = cipher.doFinal(decodeBase64(content));
            return new String(original);
        } catch (NoSuchAlgorithmException e) {
            return content;
        } catch (NoSuchPaddingException e) {
            return content;
        } catch (InvalidKeyException e) {
            return content;
        } catch (IllegalBlockSizeException e) {
            return content;
        } catch (BadPaddingException e) {
            return content;
        }
    }

    /**
     * 使用3DES算法进行解密，密钥与加密时的密钥一样<br/>
     * 如果传递的密文参数为空或者空内容，则返回空内容<br/>
     * 如果解密失败（没有找到这样的算法或者加密的Key不合法），则返回原来的内容.
     *
     * @param encryptText the encrypt text
     * @return the string
     * @author lbt
     */
    public static String decryptTripleDes(final String encryptText) {
        if (encryptText == null || encryptText.trim().isEmpty()) {
            return "";
        }
        final SecretKey secretKey = new SecretKeySpec(TRIPLE_DES_KEY,
            ALGORITHM_TRIPLE_DES);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(DES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final byte[] decryptPlainText = cipher.doFinal(StringUtil.hexStringToBytes(encryptText));
            return new String(decryptPlainText);
        } catch (final NoSuchAlgorithmException e) {
            return encryptText;
        } catch (final NoSuchPaddingException e) {
            return encryptText;
        } catch (final IllegalBlockSizeException e) {
            return encryptText;
        } catch (final BadPaddingException e) {
            return encryptText;
        } catch (final InvalidKeyException e) {
            return encryptText;
        }

    }

    /**
     * 使用3DES的算法对字符串进行加密，采用DESede/ECB/PKCS5Padding的方式进行<br/>
     * 加密之后生成十六进制（全部大写）的密文<br/>
     * 如果明文为空或者空内容，则返回空内容<br/>
     * 如果加密失败（没有找到这样的算法或者加密的Key不合法），则返回原来的内容.
     *
     * @param plainText 明文
     * @return 3DES算法加密后的密文
     * @author lbt
     */
    public static String encryptTripleDes(final String plainText) {
        if (plainText == null || plainText.trim().isEmpty()) {
            return "";
        }
        final SecretKey secretKey = new SecretKeySpec(TRIPLE_DES_KEY,
            ALGORITHM_TRIPLE_DES);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(DES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return StringUtil.bytesToHexString(cipher.doFinal(plainText.getBytes()));
        } catch (final NoSuchAlgorithmException e) {
            return plainText;
        } catch (final NoSuchPaddingException e) {
            return plainText;
        } catch (final InvalidKeyException e) {
            return plainText;
        } catch (final IllegalBlockSizeException e) {
            return plainText;
        } catch (final BadPaddingException e) {
            return plainText;
        }
    }

    public static String encryptAes2(String content) {
        return encryptAes(content);
    }

    public static String decryptAes2(String content) {
        return decryptAes(content);
    }
}