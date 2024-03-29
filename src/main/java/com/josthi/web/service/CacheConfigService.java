package com.josthi.web.service ;

import java.util.List;
import java.util.Map;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.OnDemandServiceBean;
import com.josthi.web.bo.PriceMonthlyAndYearly;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.po.CacheConfigPO;

public interface CacheConfigService {

	Map<String, List<CacheConfigPO>> getConfigData();

	List<DropDownBean> getBloodGroup(String bloodGroup);

	List<DropDownBean> getLanguages() throws Exception;

	List<DropDownBean> getTimeZones() throws Exception;

	List<DropDownBean> getBeneficiaryList(String userId) throws Exception;

	List<DropDownBean> getServiceTypeList(String userId) throws Exception;

	List<DropDownBean> getServicaCategoryList(String userId) throws Exception;

	List<DropDownBean> getOnDemandServicaList() throws Exception;

	List<DropDownBean> getUrgencyTypeList() throws Exception;
	
	List<DropDownBean> getDefaultList() throws Exception;

	List<DropDownBean> getPLanType(String dropdownPlanType) throws Exception;

	List<DropDownBean> getAgentList(String customerId) throws Exception;

	List<ServiceDetailsBean> getServiceListToDisplayInMainScreen() throws Exception;

	boolean addUserQuery(String name, String email, String userNotes) throws Exception;

	PriceMonthlyAndYearly getPlanPriceToDisplay() throws Exception;

	List<OnDemandServiceBean> getOnDemandServicaBeanList() throws Exception;
	
	
}
