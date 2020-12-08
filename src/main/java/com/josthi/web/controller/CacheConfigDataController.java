package com.josthi.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.po.CacheConfigPO;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.serviceimpl.CacheConfigServiceImpl;
import com.josthi.web.utils.Utils;

@Controller("cacheConfigDataController")
public class CacheConfigDataController {

	public static Map<String, List<CacheConfigPO>> configMap = new HashMap<String, List<CacheConfigPO>>();
	public static List<DropDownBean> bloodGroupList = new ArrayList<DropDownBean>();
	
	private static final Logger logger = LoggerFactory.getLogger(CacheConfigDataController.class);
	
	@Autowired
	private CacheConfigService cacheConfigService;
	
	
	@GetMapping("/refresh")
	public void getConfigData() {
		try {
		   
			long startTime = System.currentTimeMillis();
		    
			configMap = cacheConfigService.getConfigData();
			bloodGroupList = cacheConfigService.getBloodGroup(Constant.BLOOD_GROUP);
			//serviceRequestTypeForAdminList = cacheConfigService.getBloodGroup(Constant.BLOOD_GROUP);
			
			logger.info("========================================================================");
			logger.info(Utils.getQueryTime("cacheConfigData()", startTime));
			logger.info("========================================================================");
		}catch(Exception ex) {
			logger.error("##### ERROR : Cache data not Loaded..");
			logger.error(ex.getMessage(), ex);
		}
	}



	
	/*
	 * public void setCacheConfigService(CacheConfigServiceImpl
	 * cacheConfigServiceImpl) { this.cacheConfigServiceImpl =
	 * cacheConfigServiceImpl; }
	 */

	
	
	
}
