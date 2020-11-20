package com.josthi.web.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.josthi.web.service.ServiceRequestService;

//@Service("serviceRequestService")
public class ServiceRequestServiceImpl implements ServiceRequestService {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceRequestServiceImpl.class);
	
	private PlatformTransactionManager platformTransactionManager;
	
	public PlatformTransactionManager getPlatformTransactionManager() {
		return platformTransactionManager;
	}

	public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
		this.platformTransactionManager = platformTransactionManager;
	}
	
}
