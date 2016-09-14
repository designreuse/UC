package security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author ChNan
 */
public class Base64 {
    public static void main(String[] args) throws Exception {
        System.out.println(encryptBASE64("Dyl23123123123an".getBytes()));
    }
    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}
