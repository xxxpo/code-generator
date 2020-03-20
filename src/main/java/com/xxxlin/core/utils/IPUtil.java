/**
 * Date:		 2018年6月24日下午8:34:19
 * Copyright (c) 2018, xxxlin.com All Rights Reserved.
 *
 */

package com.xxxlin.core.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取访问地址
 * Date:     2018年6月24日 下午8:34:19
 * @author   XiaoLin
 * @version  0.1
 */
public class IPUtil {

	public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
