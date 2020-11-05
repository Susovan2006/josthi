package com.josthi.web.dao ;
import java.util.List;
import com.josthi.web.po.UserDetailsPO ;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserDetailsBean ;

public interface UserDetailsDao {

	UserDetailsBean getUserDetails(String customerId);
	boolean updateUserDetails(UserDetailsBean userDetailsBean, String custId);
	List<EmergencyContactBean> getEmergencyContactListForUser(String customerId);
	boolean saveEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId);
	void deleteEmergencyContact(int contactId);
	EmergencyContactBean getEmergencyContactBean(int contactId, String customerId);
	int isValidContactId(Integer contactId);
	boolean updateEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId);
}
