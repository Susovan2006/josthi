package com.josthi.web.service ;
import java.util.List;

import com.josthi.web.bo.AgentAssignmentBean;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserDetailsBean ;
import com.josthi.web.bo.UserDetailsBeanForProfile;



public interface UserDetailService {

	UserDetailsBean getUserDetails(String customerId);

	String updateUserDetails(UserDetailsBean userDetailsBean, String custId);

	void setDataToDisplay(UserDetailsBean userDetailsfromDb, UserDetailsBean userDetailsBean);

	List<EmergencyContactBean> getEmergencyContactForUser(String customerId);

	boolean saveEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) throws Exception;

	void deleteEmergencyContact(int contactId);

	void getEmergencyContact(EmergencyContactBean emergencyContactBean ,int contactId, String customerId);

	boolean isValidContactId(Integer contactId);

	boolean updateEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) throws Exception;

	boolean updateAgentAdminProfile(UserDetailsBean userDetailsBean) throws Exception;

	UserDetailsBean getAgentAdminProfileDetails(String customerId) throws Exception;

	UserDetailsBeanForProfile getProfileDisplayDetails(String userID) throws Exception;

	List<AgentAssignmentBean> getBeneficiaryAgentDetailsList() throws Exception;

	boolean updateAgentForBeneficiary(String relationId, String newAgentId, String beneficiaryName, String hostUserId,
			String adminId) throws Exception;

	AgentAssignmentBean getBeneficiaryAgentDetail(String relationId) throws Exception;

	List<DropDownBean> getAgentListForDropDown() throws Exception;
	
}
