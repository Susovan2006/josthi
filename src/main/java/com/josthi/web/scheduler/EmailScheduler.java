package com.josthi.web.scheduler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.josthi.web.constants.EmailConstant;
import com.josthi.web.dao.EmailDao;
import com.josthi.web.daoimpl.EmailDaoImpl;
import com.josthi.web.mail.EmailController;
import com.josthi.web.po.EmailDbBean;



//https://memorynotfound.com/spring-mail-sending-email-inline-attachment-example/
@Controller
public class EmailScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailScheduler.class);
	
	@Autowired
	EmailDao emailDao;
	
	@Autowired
	EmailController emailController;

	//This flag is set to true when the user request for password reset.
	//Once the email is sent, it will be disabled.
	public static boolean ENAMBLE_TIMER = true;
	
	public static final String CRON_EXPRESSION_EMAIL = "0/45 * * * * ?";
	
	@Scheduled(cron=CRON_EXPRESSION_EMAIL) 
	public void sendEmailFromQueue() throws Exception {
		logger.info("----- Timer Called -------");
		if(ENAMBLE_TIMER) {
			List <EmailDbBean> listOfEmails = new ArrayList<EmailDbBean>();
			listOfEmails = emailDao.getEmailsListToTrigger();		
			try {
				for(EmailDbBean emailBean : listOfEmails) {
					try {
						ObjectMapper mapper = new ObjectMapper();
						@SuppressWarnings("unchecked")
						Map<String, String> emailDetailsModel = mapper.readValue(emailBean.getJsonString(), Map.class);
						//System.out.println("emailDetailsModel"+emailDetailsModel);
						emailController.sendEmailForPasswordRecovery(emailBean, emailDetailsModel);
						
						emailBean.setEmailStatus(EmailConstant.SUCCESS_STATUS);
						emailBean.setEmailDelivaryStatus(EmailConstant.DELEVERED_EMAIL_DELIVARY_STATUS);
						emailBean.setEmailSentAt(new Timestamp(System.currentTimeMillis()));
						boolean emailSentStatus = emailDao.updateEmailSentStatus(emailBean);
						if(emailSentStatus) {
							ENAMBLE_TIMER = false;
							logger.info("Timer on Hold");
						}
						logger.info("Email Delivered Successfully");
					}catch(Exception ex) {
						logger.error(ex.getMessage(), ex);
						emailBean.setEmailStatus(EmailConstant.RETRY_STATUS);
						emailBean.setEmailDelivaryStatus(EmailConstant.FAILED_EMAIL_DELIVARY_STATUS);
						emailBean.setEmailSentAt(new Timestamp(System.currentTimeMillis()));
						boolean emailSentStatus = emailDao.updateEmailSentStatus(emailBean);
						if(emailSentStatus) {
							ENAMBLE_TIMER = true;
							logger.info("Timer still enabled for next retry");
						}
					}
				}//end for
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}else {
			System.out.println("Timer Skipped");
		}
	}

}
