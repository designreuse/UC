package sync.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 洪 qq:2260806429
 */
public class MD5 {


	public static void main(String[] args) {

		System.out.println();
	}
	public static String md5(byte[] dataBytes, int pos, int len) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(dataBytes, pos, len);
			return toString(md);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static String toString(MessageDigest md) {
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

}
