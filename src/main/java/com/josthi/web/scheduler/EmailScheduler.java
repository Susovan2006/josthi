package com.josthi.web.scheduler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

@Controller
public class EmailScheduler {
	
	
	@Autowired
	EmailDao emailDao;
	
	@Autowired
	EmailController emailController;

	
	@Scheduled(cron="0/10 * * * * ?") 
	public void sendEmailFromQueue() throws Exception {
		
		List <EmailDbBean> listOfEmails = new ArrayList<EmailDbBean>();
		listOfEmails = emailDao.getEmailsListToTrigger();
		//System.out.println(listOfEmails);
		
		try {
			for(EmailDbBean emailBean : listOfEmails) {
				try {
					ObjectMapper mapper = new ObjectMapper();
					@SuppressWarnings("unchecked")
					Map<String, String> emailDetailsModel = mapper.readValue(emailBean.getJsonString(), Map.class);
					//System.out.println("emailDetailsModel"+emailDetailsModel);
					emailController.sendEmailForPasswordRecovery(emailBean, emailDetailsModel);
					
					emailBean.setEmailStatus(EmailConstant.SUCCESS_STATUS_FROM_FOR_PASSWORD_RECOVERY);
					emailBean.setEmailDelivaryStatus(EmailConstant.DELEVERED_EMAIL_DELIVARY_STATUS_FROM_FOR_PASSWORD_RECOVERY);
					emailBean.setEmailSentAt(new Timestamp(System.currentTimeMillis()));
					boolean emailSentStatus = emailDao.updateEmailSentStatus(emailBean);
					
					System.out.println("Email Delivered Successfully");
				}catch(Exception ex) {
					ex.printStackTrace();
					emailBean.setEmailStatus(EmailConstant.RETRY_STATUS_FROM_FOR_PASSWORD_RECOVERY);
					emailBean.setEmailDelivaryStatus(EmailConstant.FAILED_EMAIL_DELIVARY_STATUS_FROM_FOR_PASSWORD_RECOVERY);
					emailBean.setEmailSentAt(new Timestamp(System.currentTimeMillis()));
					boolean emailSentStatus = emailDao.updateEmailSentStatus(emailBean);
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
