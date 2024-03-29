package com.josthi.web.dao ;
import java.util.List;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.OnDemandServiceBean;
import com.josthi.web.bo.ServiceDetailsBean;
import com.josthi.web.po.CacheConfigPO ;



public interface CacheConfigDao {

	List<CacheConfigPO> getConfigData();

	List<String> getScreenNames();

	List<DropDownBean> getDropDownForGroupID(String bloodGroup);

	List<DropDownBean> getBeneficiaryList(String userId) throws Exception;

	List<DropDownBean> getAgentList(String customerId) throws Exception;

	List<ServiceDetailsBean> getServiceListToDisplayInMainScreen() throws Exception;

	boolean addUserQuery(String name, String email, String userNotes) throws Exception;

	List<OnDemandServiceBean> getOnDemandServicaListToDisplay() throws Exception;



}
