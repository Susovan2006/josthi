package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.josthi.web.po.UserDetailsPO ;
public class UserDetailRowMapper implements RowMapper<UserDetailsPO> {
	@Override
public UserDetailsPO mapRow(ResultSet resultSet,int arg1)throws SQLException {
		UserDetailsPO userDetailsPO= new UserDetailsPO() ;
		userDetailsPO.setUid(resultSet.getString("UID"));
		userDetailsPO.setFirstName(resultSet.getString("FIRST_NAME"));
		userDetailsPO.setMiddleName(resultSet.getString("MIDDLE_NAME"));
		userDetailsPO.setLastName(resultSet.getString("LAST_NAME"));
		userDetailsPO.setUserAddressFirstLine(resultSet.getString("USER_ADDRESS_FIRST_LINE"));
		userDetailsPO.setUserAddressSecondLine(resultSet.getString("USER_ADDRESS_SECOND_LINE"));
		userDetailsPO.setCityTown(resultSet.getString("CITY_TOWN"));
		userDetailsPO.setState(resultSet.getString("STATE"));
		userDetailsPO.setCountyDistrict(resultSet.getString("COUNTY_DISTRICT"));
		userDetailsPO.setCountry(resultSet.getString("COUNTRY"));
		userDetailsPO.setMobileNo1(resultSet.getString("MOBILE_NO1"));
		userDetailsPO.setMobileNo2(resultSet.getString("MOBILE_NO2"));
		userDetailsPO.setWhatsappNo(resultSet.getString("WHATSAPP_NO"));
		userDetailsPO.setSecondaryEmail(resultSet.getString("SECONDARY_EMAIL"));
		userDetailsPO.setEmergencyContact(resultSet.getString("EMERGENCY_CONTACT"));
		userDetailsPO.setWebsite(resultSet.getString("WEBSITE"));
		userDetailsPO.setFacebookLink(resultSet.getString("FACEBOOK_LINK"));
		userDetailsPO.setInstagramLink(resultSet.getString("INSTAGRAM_LINK"));
		userDetailsPO.setBeneficiaryCount(resultSet.getInt("BENEFICIARY_COUNT"));
		userDetailsPO.setPhotoId(resultSet.getString("PHOTO_ID"));
		//userDetailsPO.setPhotoPassportSize(resultSet.get[B("PHOTO_PASSPORT_SIZE"));
		userDetailsPO.setPlanTypeName(resultSet.getString("PLAN_TYPE_NAME"));
		userDetailsPO.setPlanActive(resultSet.getString("PLAN_ACTIVE"));
		userDetailsPO.setPlanSubscribeDate(resultSet.getTimestamp("PLAN_SUBSCRIBE_DATE"));
		userDetailsPO.setMonthlyYearlyPlan(resultSet.getString("MONTHLY_YEARLY_PLAN"));
		userDetailsPO.setPlanRenueDate(resultSet.getTimestamp("PLAN_RENUE_DATE"));
		userDetailsPO.setReminderEnabled(resultSet.getString("REMINDER_ENABLED"));
		userDetailsPO.setComments(resultSet.getString("COMMENTS"));
		userDetailsPO.setUserStatus(resultSet.getString("USER_STATUS"));
		return userDetailsPO;
		}
	}
