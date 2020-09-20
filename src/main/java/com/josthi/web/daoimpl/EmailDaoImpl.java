package com.josthi.web.daoimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.josthi.web.dao.EmailDao;
import com.josthi.web.dao.rowmapper.EmailQueueRowMapper;
import com.josthi.web.po.EmailDbBean;


//@Repository("emailDao")
public class EmailDaoImpl implements EmailDao{

	private static final Logger logger = LoggerFactory.getLogger(EmailDaoImpl.class);

	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	
	
	public static final String INSERT_EMAIL_DETAILS_FOR_PASSWORD_RECOVERY = "Insert into email_queue (SENT_TO,SENT_FROM,SUBJECT,JSON_STRING,"
			+ "EMAIL_TEMPLATE,EMAIL_STATUS,EMAIL_QUEUED_AT,EMAIL_DELIVARY_STATUS) "
			+ "values (?,?,?,?,?,?,?,?) ";

	
	public boolean queuePasswordRecoveryEmail(EmailDbBean emailDbBean) {
		try {
				
			System.out.println("jdbcTemplate:"+jdbcTemplate);
			int result = jdbcTemplate.update(INSERT_EMAIL_DETAILS_FOR_PASSWORD_RECOVERY, new Object[]{emailDbBean.getSentTo(),
																									  emailDbBean.getSentFrom(), 
																									  emailDbBean.getSubject(),
																									  emailDbBean.getJsonString(),
																									  emailDbBean.getEmailTemplate(),
																									  emailDbBean.getEmailStatus(),
																									  emailDbBean.getEmailQueuedAt(),
																									  emailDbBean.getEmailDelivaryStatus()});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		}
		
	}
	
	
	
	public static final String SELECT_EMAIL_LIST_TO_TRIGGER = "SELECT EMAIL_UID,SENT_TO,SENT_FROM,SUBJECT,JSON_STRING,EMAIL_TEMPLATE,EMAIL_STATUS,EMAIL_QUEUED_AT,EMAIL_SENT_AT,EMAIL_DELIVARY_STATUS FROM email_queue where EMAIL_STATUS IN ('RETRY','LOADED')";
	public List<EmailDbBean> getEmailsListToTrigger() {
		List<EmailDbBean> emailDbBeanList = (List<EmailDbBean>) jdbcTemplate.query(SELECT_EMAIL_LIST_TO_TRIGGER, new EmailQueueRowMapper());	
		return emailDbBeanList;
	}
	
	
	
	private static final String UPDATE_EMAIL_STATUS_ON_EMAIL_TRIGGER = "update email_queue set EMAIL_STATUS=?, EMAIL_SENT_AT = ? , EMAIL_DELIVARY_STATUS = ? where EMAIL_UID = ? ";
	@Override
	public boolean updateEmailSentStatus(EmailDbBean emailBean) {
		try {
			
			System.out.println("emailBean.getEmailUid():"+emailBean.getEmailUid());
			int result = jdbcTemplate.update(UPDATE_EMAIL_STATUS_ON_EMAIL_TRIGGER, new Object[]{emailBean.getEmailStatus(),
															   									emailBean.getEmailSentAt(), 
															   									emailBean.getEmailDelivaryStatus(),
															   									emailBean.getEmailUid()});	
			return (result > 0 ? true : false);
		}catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			return false;
		}
	}
	

}
