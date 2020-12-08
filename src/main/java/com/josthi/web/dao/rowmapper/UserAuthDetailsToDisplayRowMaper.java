package com.josthi.web.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.utils.Utils;


//CUSTOMER_ID, USERID_EMAIL, WORDAPP, STATUS, ROLE
public class UserAuthDetailsToDisplayRowMaper implements RowMapper{
	@Override
	public UserAuthBo mapRow(ResultSet resultSet,int arg1)throws SQLException {
		UserAuthBo userAuth= new UserAuthBo() ;
			
			userAuth.setUseridEmail(resultSet.getString("USERID_EMAIL"));
			userAuth.setStatus(resultSet.getString("STATUS"));
			userAuth.setRole(resultSet.getString("ROLE"));			
			userAuth.setRegistrationDateTimeStr(Utils.timestampToFormattedString(resultSet.getTimestamp("REGISTRATION_DATE_TIME"), "d MMM yyyy"));
			userAuth.setRegistrationDateTime(resultSet.getTimestamp("REGISTRATION_DATE_TIME"));
			userAuth.setVerifiedUser(resultSet.getString("VERIFIED_USER"));
			return userAuth;
		}
}
