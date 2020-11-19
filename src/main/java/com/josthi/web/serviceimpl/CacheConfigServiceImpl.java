package com.josthi.web.serviceimpl ;
 import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.josthi.web.service.CacheConfigService ;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.dao.CacheConfigDao ;
import com.josthi.web.daoimpl.CacheConfigDaoImpl;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.po.CacheConfigPO;

@Service("cacheConfigService")
public class CacheConfigServiceImpl implements CacheConfigService{
	
	
	private static final Logger logger = LoggerFactory.getLogger(CacheConfigServiceImpl.class);
	
	@Autowired
	private CacheConfigDao cacheConfigDao;

	@Override
	public Map<String, List<CacheConfigPO>> getConfigData() {
		Map<String, List<CacheConfigPO>> configMap = new HashMap<String, List<CacheConfigPO>>();
		
		List<CacheConfigPO> cacheConfigBeanList = cacheConfigDao.getConfigData();
		List<String> htmlPageNameList = cacheConfigDao.getScreenNames();
		
		System.out.println(htmlPageNameList);
		
		//Here we are getting the HTML PageName List.
		for(String htmlPage : htmlPageNameList) {
			
			//We are Creating a list for each HTML Screen.
			List<CacheConfigPO> htmlPageDataBeanList = new ArrayList<CacheConfigPO>();
			
			
			//Here we are creating a sublist and assigning to 
			for(CacheConfigPO cacheConfigdata : cacheConfigBeanList) {
				if(htmlPage.equalsIgnoreCase(cacheConfigdata.getScreenName())) {
					htmlPageDataBeanList.add(cacheConfigdata);
				}
			}
			
			configMap.put(htmlPage, htmlPageDataBeanList);
		}
		
		return configMap;
	}

	@Override
	public List<DropDownBean> getBloodGroup(String bloodGroup) {
		return cacheConfigDao.getDropDownForGroupID(bloodGroup);
	}

	@Override
	public List<DropDownBean> getLanguages() throws Exception {
		try {
			return cacheConfigDao.getDropDownForGroupID(Constant.LANGUAGE);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UserExceptionInvalidData("Error Occured while fetching the Language values from the Server. Please try later");
		}
	}

	@Override
	public List<DropDownBean> getTimeZones() throws Exception {
		try {
			return cacheConfigDao.getDropDownForGroupID(Constant.TIME_ZONE);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new UserExceptionInvalidData("Error Occured while fetching the TimeZone values from the Server. Please try later");
		}
	}

	/*
	 * public void setCacheConfigDao(CacheConfigDaoImpl cacheConfigDaoImpl) {
	 * this.cacheConfigDao = cacheConfigDaoImpl; }
	 */

	


}
