package com.josthi.web.daoimpl ;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import com.josthi.web.po.UserDetailsPO ;
import com.josthi.web.dao.UserDetailsDao ;
import com.josthi.web.bo.UserDetailsBean ;
//@Repository
public class UserDetailsController implements UserDetailsDao {
	private final String SEL_USER_DETAIL = "Select * from user_detail nolock where UID=:uid and FIRST_NAME=:firstName and MIDDLE NAME=:middle name and LAST_NAME=:lastName and USER_ADDRESS_FIRST_LINE=:userAddressFirstLine and USER_ADDRESS_SECOND_LINE=:userAddressSecondLine and CITY_TOWN=:cityTown and STATE=:state and COUNTY_DISTRICT=:countyDistrict and COUNTRY=:country and MOBILE_NO1=:mobileNo1 and MOBILE_NO2=:mobileNo2 and WHATSAPP_NO=:whatsappNo and SECONDARY_EMAIL=:secondaryEmail and EMERGENCY_CONTACT=:emergencyContact and WEBSITE=:website and FACEBOOK_LINK=:facebookLink and INSTAGRAM_LINK=:instagramLink and BENEFICIARY_COUNT=:beneficiaryCount and PHOTO_ID=:photoId and PHOTO_PASSPORT_SIZE=:photoPassportSize and PLAN_TYPE_NAME=:planTypeName and PLAN_ACTIVE=:planActive and PLAN_SUBSCRIBE_DATE=:planSubscribeDate and MONTHLY_YEARLY_PLAN=:monthlyYearlyPlan and PLAN_RENUE_DATE=:planRenueDate and REMINDER_ENABLED=:reminderEnabled and COMMENTS=:comments and USER_STATUS=:userStatus  ";
	private final String UPD_USER_DETAIL = "Update user_detail set UID=:uid,FIRST_NAME=:firstName,MIDDLE NAME=:middle name,LAST_NAME=:lastName,USER_ADDRESS_FIRST_LINE=:userAddressFirstLine,USER_ADDRESS_SECOND_LINE=:userAddressSecondLine,CITY_TOWN=:cityTown,STATE=:state,COUNTY_DISTRICT=:countyDistrict,COUNTRY=:country,MOBILE_NO1=:mobileNo1,MOBILE_NO2=:mobileNo2,WHATSAPP_NO=:whatsappNo,SECONDARY_EMAIL=:secondaryEmail,EMERGENCY_CONTACT=:emergencyContact,WEBSITE=:website,FACEBOOK_LINK=:facebookLink,INSTAGRAM_LINK=:instagramLink,BENEFICIARY_COUNT=:beneficiaryCount,PHOTO_ID=:photoId,PHOTO_PASSPORT_SIZE=:photoPassportSize,PLAN_TYPE_NAME=:planTypeName,PLAN_ACTIVE=:planActive,PLAN_SUBSCRIBE_DATE=:planSubscribeDate,MONTHLY_YEARLY_PLAN=:monthlyYearlyPlan,PLAN_RENUE_DATE=:planRenueDate,REMINDER_ENABLED=:reminderEnabled,COMMENTS=:comments,USER_STATUS=:userStatus  where UID=:newUid,FIRST_NAME=:newFirstName,MIDDLE NAME=:newMiddle name,LAST_NAME=:newLastName,USER_ADDRESS_FIRST_LINE=:newUserAddressFirstLine,USER_ADDRESS_SECOND_LINE=:newUserAddressSecondLine,CITY_TOWN=:newCityTown,STATE=:newState,COUNTY_DISTRICT=:newCountyDistrict,COUNTRY=:newCountry,MOBILE_NO1=:newMobileNo1,MOBILE_NO2=:newMobileNo2,WHATSAPP_NO=:newWhatsappNo,SECONDARY_EMAIL=:newSecondaryEmail,EMERGENCY_CONTACT=:newEmergencyContact,WEBSITE=:newWebsite,FACEBOOK_LINK=:newFacebookLink,INSTAGRAM_LINK=:newInstagramLink,BENEFICIARY_COUNT=:newBeneficiaryCount,PHOTO_ID=:newPhotoId,PHOTO_PASSPORT_SIZE=:newPhotoPassportSize,PLAN_TYPE_NAME=:newPlanTypeName,PLAN_ACTIVE=:newPlanActive,PLAN_SUBSCRIBE_DATE=:newPlanSubscribeDate,MONTHLY_YEARLY_PLAN=:newMonthlyYearlyPlan,PLAN_RENUE_DATE=:newPlanRenueDate,REMINDER_ENABLED=:newReminderEnabled,COMMENTS=:newComments,USER_STATUS=:newUserStatus ";
	private final String INS_USER_DETAIL = "Insert into user_detail (UID,FIRST_NAME,MIDDLE NAME,LAST_NAME,USER_ADDRESS_FIRST_LINE,USER_ADDRESS_SECOND_LINE,CITY_TOWN,STATE,COUNTY_DISTRICT,COUNTRY,MOBILE_NO1,MOBILE_NO2,WHATSAPP_NO,SECONDARY_EMAIL,EMERGENCY_CONTACT,WEBSITE,FACEBOOK_LINK,INSTAGRAM_LINK,BENEFICIARY_COUNT,PHOTO_ID,PHOTO_PASSPORT_SIZE,PLAN_TYPE_NAME,PLAN_ACTIVE,PLAN_SUBSCRIBE_DATE,MONTHLY_YEARLY_PLAN,PLAN_RENUE_DATE,REMINDER_ENABLED,COMMENTS,USER_STATUS) values (uid,firstName,middle name,lastName,userAddressFirstLine,userAddressSecondLine,cityTown,state,countyDistrict,country,mobileNo1,mobileNo2,whatsappNo,secondaryEmail,emergencyContact,website,facebookLink,instagramLink,beneficiaryCount,photoId,photoPassportSize,planTypeName,planActive,planSubscribeDate,monthlyYearlyPlan,planRenueDate,reminderEnabled,comments,userStatus) ";
	private final String DEL_USER_DETAIL = "Delete from user_detail where UID=:uid and FIRST_NAME=:firstName and MIDDLE NAME=:middle name and LAST_NAME=:lastName and USER_ADDRESS_FIRST_LINE=:userAddressFirstLine and USER_ADDRESS_SECOND_LINE=:userAddressSecondLine and CITY_TOWN=:cityTown and STATE=:state and COUNTY_DISTRICT=:countyDistrict and COUNTRY=:country and MOBILE_NO1=:mobileNo1 and MOBILE_NO2=:mobileNo2 and WHATSAPP_NO=:whatsappNo and SECONDARY_EMAIL=:secondaryEmail and EMERGENCY_CONTACT=:emergencyContact and WEBSITE=:website and FACEBOOK_LINK=:facebookLink and INSTAGRAM_LINK=:instagramLink and BENEFICIARY_COUNT=:beneficiaryCount and PHOTO_ID=:photoId and PHOTO_PASSPORT_SIZE=:photoPassportSize and PLAN_TYPE_NAME=:planTypeName and PLAN_ACTIVE=:planActive and PLAN_SUBSCRIBE_DATE=:planSubscribeDate and MONTHLY_YEARLY_PLAN=:monthlyYearlyPlan and PLAN_RENUE_DATE=:planRenueDate and REMINDER_ENABLED=:reminderEnabled and COMMENTS=:comments and USER_STATUS=:userStatus  ";
	
	
	/*private SimpleJdbcTemplate jdbcTemplate;
	@Autowired
	public void setJdbcTemplate(DataSource josthiDbDataSource) {
		this.jdbcTemplate = new SimpleJdbcTemplate(josthiDbDataSource);
	}
	@Override
	public List<UserDetailsPO> selectUserDetails(UserDetailsBean userDetailsBean){
		RowMapper<UserDetailsPO> stdRowMapper =new UserDetailRowMapper() ;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid",userDetailsBean.getUid()) ;

		map.put("firstName",userDetailsBean.getFirstName()) ;

		map.put("middle name",userDetailsBean.getMiddle name()) ;

		map.put("lastName",userDetailsBean.getLastName()) ;

		map.put("userAddressFirstLine",userDetailsBean.getUserAddressFirstLine()) ;

		map.put("userAddressSecondLine",userDetailsBean.getUserAddressSecondLine()) ;

		map.put("cityTown",userDetailsBean.getCityTown()) ;

		map.put("state",userDetailsBean.getState()) ;

		map.put("countyDistrict",userDetailsBean.getCountyDistrict()) ;

		map.put("country",userDetailsBean.getCountry()) ;

		map.put("mobileNo1",userDetailsBean.getMobileNo1()) ;

		map.put("mobileNo2",userDetailsBean.getMobileNo2()) ;

		map.put("whatsappNo",userDetailsBean.getWhatsappNo()) ;

		map.put("secondaryEmail",userDetailsBean.getSecondaryEmail()) ;

		map.put("emergencyContact",userDetailsBean.getEmergencyContact()) ;

		map.put("website",userDetailsBean.getWebsite()) ;

		map.put("facebookLink",userDetailsBean.getFacebookLink()) ;

		map.put("instagramLink",userDetailsBean.getInstagramLink()) ;

		map.put("beneficiaryCount",userDetailsBean.getBeneficiaryCount()) ;

		map.put("photoId",userDetailsBean.getPhotoId()) ;

		map.put("photoPassportSize",userDetailsBean.getPhotoPassportSize()) ;

		map.put("planTypeName",userDetailsBean.getPlanTypeName()) ;

		map.put("planActive",userDetailsBean.getPlanActive()) ;

		map.put("planSubscribeDate",userDetailsBean.getPlanSubscribeDate()) ;

		map.put("monthlyYearlyPlan",userDetailsBean.getMonthlyYearlyPlan()) ;

		map.put("planRenueDate",userDetailsBean.getPlanRenueDate()) ;

		map.put("reminderEnabled",userDetailsBean.getReminderEnabled()) ;

		map.put("comments",userDetailsBean.getComments()) ;

		map.put("userStatus",userDetailsBean.getUserStatus()) ;

		return jdbcTemplate.query(SEL_USER_DETAIL,stdRowMapper,map);
	}
	@Override
	public void updateUserDetails(UserDetailsBean userDetailsBean){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid",userDetailsBean.getUid()) ;

		map.put("firstName",userDetailsBean.getFirstName()) ;

		map.put("middle name",userDetailsBean.getMiddle name()) ;

		map.put("lastName",userDetailsBean.getLastName()) ;

		map.put("userAddressFirstLine",userDetailsBean.getUserAddressFirstLine()) ;

		map.put("userAddressSecondLine",userDetailsBean.getUserAddressSecondLine()) ;

		map.put("cityTown",userDetailsBean.getCityTown()) ;

		map.put("state",userDetailsBean.getState()) ;

		map.put("countyDistrict",userDetailsBean.getCountyDistrict()) ;

		map.put("country",userDetailsBean.getCountry()) ;

		map.put("mobileNo1",userDetailsBean.getMobileNo1()) ;

		map.put("mobileNo2",userDetailsBean.getMobileNo2()) ;

		map.put("whatsappNo",userDetailsBean.getWhatsappNo()) ;

		map.put("secondaryEmail",userDetailsBean.getSecondaryEmail()) ;

		map.put("emergencyContact",userDetailsBean.getEmergencyContact()) ;

		map.put("website",userDetailsBean.getWebsite()) ;

		map.put("facebookLink",userDetailsBean.getFacebookLink()) ;

		map.put("instagramLink",userDetailsBean.getInstagramLink()) ;

		map.put("beneficiaryCount",userDetailsBean.getBeneficiaryCount()) ;

		map.put("photoId",userDetailsBean.getPhotoId()) ;

		map.put("photoPassportSize",userDetailsBean.getPhotoPassportSize()) ;

		map.put("planTypeName",userDetailsBean.getPlanTypeName()) ;

		map.put("planActive",userDetailsBean.getPlanActive()) ;

		map.put("planSubscribeDate",userDetailsBean.getPlanSubscribeDate()) ;

		map.put("monthlyYearlyPlan",userDetailsBean.getMonthlyYearlyPlan()) ;

		map.put("planRenueDate",userDetailsBean.getPlanRenueDate()) ;

		map.put("reminderEnabled",userDetailsBean.getReminderEnabled()) ;

		map.put("comments",userDetailsBean.getComments()) ;

		map.put("userStatus",userDetailsBean.getUserStatus()) ;

		map.put("newUid",userDetailsBean.getUid()) ;

		map.put("newFirstName",userDetailsBean.getFirstName()) ;

		map.put("newMiddle name",userDetailsBean.getMiddle name()) ;

		map.put("newLastName",userDetailsBean.getLastName()) ;

		map.put("newUserAddressFirstLine",userDetailsBean.getUserAddressFirstLine()) ;

		map.put("newUserAddressSecondLine",userDetailsBean.getUserAddressSecondLine()) ;

		map.put("newCityTown",userDetailsBean.getCityTown()) ;

		map.put("newState",userDetailsBean.getState()) ;

		map.put("newCountyDistrict",userDetailsBean.getCountyDistrict()) ;

		map.put("newCountry",userDetailsBean.getCountry()) ;

		map.put("newMobileNo1",userDetailsBean.getMobileNo1()) ;

		map.put("newMobileNo2",userDetailsBean.getMobileNo2()) ;

		map.put("newWhatsappNo",userDetailsBean.getWhatsappNo()) ;

		map.put("newSecondaryEmail",userDetailsBean.getSecondaryEmail()) ;

		map.put("newEmergencyContact",userDetailsBean.getEmergencyContact()) ;

		map.put("newWebsite",userDetailsBean.getWebsite()) ;

		map.put("newFacebookLink",userDetailsBean.getFacebookLink()) ;

		map.put("newInstagramLink",userDetailsBean.getInstagramLink()) ;

		map.put("newBeneficiaryCount",userDetailsBean.getBeneficiaryCount()) ;

		map.put("newPhotoId",userDetailsBean.getPhotoId()) ;

		map.put("newPhotoPassportSize",userDetailsBean.getPhotoPassportSize()) ;

		map.put("newPlanTypeName",userDetailsBean.getPlanTypeName()) ;

		map.put("newPlanActive",userDetailsBean.getPlanActive()) ;

		map.put("newPlanSubscribeDate",userDetailsBean.getPlanSubscribeDate()) ;

		map.put("newMonthlyYearlyPlan",userDetailsBean.getMonthlyYearlyPlan()) ;

		map.put("newPlanRenueDate",userDetailsBean.getPlanRenueDate()) ;

		map.put("newReminderEnabled",userDetailsBean.getReminderEnabled()) ;

		map.put("newComments",userDetailsBean.getComments()) ;

		map.put("newUserStatus",userDetailsBean.getUserStatus()) ;

		jdbcTemplate.update(UPD_USER_DETAIL,map);
	}
	@Override
	public void deleteUserDetails(UserDetailsBean userDetailsBean){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid",userDetailsBean.getUid()) ;

		map.put("firstName",userDetailsBean.getFirstName()) ;

		map.put("middle name",userDetailsBean.getMiddle name()) ;

		map.put("lastName",userDetailsBean.getLastName()) ;

		map.put("userAddressFirstLine",userDetailsBean.getUserAddressFirstLine()) ;

		map.put("userAddressSecondLine",userDetailsBean.getUserAddressSecondLine()) ;

		map.put("cityTown",userDetailsBean.getCityTown()) ;

		map.put("state",userDetailsBean.getState()) ;

		map.put("countyDistrict",userDetailsBean.getCountyDistrict()) ;

		map.put("country",userDetailsBean.getCountry()) ;

		map.put("mobileNo1",userDetailsBean.getMobileNo1()) ;

		map.put("mobileNo2",userDetailsBean.getMobileNo2()) ;

		map.put("whatsappNo",userDetailsBean.getWhatsappNo()) ;

		map.put("secondaryEmail",userDetailsBean.getSecondaryEmail()) ;

		map.put("emergencyContact",userDetailsBean.getEmergencyContact()) ;

		map.put("website",userDetailsBean.getWebsite()) ;

		map.put("facebookLink",userDetailsBean.getFacebookLink()) ;

		map.put("instagramLink",userDetailsBean.getInstagramLink()) ;

		map.put("beneficiaryCount",userDetailsBean.getBeneficiaryCount()) ;

		map.put("photoId",userDetailsBean.getPhotoId()) ;

		map.put("photoPassportSize",userDetailsBean.getPhotoPassportSize()) ;

		map.put("planTypeName",userDetailsBean.getPlanTypeName()) ;

		map.put("planActive",userDetailsBean.getPlanActive()) ;

		map.put("planSubscribeDate",userDetailsBean.getPlanSubscribeDate()) ;

		map.put("monthlyYearlyPlan",userDetailsBean.getMonthlyYearlyPlan()) ;

		map.put("planRenueDate",userDetailsBean.getPlanRenueDate()) ;

		map.put("reminderEnabled",userDetailsBean.getReminderEnabled()) ;

		map.put("comments",userDetailsBean.getComments()) ;

		map.put("userStatus",userDetailsBean.getUserStatus()) ;

		jdbcTemplate.update(DEL_USER_DETAIL,map);
	}
	@Override
	public void insertUserDetails(UserDetailsBean userDetailsBean){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid",userDetailsBean.getUid()) ;

		map.put("firstName",userDetailsBean.getFirstName()) ;

		map.put("middle name",userDetailsBean.getMiddle name()) ;

		map.put("lastName",userDetailsBean.getLastName()) ;

		map.put("userAddressFirstLine",userDetailsBean.getUserAddressFirstLine()) ;

		map.put("userAddressSecondLine",userDetailsBean.getUserAddressSecondLine()) ;

		map.put("cityTown",userDetailsBean.getCityTown()) ;

		map.put("state",userDetailsBean.getState()) ;

		map.put("countyDistrict",userDetailsBean.getCountyDistrict()) ;

		map.put("country",userDetailsBean.getCountry()) ;

		map.put("mobileNo1",userDetailsBean.getMobileNo1()) ;

		map.put("mobileNo2",userDetailsBean.getMobileNo2()) ;

		map.put("whatsappNo",userDetailsBean.getWhatsappNo()) ;

		map.put("secondaryEmail",userDetailsBean.getSecondaryEmail()) ;

		map.put("emergencyContact",userDetailsBean.getEmergencyContact()) ;

		map.put("website",userDetailsBean.getWebsite()) ;

		map.put("facebookLink",userDetailsBean.getFacebookLink()) ;

		map.put("instagramLink",userDetailsBean.getInstagramLink()) ;

		map.put("beneficiaryCount",userDetailsBean.getBeneficiaryCount()) ;

		map.put("photoId",userDetailsBean.getPhotoId()) ;

		map.put("photoPassportSize",userDetailsBean.getPhotoPassportSize()) ;

		map.put("planTypeName",userDetailsBean.getPlanTypeName()) ;

		map.put("planActive",userDetailsBean.getPlanActive()) ;

		map.put("planSubscribeDate",userDetailsBean.getPlanSubscribeDate()) ;

		map.put("monthlyYearlyPlan",userDetailsBean.getMonthlyYearlyPlan()) ;

		map.put("planRenueDate",userDetailsBean.getPlanRenueDate()) ;

		map.put("reminderEnabled",userDetailsBean.getReminderEnabled()) ;

		map.put("comments",userDetailsBean.getComments()) ;

		map.put("userStatus",userDetailsBean.getUserStatus()) ;

		jdbcTemplate.update(INS_USER_DETAIL,map);
	}*/
}
