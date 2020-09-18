package com.josthi.web.dao.rowmapper ;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.josthi.web.po.EmailDbBean;
public class EmailQueueRowMapper implements RowMapper<EmailDbBean> {
	@Override
public EmailDbBean mapRow(ResultSet resultSet,int arg1)throws SQLException {
		EmailDbBean emailDbBean= new EmailDbBean() ;
		emailDbBean.setEmailUid(resultSet.getInt("EMAIL_UID"));
		emailDbBean.setSentTo(resultSet.getString("SENT_TO"));
		emailDbBean.setSentFrom(resultSet.getString("SENT_FROM"));
		emailDbBean.setSubject(resultSet.getString("SUBJECT"));
		emailDbBean.setJsonString(resultSet.getString("JSON_STRING"));
		emailDbBean.setEmailTemplate(resultSet.getString("EMAIL_TEMPLATE"));
		emailDbBean.setEmailStatus(resultSet.getString("EMAIL_STATUS"));
		emailDbBean.setEmailQueuedAt(resultSet.getTimestamp("EMAIL_QUEUED_AT"));
		emailDbBean.setEmailSentAt(resultSet.getTimestamp("EMAIL_SENT_AT"));
		emailDbBean.setEmailDelivaryStatus(resultSet.getString("EMAIL_DELIVARY_STATUS"));
		return emailDbBean;
		}
	}
