package security;

import java.security.MessageDigest;

/**
 * @author ChNan
 */
public class SHA {
    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(data);

        return sha.digest();

    }

}
