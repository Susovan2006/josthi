package com.josthi.web.scheduler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.josthi.web.controller.CacheConfigDataController;


//Help : https://www.baeldung.com/running-setup-logic-on-startup-in-spring
@Component
public class LoadOnStartup{
 
	private static final Logger logger = LoggerFactory.getLogger(LoadOnStartup.class);
 
    @Autowired
    private CacheConfigDataController cacheConfigDataController;
 
	    
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	logger.info("Called ---> for Caching data");
    	cacheConfigDataController.getConfigData();
    }
}
