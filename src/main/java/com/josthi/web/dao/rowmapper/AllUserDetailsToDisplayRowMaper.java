package com.josthi.web.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserDetailsBeanForProfile;
import com.josthi.web.utils.Utils;


public class AllUserDetailsToDisplayRowMaper implements RowMapper{
	@Override
	public UserDetailsBeanForProfile mapRow(ResultSet resultSet,int arg1)throws SQLException {
		UserDetailsBeanForProfile userDetailsBeanForProfile= new UserDetailsBeanForProfile() ;
			
			userDetailsBeanForProfile.setCustomerId(resultSet.getString("CUSTOMER_ID"));
			userDetailsBeanForProfile.setUseridEmail(resultSet.getString("USERID_EMAIL"));
			userDetailsBeanForProfile.setStatus(resultSet.getString("STATUS"));
			userDetailsBeanForProfile.setRole(resultSet.getString("ROLE"));			
			userDetailsBeanForProfile.setRegistrationDateTimeStr(Utils.timestampToFormattedString(resultSet.getTimestamp("REGISTRATION_DATE_TIME"), "d MMM yyyy"));
			userDetailsBeanForProfile.setRegistrationDateTime(resultSet.getTimestamp("REGISTRATION_DATE_TIME"));
			userDetailsBeanForProfile.setVerifiedUser(resultSet.getString("VERIFIED_USER"));
			userDetailsBeanForProfile.setLoginStatus(resultSet.getString("LOGIN_STATUS"));
			userDetailsBeanForProfile.setFirstName(resultSet.getString("FIRST_NAME"));
			userDetailsBeanForProfile.setLastName(resultSet.getString("LAST_NAME"));
			userDetailsBeanForProfile.setCityTown(resultSet.getString("CITY_TOWN"));
			userDetailsBeanForProfile.setCountry(resultSet.getString("COUNTRY"));
			userDetailsBeanForProfile.setState(resultSet.getString("STATE"));
			userDetailsBeanForProfile.setZipPin(resultSet.getString("ZIP_PIN"));			
			return userDetailsBeanForProfile;
		}
}
