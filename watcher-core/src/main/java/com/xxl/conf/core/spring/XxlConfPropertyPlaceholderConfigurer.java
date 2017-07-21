package com.xxl.conf.core.spring;

import com.google.common.collect.Sets;
import com.google.gson.GsonBuilder;
import com.xxl.conf.core.XxlConfZkClient;
import com.xxl.conf.core.util.ZkConfgEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * rewrite PropertyPlaceholderConfigurer
 * @version 1.0
 * @author xuxueli 2015-9-12 19:42:49
 *
 * <bean id="xxlConfPropertyPlaceholderConfigurer" class="com.xxl.conf.core.spring.XxlConfPropertyPlaceholderConfigurer" />
 *
 */
public class XxlConfPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private static Logger logger = LoggerFactory.getLogger(XxlConfPropertyPlaceholderConfigurer.class);

	public static final String SYSTEM_PREFIX = "system.";

	private String fileEncoding;

	private String connectString;

	private String evnName;

	private String[] placeholderConfigLocations;

	public void setEvnName(String evnName) {
		this.evnName = evnName;
	}

	public void setConnectString(String connectString) {
		this.connectString = connectString;
	}

	public void setPlaceholderConfigLocations(String[] placeholderConfigLocations) {
		this.placeholderConfigLocations = placeholderConfigLocations;
	}

	public String[] getPlaceholderConfigLocations() {
		return placeholderConfigLocations;
	}

	@Override
	protected Properties mergeProperties() throws IOException {
		final Properties properties = super.mergeProperties();
		setSystemEnvProperties(properties);
		for(String configLocation:getPlaceholderConfigLocations()){
			String envConfigLocation = parseStringValue(configLocation, properties, Sets.newHashSet());
			Resource resource = new ClassPathResource(envConfigLocation.substring(ResourceUtils.CLASSPATH_URL_PREFIX
					.length()), ClassUtils.getDefaultClassLoader());
			PropertiesLoaderUtils.fillProperties(properties, new EncodedResource(resource, this.fileEncoding).getResource());
			//从zk上拉取所有的配置属性
			if(connectString != null){
				connectString = (String) properties.get(parsePlaceholder(connectString));
			}
			if(evnName != null){
				evnName = (String) properties.get(parsePlaceholder(evnName));
			}
			ZkConfgEnvironment.setConnectString(connectString);
			ZkConfgEnvironment.setConfDataPath(evnName);
			Map<String, String> allData = XxlConfZkClient.getAllData();
			logger.info("<<<<<<<<<<<<<<<<<Get param from zk:{}", new GsonBuilder().create().toJson(allData));
			for(Map.Entry<String, String> param : allData.entrySet()){
				properties.setProperty(param.getKey(),param.getValue());
			}
		}
		return properties;
	}

	private void setSystemEnvProperties(Properties properties) {
		Set<Object> keys = properties.keySet();
		try {
			for (Object obj : keys) {
				if (obj instanceof String) {
					String key = (String) obj;
					if (key.startsWith(SYSTEM_PREFIX) || key.startsWith(SYSTEM_PREFIX.toUpperCase())) {
						String subfix = null;
						if (key.indexOf(SYSTEM_PREFIX) != -1) {
							subfix = key.substring(key.indexOf(SYSTEM_PREFIX) + SYSTEM_PREFIX.length());
						}
						if (key.indexOf(SYSTEM_PREFIX.toUpperCase()) != -1) {
							subfix = key.substring(key.indexOf(SYSTEM_PREFIX.toUpperCase()) + SYSTEM_PREFIX.length());
						}
						if (System.getProperty(subfix) == null) {
							System.setProperty(subfix, properties.getProperty(key));
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	private String beanName;
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	private BeanFactory beanFactory;
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setIgnoreUnresolvablePlaceholders(boolean ignoreUnresolvablePlaceholders) {
		super.setIgnoreUnresolvablePlaceholders(true);
	}

	@Override
	public void setFileEncoding(String encoding) {
		this.fileEncoding = encoding;
		super.setFileEncoding(encoding);
	}

	private String parsePlaceholder(String strVal){
		StringBuilder result = new StringBuilder(strVal);
		int startIndex = strVal.indexOf(this.placeholderPrefix);
		if (startIndex != -1) {
			int endIndex = findPlaceholderEndIndex(result, startIndex);
			if (endIndex != -1) {
				return result.substring(startIndex + this.placeholderPrefix.length(), endIndex);
			}
		}
		return null;
	}

	private int findPlaceholderEndIndex(CharSequence buf, int startIndex) {
		int index = startIndex + this.placeholderPrefix.length();
		int withinNestedPlaceholder = 0;
		while (index < buf.length()) {
			if (StringUtils.substringMatch(buf, index, this.placeholderSuffix)) {
				if (withinNestedPlaceholder > 0) {
					withinNestedPlaceholder--;
					index = index + this.placeholderSuffix.length();
				}
				else {
					return index;
				}
			}
			index ++;
		}
		return -1;
	}

}
