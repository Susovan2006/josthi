package com.josthi.web.dao ;
import java.util.List;
import com.josthi.web.po.UserDetailsPO ;
import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserDetailsBean ;
import com.josthi.web.bo.UserRegistrationBean;

public interface UserDetailsDao {

	UserDetailsBean getUserDetails(String customerId);
	boolean updateUserDetails(UserDetailsBean userDetailsBean, String custId);
	List<EmergencyContactBean> getEmergencyContactListForUser(String customerId);
	boolean saveEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId);
	void deleteEmergencyContact(int contactId);
	EmergencyContactBean getEmergencyContactBean(int contactId, String customerId);
	int isValidContactId(Integer contactId);
	boolean updateEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId);
	
	//Beneficiary
	String getPrimaryBeneficiaryIdFromRelation(String customerId) throws Exception;
	String getSecondaryBeneficiaryIdFromRelation(String customerId) throws Exception;
	UserDetailsBean getPrimaryBeneficiaryDetails();
	UserDetailsBean getSecondaryBeneficiaryDetails();
	UserDetailsBean getBeneficiaryDetails(String primaryBeneficiaryId) throws Exception;
	List<BeneficiaryDetailBean> getBeneficiaryList(String customerId);
	boolean insertIntoUserDetail(BeneficiaryDetailBean beneficiaryDetailBean);
	boolean insertIntoBeneficiaryDetail(BeneficiaryDetailBean beneficiaryDetailBean);
	boolean insertIntoRelationDetail(BeneficiaryDetailBean beneficiaryDetailBean);
}
