package com.newcapec.wapcashdesk.utils.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Title: 系统信息--工具类
 * @ClassName:SysUtils.java
 * @Description:
 *
 * @Copyright 2016-2017 新开普 - Powered By 研发中心
 * @author: 王延飞
 * @date:2016年12月26日 下午8:36:43
 * @version V1.0
 */
public class SysUtils {

	/**
	 * 
	 * @Title: 获取本机IP
	 * @return
	 * @throws UnknownHostException String
	 * @Description:
	 *
	 * @author: 王延飞
	 * @date:2016年12月26日 下午8:37:03
	 */
	public static String getIpAddress() throws UnknownHostException {  
        InetAddress address = InetAddress.getLocalHost();  
        return address.getHostAddress();  
    }


	public static void main(String[] args) throws UnknownHostException {
		String ipAddress = getIpAddress();
		System.out.println(ipAddress);
	}
}
