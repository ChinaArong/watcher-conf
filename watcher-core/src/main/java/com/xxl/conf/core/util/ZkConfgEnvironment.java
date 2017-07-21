package com.xxl.conf.core.util;

import com.xxl.conf.core.exception.CanNotInitZkConnectException;
import org.springframework.stereotype.Component;

/**
 * 环境基类
 * @author xuxueli 2015-8-28 10:37:43
 */
@Component
public class ZkConfgEnvironment {

	/**
	 * conf data path in zk
     */
	private static String CONF_DATA_PATH = "/watcher-conf";

	private static String evn;

	private static String zkConnectString;

	public void setZkConnectString(String connectString) {
		zkConnectString = connectString;
		setConnectString(zkConnectString);
	}

	public void setEvn(String evn) {
		this.evn = evn;
		setConfDataPath(evn);
	}

	// 连接ZooKeeper
	private static String CONNECT_STRING;		// zk地址：格式	ip1:port,ip2:port,ip3:port

	public static void setConfDataPath(String evn) {
		if(evn != null){
			CONF_DATA_PATH = CONF_DATA_PATH.substring(0,1)+evn+"."+CONF_DATA_PATH.substring(1);
		}
	}

	public static void setConnectString(String connectString) {
		if(connectString == null){
			throw new CanNotInitZkConnectException();
		}
		CONNECT_STRING = connectString;
	}

	public static String getConfDataPath() {
		return CONF_DATA_PATH;
	}

	public static String getConnectString() {
		return CONNECT_STRING;
	}


}

