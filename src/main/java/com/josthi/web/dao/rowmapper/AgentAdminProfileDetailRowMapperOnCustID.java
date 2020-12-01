package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.po.UserDetailsPO ;
public class AgentAdminProfileDetailRowMapperOnCustID implements RowMapper<UserDetailsBean> {
	@Override
public UserDetailsBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		UserDetailsBean userDetailsBean= new UserDetailsBean() ;
		userDetailsBean.setUid(resultSet.getString("UID"));
		userDetailsBean.setFirstName(resultSet.getString("FIRST_NAME"));
		//userDetailsBean.setMiddleName(resultSet.getString("MIDDLE_NAME"));
		userDetailsBean.setLastName(resultSet.getString("LAST_NAME"));
		//userDetailsBean.setGender(resultSet.getString("GENDER"));
		
		userDetailsBean.setUserAddressFirstLine(resultSet.getString("USER_ADDRESS_FIRST_LINE"));
		userDetailsBean.setUserAddressSecondLine(resultSet.getString("USER_ADDRESS_SECOND_LINE"));
		userDetailsBean.setUserAdditionalAddressLine(resultSet.getString("ADDITIONAL_ADDRESS_LINE"));
		userDetailsBean.setPoliceStation(resultSet.getString("POLICE_STATION"));
		userDetailsBean.setNearestLandMark(resultSet.getString("NEAREST_LAND_MARK"));
		userDetailsBean.setCityTown(resultSet.getString("CITY_TOWN"));
		userDetailsBean.setCoverageArea(resultSet.getString("COVERAGE_AREA"));
		//userDetailsBean.setState(resultSet.getString("STATE"));
		userDetailsBean.setCountyDistrict(resultSet.getString("COUNTY_DISTRICT"));
		//userDetailsBean.setCountry(resultSet.getString("COUNTRY"));
		userDetailsBean.setZipPin(resultSet.getString("ZIP_PIN"));
		
		userDetailsBean.setMobileNo1(resultSet.getString("MOBILE_NO1"));
		userDetailsBean.setMobileNo2(resultSet.getString("MOBILE_NO2"));
		userDetailsBean.setWhatsappNo(resultSet.getString("WHATSAPP_NO"));
		userDetailsBean.setLandLineNo(resultSet.getString("LAND_LINE_NO"));
		
		
		userDetailsBean.setSecondaryEmail(resultSet.getString("SECONDARY_EMAIL"));
		userDetailsBean.setWebsite(resultSet.getString("WEBSITE"));
		userDetailsBean.setFacebookLink(resultSet.getString("FACEBOOK_LINK"));

		userDetailsBean.setUserStatus(resultSet.getString("USER_STATUS"));
		
		userDetailsBean.setAgencyName(resultSet.getString("AGENCY_NAME"));
		userDetailsBean.setAgencyDescription(resultSet.getString("AGENCY_DESCRIPTION"));
		return userDetailsBean;
		}
	}
