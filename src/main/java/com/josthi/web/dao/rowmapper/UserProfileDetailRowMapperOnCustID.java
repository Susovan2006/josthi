package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.po.UserDetailsPO ;
import com.josthi.web.utils.Utils;
public class UserProfileDetailRowMapperOnCustID implements RowMapper<UserDetailsBean> {
	@Override
public UserDetailsBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		UserDetailsBean userDetailsBean= new UserDetailsBean() ;
		userDetailsBean.setUid(resultSet.getString("UID"));
		userDetailsBean.setFirstName(Utils.convertToCamelCase(resultSet.getString("FIRST_NAME")));
		userDetailsBean.setMiddleName(resultSet.getString("MIDDLE_NAME"));
		userDetailsBean.setLastName(Utils.convertToCamelCase(resultSet.getString("LAST_NAME")));
		userDetailsBean.setGender(resultSet.getString("GENDER"));
		
		userDetailsBean.setUserAddressFirstLine(resultSet.getString("USER_ADDRESS_FIRST_LINE"));
		userDetailsBean.setUserAddressSecondLine(resultSet.getString("USER_ADDRESS_SECOND_LINE"));
		userDetailsBean.setCityTown(Utils.convertToCamelCase(resultSet.getString("CITY_TOWN")));
		userDetailsBean.setState(resultSet.getString("STATE"));
		userDetailsBean.setCountyDistrict(resultSet.getString("COUNTY_DISTRICT"));
		userDetailsBean.setCountry(Utils.convertToCamelCase(resultSet.getString("COUNTRY")));
		userDetailsBean.setZipPin(resultSet.getString("ZIP_PIN"));
		
		userDetailsBean.setMobileNo1(resultSet.getString("MOBILE_NO1"));
		userDetailsBean.setMobileNo2(resultSet.getString("MOBILE_NO2"));
		userDetailsBean.setWhatsappNo(resultSet.getString("WHATSAPP_NO"));
		userDetailsBean.setLandLineNo(resultSet.getString("LAND_LINE_NO"));
		userDetailsBean.setFaxNo(resultSet.getString("FAX_NO"));
		userDetailsBean.setOfficePhNo(resultSet.getString("OFFICE_PH_NO"));
		
		userDetailsBean.setSecondaryEmail(resultSet.getString("SECONDARY_EMAIL"));
		userDetailsBean.setWebsite(resultSet.getString("WEBSITE"));
		userDetailsBean.setFacebookLink(resultSet.getString("FACEBOOK_LINK"));

		userDetailsBean.setUserStatus(resultSet.getString("USER_STATUS"));
		return userDetailsBean;
		}
	}
