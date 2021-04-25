package com.josthi.web.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserPreferencesBean;


//CUSTOMER_ID, USERID_EMAIL, WORDAPP, STATUS, ROLE
public class UserPreferenceRowMaper implements RowMapper{
	@Override
	public UserPreferencesBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		UserPreferencesBean userPreferencesBean= new UserPreferencesBean() ;
		userPreferencesBean.setUserId(resultSet.getString("USER_ID"));
		userPreferencesBean.setLanguage(resultSet.getString("PREFERED_LANGUAGE"));
		userPreferencesBean.setTimeZone(resultSet.getString("TIME_ZONE"));
		userPreferencesBean.setPlanRenewalAlert(resultSet.getBoolean("PLAN_RENEWAL_REMINDER"));
		userPreferencesBean.setWhatsappNotificationsAlert(resultSet.getBoolean("WHATSAPP_NOTIFICATION"));
		userPreferencesBean.setPromotionalOfferAlert(resultSet.getBoolean("EMAIL_NOTIFICATION_FOR_AD"));
			return userPreferencesBean;
		}
}
