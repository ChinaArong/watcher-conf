package com.xxl.conf.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 环境基类
 * @author xuxueli 2015-8-28 10:37:43
 */
@Component
public class Environment {

	/**
	 * conf data path in zk
     */
	public static final String CONF_DATA_PATH = "/watcher-conf";

	/**
	 * zk address
     */
	public static String ZK_ADDRESS;		// zk地址：格式	ip1:port,ip2:port,ip3:port

	public static String getZkAddress() {
		if(ZK_ADDRESS == null){
			Properties prop = PropertiesUtil.loadProperties("config.properties");
			ZK_ADDRESS = PropertiesUtil.getString(prop, "zkService");
		}
		return ZK_ADDRESS;
	}

	public static void setZkAddress(String zkAddress) {
		ZK_ADDRESS = zkAddress;
	}
}

