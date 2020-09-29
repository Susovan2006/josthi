package com.josthi.web.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		emailDbBean.setEmailStatus(EmailConstant.LOAD_STATUS);
		emailDbBean.setEmailQueuedAt(new Timestamp(System.currentTimeMillis()));
		emailDbBean.setEmailDelivaryStatus(EmailConstant.LOADED_EMAIL_DELIVARY_STATUS);		
		return emailDbBean;
	}
	
	
	
    public static EmailDbBean getEmailBeanForAccountLock(String emailTo, String jsonValue) {
		
		EmailDbBean emailDbBean =  new EmailDbBean();
		emailDbBean.setSentTo(emailTo);
		emailDbBean.setSentFrom(EmailConstant.EMAIL_FROM_FOR_ACCOUNT_UN_LOCK);
		emailDbBean.setSubject(EmailConstant.SUBJECT_FROM_FOR_ACCOUNT_UN_LOCK);
		emailDbBean.setJsonString(jsonValue);
		emailDbBean.setEmailTemplate(EmailConstant.TEMPLATE_FROM_FOR_ACCOUNT_UNLOCK);
		emailDbBean.setEmailStatus(EmailConstant.LOAD_STATUS);
		emailDbBean.setEmailQueuedAt(new Timestamp(System.currentTimeMillis()));
		emailDbBean.setEmailDelivaryStatus(EmailConstant.LOADED_EMAIL_DELIVARY_STATUS);		
		return emailDbBean;
	}
	
	public static EmailDbBean getEmailBeanForWelcome(String emailTo, String jsonValue) {
		
		EmailDbBean emailDbBean =  new EmailDbBean();
		emailDbBean.setSentTo(emailTo);
		emailDbBean.setSentFrom(EmailConstant.EMAIL_FROM_FOR_WELCOME);
		emailDbBean.setSubject(EmailConstant.SUBJECT_FROM_FOR_WELCOME);
		emailDbBean.setJsonString(jsonValue);
		emailDbBean.setEmailTemplate(EmailConstant.TEMPLATE_FROM_FOR_WELCOME);
		emailDbBean.setEmailStatus(EmailConstant.LOAD_STATUS);
		emailDbBean.setEmailQueuedAt(new Timestamp(System.currentTimeMillis()));
		emailDbBean.setEmailDelivaryStatus(EmailConstant.LOADED_EMAIL_DELIVARY_STATUS);		
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
	
	public static String toCamelCase(String val) {
		if(!StringUtils.isEmpty(val)) {
			return StringUtils.capitalize(val.toLowerCase());
		}else {
			return val;
		}
	}
	
	public static String convertToCamelCase(final String init) {
	    if (init == null)
	        return null;

	    final StringBuilder ret = new StringBuilder(init.length());

	    for (final String word : init.split(" ")) {
	        if (!word.isEmpty()) {
	            ret.append(Character.toUpperCase(word.charAt(0)));
	            ret.append(word.substring(1).toLowerCase());
	        }
	        if (!(ret.length() == init.length()))
	            ret.append(" ");
	    }

	    return ret.toString();
	}
	
	
	// Get Query time.
	public static String getQueryTime(String message, long startTime) {
		double queryTime = (System.currentTimeMillis()-startTime);
		return "Execution time for :"+message+ " is "+ queryTime + "mili sec. i.e.  " + queryTime/1000+" sec";
	}


	public static String generateAccountUnlockUrl(String emailID) throws Exception {	
		//return "https://josthi.com/unlock/"+Security.encrypt(emailID);
		return "https://josthi.com";
	}
	
	
	public static String readableFileSize(long size) {
	    if(size <= 0) return "0";
	    final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}


	public static String setDownLoadPath(String downloadPath) {
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(downloadPath).toUriString();
		return "<a class='nav-link' href='"+fileDownloadUri+"'><i class='fas fa-cloud-download nav-icon'></i></a>";
		//return null;
	}

}
