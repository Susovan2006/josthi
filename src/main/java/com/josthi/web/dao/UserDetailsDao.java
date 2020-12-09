package com.josthi.web.dao ;
import java.util.List;
import com.josthi.web.po.UserDetailsPO ;
import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserDetailsBean ;
import com.josthi.web.bo.UserDetailsBeanForProfile;
import com.josthi.web.bo.UserRegistrationBean;

public interface UserDetailsDao {

	UserDetailsBean getUserDetails(String customerId);
	boolean updateUserDetails(UserDetailsBean userDetailsBean, String custId);
	List<EmergencyContactBean> getEmergencyContactListForUser(String customerId);
	boolean saveEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) throws Exception;
	void deleteEmergencyContact(int contactId);
	EmergencyContactBean getEmergencyContactBean(int contactId, String customerId);
	int isValidContactId(Integer contactId);
	boolean updateEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) throws Exception;
	
	//Beneficiary
	//String getPrimaryBeneficiaryIdFromRelation(String customerId) throws Exception;
	//String getSecondaryBeneficiaryIdFromRelation(String customerId) throws Exception;
	//UserDetailsBean getPrimaryBeneficiaryDetails();
	//UserDetailsBean getSecondaryBeneficiaryDetails();
	UserDetailsBean getBeneficiaryDetails(String primaryBeneficiaryId) throws Exception;
	List<BeneficiaryDetailBean> getBeneficiaryList(String customerId);
	boolean insertIntoUserDetail(BeneficiaryDetailBean beneficiaryDetailBean);
	boolean insertIntoBeneficiaryDetail(BeneficiaryDetailBean beneficiaryDetailBean);
	boolean insertIntoRelationDetail(BeneficiaryDetailBean beneficiaryDetailBean);
	
	
	boolean deleteBeneficiaryFromUserAuth(String beneficiaryID) throws Exception;
	boolean deleteBeneficiaryFromUserDetail(String beneficiaryID) throws Exception;
	boolean deleteBeneficiaryFromBeneficiaryDetail(String beneficiaryID) throws Exception;
	boolean deleteBeneficiaryFromRelationDetail(String beneficiaryID) throws Exception;
	BeneficiaryDetailBean getBeneficiaryDetailToEdit(String beneficiaryID, String customerId) throws Exception;
	boolean isValidBeneficiaryID(String beneficiaryID) throws Exception;
	//boolean updateBeneficiaryFromUserAuth(BeneficiaryDetailBean beneficiaryDetailBean) throws Exception;
	boolean updateBeneficiaryFromUserDetail(BeneficiaryDetailBean beneficiaryDetailBean) throws Exception;
	boolean updateBeneficiaryFromBeneficiaryDetail(BeneficiaryDetailBean beneficiaryDetailBean) throws Exception;
	List<DropDownBean> getBloodGroup(String bloodGroup);
	boolean updateAgentAdminProfile(UserDetailsBean userDetailsBean) throws Exception;
	UserDetailsBean getAgentAdminProfileDetails(String customerId) throws Exception;
	UserDetailsBeanForProfile getProfileDisplayDetails(String userID) throws Exception;
	BeneficiaryDetailBean getBeneficiaryDetailToView(String userID) throws Exception;
}
