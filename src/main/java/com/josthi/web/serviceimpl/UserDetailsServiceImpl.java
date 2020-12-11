package com.josthi.web.serviceimpl ;
 import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.josthi.web.service.UserDetailService ;
import com.josthi.web.dao.HistoryDao;
import com.josthi.web.dao.UserDetailsDao ;
import com.josthi.web.bo.AgentAssignmentBean;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserDetailsBean ;
import com.josthi.web.bo.UserDetailsBeanForProfile;

@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailService{

	@Autowired
	private UserDetailsDao userDetailsDao;
	
	@Autowired
	private HistoryDao historyDao;
	
	@Override
	public UserDetailsBean getUserDetails(String customerId) {		
		return userDetailsDao.getUserDetails(customerId);
	}

	@Override
	public String updateUserDetails(UserDetailsBean userDetailsBean, String custId) {
		try {
			boolean status = userDetailsDao.updateUserDetails(userDetailsBean, custId);
			return "Success";
		}catch(Exception ex) {
			throw ex;
		}
		
	}

	@Override
	public void setDataToDisplay(UserDetailsBean userDetailsfromDb, UserDetailsBean userDetailsBean) {
		userDetailsBean.setFirstName(userDetailsfromDb.getFirstName());
    	userDetailsBean.setLastName(userDetailsfromDb.getLastName());
    	userDetailsBean.setUid(userDetailsfromDb.getUid());
    	userDetailsBean.setGender(userDetailsfromDb.getGender());
    	
    	userDetailsBean.setUserAddressFirstLine(userDetailsfromDb.getUserAddressFirstLine());
    	userDetailsBean.setUserAddressSecondLine(userDetailsfromDb.getUserAddressSecondLine());
		userDetailsBean.setCityTown(userDetailsfromDb.getCityTown());
		userDetailsBean.setState(userDetailsfromDb.getState());
		userDetailsBean.setCountyDistrict(userDetailsfromDb.getCountyDistrict());
		userDetailsBean.setCountry(userDetailsfromDb.getCountry());
		userDetailsBean.setZipPin(userDetailsfromDb.getZipPin());
		
		userDetailsBean.setMobileNo1(userDetailsfromDb.getMobileNo1());
		userDetailsBean.setMobileNo2(userDetailsfromDb.getMobileNo2());
		userDetailsBean.setWhatsappNo(userDetailsfromDb.getWhatsappNo());
		userDetailsBean.setLandLineNo(userDetailsfromDb.getLandLineNo());
		userDetailsBean.setFaxNo(userDetailsfromDb.getFaxNo());
		userDetailsBean.setOfficePhNo(userDetailsfromDb.getOfficePhNo());
		
		userDetailsBean.setSecondaryEmail(userDetailsfromDb.getSecondaryEmail());
		userDetailsBean.setWebsite(userDetailsfromDb.getWebsite());
		userDetailsBean.setFacebookLink(userDetailsfromDb.getFacebookLink());

		userDetailsBean.setUserStatus(userDetailsfromDb.getUserStatus());
		
	}

	@Override
	public List<EmergencyContactBean> getEmergencyContactForUser(String customerId) {		
		return userDetailsDao.getEmergencyContactListForUser(customerId);
	}

	@Override
	public boolean saveEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) throws Exception{
		return userDetailsDao.saveEmergencyDetails(emergencyContactBean, custId);
	}

	@Override
	public void deleteEmergencyContact(int contactId) {
		userDetailsDao.deleteEmergencyContact(contactId);
		
	}

	@Override
	public void getEmergencyContact(EmergencyContactBean emergencyContactBean, int contactId, String customerId) {

		EmergencyContactBean emergencyContactBeanFromDB = userDetailsDao.getEmergencyContactBean(contactId,customerId);
				
		emergencyContactBean.setContactId(emergencyContactBeanFromDB.getContactId());
		emergencyContactBean.setPrimaryUid(emergencyContactBeanFromDB.getPrimaryUid());
		emergencyContactBean.setBenId(emergencyContactBeanFromDB.getBenId());
		emergencyContactBean.setEmergencyContactName(emergencyContactBeanFromDB.getEmergencyContactName());
		emergencyContactBean.setEmergencyContactNumber(emergencyContactBeanFromDB.getEmergencyContactNumber());
		emergencyContactBean.setEmergencyContactStayLocation(emergencyContactBeanFromDB.getEmergencyContactStayLocation());
		emergencyContactBean.setRelation(emergencyContactBeanFromDB.getRelation());
		emergencyContactBean.setContactNoValidated(emergencyContactBeanFromDB.getContactNoValidated());
		emergencyContactBean.setNotes(emergencyContactBeanFromDB.getNotes());
	}

	@Override
	public boolean isValidContactId(Integer contactId) {
		boolean result = false; 
		 int count = userDetailsDao.isValidContactId(contactId);
		 if (count > 0) { result = true; }
		 return result;
	}

	@Override
	public boolean updateEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) throws Exception{
		return userDetailsDao.updateEmergencyDetails(emergencyContactBean, custId);
	}

	
	
	
	//*****************************************************************************************************************
	//******************** A G E N T    A D M I N    S E C T I O N  ***************************************************
	//*****************************************************************************************************************
	
	
	@Override
	public boolean updateAgentAdminProfile(UserDetailsBean userDetailsBean) throws Exception {
		try {
			return userDetailsDao.updateAgentAdminProfile(userDetailsBean);
		}catch(Exception ex) {
			throw ex;
		}
	}

	@Override
	public UserDetailsBean getAgentAdminProfileDetails(String customerId) throws Exception {
		return userDetailsDao.getAgentAdminProfileDetails(customerId);
	}

	@Override
	public UserDetailsBeanForProfile getProfileDisplayDetails(String userID) throws Exception {
		return userDetailsDao.getProfileDisplayDetails(userID);
	}

	@Override
	public List<AgentAssignmentBean> getBeneficiaryAgentDetailsList() throws Exception {
		
		return userDetailsDao.getBeneficiaryAgentDetailsList();
	}

	@Override
	public boolean updateAgentForBeneficiary(String relationId, String newAgentId, String beneficiaryName,
			String hostUserId, String adminId) throws Exception {
		String activityFor = hostUserId;
		String activityBy = adminId;
		String activityNotes = "New Agent "+newAgentId+ " assigned to "+beneficiaryName;
		try {
			boolean logstatus = historyDao.logActivityHistory(activityFor, activityBy, activityNotes);
		}catch(Exception ex) {
			//nothing to do. just suppress.
		}
		return userDetailsDao.updateAgentForBeneficiary(relationId, newAgentId, beneficiaryName, hostUserId, adminId);
	}

	@Override
	public AgentAssignmentBean getBeneficiaryAgentDetail(String relationId) throws Exception {
		return userDetailsDao.getBeneficiaryAgentDetail(relationId);
	}

	@Override
	public List<DropDownBean> getAgentListForDropDown() throws Exception {		
		return userDetailsDao.getAgentListForDropDown();
	}

	


}
