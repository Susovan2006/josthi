package com.josthi.web.utils;

import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@SuppressWarnings("deprecation")
public class HostNamePropertyPlaceHolderConfig extends PropertySourcesPlaceholderConfigurer{
	
	private static final Logger logger = LoggerFactory.getLogger(HostNamePropertyPlaceHolderConfig.class);
	
	private String hostName;
	private static Properties prop = null;
	
	
	public HostNamePropertyPlaceHolderConfig(final String hostName) {
		super();
		this.hostName = hostName;
		setLocation(getLocation());
		
	}


	private Resource getLocation() {

		final StringBuffer path = new StringBuffer().append("JosthiConfig_")
													.append(hostName)
													.append(".properties");
		
		ClassLoader classLoader = getClass().getClassLoader();
		URL u = classLoader.getResource(path.toString());
		final Resource resource = new FileSystemResource(u.getFile());
		
		if(!resource.exists()) {
			logger.error("Looks like the prop file is missing or the name is not matching, check the case sensitivity");
			throw new FatalBeanException("unable to find Resource:"+path.toString());
		}
		logger.info(path.toString() + " Loaded Susscessfully!!!");
		return resource;
	}
	
	

}
