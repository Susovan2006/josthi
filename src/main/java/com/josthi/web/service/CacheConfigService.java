package com.josthi.web.service ;

import java.util.List;
import java.util.Map;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.po.CacheConfigPO;

public interface CacheConfigService {

	Map<String, List<CacheConfigPO>> getConfigData();

	List<DropDownBean> getBloodGroup(String bloodGroup);
	
	
}
