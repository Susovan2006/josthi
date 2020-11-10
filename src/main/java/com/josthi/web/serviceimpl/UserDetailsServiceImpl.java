package com.josthi.web.serviceimpl ;
 import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.josthi.web.service.UserDetailService ;
import com.josthi.web.dao.UserDetailsDao ;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserDetailsBean ;

@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailService{

	@Autowired
	private UserDetailsDao userDetailsDao;
	
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
	public boolean saveEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) {
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
	public boolean updateEmergencyDetails(EmergencyContactBean emergencyContactBean, String custId) {
		// TODO Auto-generated method stub
		return userDetailsDao.updateEmergencyDetails(emergencyContactBean, custId);
	}

	


}