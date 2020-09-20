package com.josthi.web.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josthi.web.constants.EmailConstant;
import com.josthi.web.po.EmailDbBean;

public class Utils {
	
	
	/**
	 * 
	 * @param type
	 * @param nextCount
	 * @return
	 */
	public static String getNextCustomerID(String type,int nextCount) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("YYMMdd");
		StringBuffer custId = new StringBuffer();
		if(type.equalsIgnoreCase("Admin")) {
			custId.append("AD");
		}else if(type.equalsIgnoreCase("Agent")) {
			custId.append("AG");
		}else if(type.equalsIgnoreCase("SubAgent")) {
			custId.append("SA");
		}else if(type.equalsIgnoreCase("RegUser")) {
			custId.append("RU");
		}else if(type.equalsIgnoreCase("Beneficiary")) {
			custId.append("BE");
		}	
		custId.append(formatter.format(new Date()));		
		custId.append(String.format("%03d", nextCount));
		
		return custId.toString();
		
	}

	
	/**
	 * 
	 * @param emailTo
	 * @param jsonValue
	 * @return
	 */
	public static EmailDbBean getEmailBeanForPasswordRecovery(String emailTo, String jsonValue) {
		
		EmailDbBean emailDbBean =  new EmailDbBean();
		emailDbBean.setSentTo(emailTo);
		emailDbBean.setSentFrom(EmailConstant.EMAIL_FROM_FOR_PASSWORD_RECOVERY);
		emailDbBean.setSubject(EmailConstant.SUBJECT_FROM_FOR_PASSWORD_RECOVERY);
		emailDbBean.setJsonString(jsonValue);
		emailDbBean.setEmailTemplate(EmailConstant.TEMPLATE_FROM_FOR_PASSWORD_RECOVERY);
		emailDbBean.setEmailStatus(EmailConstant.LOAD_STATUS_FROM_FOR_PASSWORD_RECOVERY);
		emailDbBean.setEmailQueuedAt(new Timestamp(System.currentTimeMillis()));
		emailDbBean.setEmailDelivaryStatus(EmailConstant.LOADED_EMAIL_DELIVARY_STATUS_FROM_FOR_PASSWORD_RECOVERY);		
		return emailDbBean;
	}
	
	
	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String mapToString(Map<String, String> map ) throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(map);
		}catch (Exception ex) {
			throw ex;
		}
	}

}
