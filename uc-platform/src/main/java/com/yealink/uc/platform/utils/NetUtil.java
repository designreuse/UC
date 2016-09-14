/**
 * 
 */
package com.yealink.uc.platform.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yl0167
 * 
 */
public class NetUtil {
	/**
	 * Gets the remote ip.
	 * 
	 * @param request
	 *            the request
	 * @return the remote ip
	 */
	public static String getRemoteIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (isUnknowIP(ip)) {
			ip = request.getHeader("X-Cluster-Client-IP");
		}
		if (isUnknowIP(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (isUnknowIP(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (isUnknowIP(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (isUnknowIP(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (isUnknowIP(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	/**
	 * @param ip
	 * @return
	 */
	private static boolean isUnknowIP(String ip) {
		return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
	}
}
