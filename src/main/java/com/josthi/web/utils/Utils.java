package com.josthi.web.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.constants.Constant;
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
		if(type.equalsIgnoreCase(Constant.USER_TYPE_ADMIN)) {
			custId.append("AD");
		}else if(type.equalsIgnoreCase(Constant.USER_TYPE_AGENT)) {
			custId.append("AG");
		}else if(type.equalsIgnoreCase(Constant.USER_TYPE_SUB_AGENT)) {
			custId.append("SA");
		}else if(type.equalsIgnoreCase(Constant.USER_TYPE_REG_USER)) {
			custId.append("RU");
		}else if(type.equalsIgnoreCase(Constant.USER_TYPE_BENEFICIARY)) {
			custId.append("BE");
		}	
		custId.append(formatter.format(new Date()));		
		custId.append(String.format("%04d", nextCount));
		
		return custId.toString();
		
	}
	
	
	
public static String getNextTicketID(String type,int nextCount) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("YYMMdd");
		StringBuffer txtId = new StringBuffer();
		if(type.equalsIgnoreCase(Constant.REQUEST_TICKET)) {
			txtId.append("TKT");
		}else if(type.equalsIgnoreCase(Constant.REQUEST_INCIDENT)) {
			txtId.append("INC");
		}
		txtId.append(formatter.format(new Date()));
		txtId.append(String.format("%04d", nextCount));
				
		
		
		return txtId.toString();
		
	}

public static String getNextPlanInvoiceID(String type, String plan,int nextCount) {
	
	SimpleDateFormat formatter = new SimpleDateFormat("YYMMdd");
	StringBuffer planId = new StringBuffer();
	if(type.equalsIgnoreCase(Constant.REQUEST_PLAN)) {
		planId.append("P");
	}
	
	if(plan.equalsIgnoreCase(Constant.PLAN_BASIC)) {
		planId.append("BAS");
	}else if(plan.equalsIgnoreCase(Constant.PLAN_SILVER)) {
		planId.append("SLV");
	}else if(plan.equalsIgnoreCase(Constant.PLAN_GOLD)) {
		planId.append("GLD");
	}
	planId.append(formatter.format(new Date()));
	planId.append(String.format("%04d", nextCount));

	return planId.toString();
	
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
		emailDbBean.setEmailTemplate(EmailConstant.TEMPLATE_FORM_FOR_PASSWORD_RECOVERY);
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
		emailDbBean.setEmailTemplate(EmailConstant.TEMPLATE_FORM_FOR_ACCOUNT_UNLOCK);
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
		emailDbBean.setEmailTemplate(EmailConstant.TEMPLATE_FORM_FOR_WELCOME);
		emailDbBean.setEmailStatus(EmailConstant.LOAD_STATUS);
		emailDbBean.setEmailQueuedAt(new Timestamp(System.currentTimeMillis()));
		emailDbBean.setEmailDelivaryStatus(EmailConstant.LOADED_EMAIL_DELIVARY_STATUS);		
		return emailDbBean;
	}
	
	
	public static EmailDbBean getEmailBeanForOTP(String emailTo, String jsonValue) {
		
		EmailDbBean emailDbBean =  new EmailDbBean();
		emailDbBean.setSentTo(emailTo);
		emailDbBean.setSentFrom(EmailConstant.EMAIL_FROM_FOR_OTP);
		emailDbBean.setSubject(EmailConstant.SUBJECT_FROM_FOR_OTP);
		emailDbBean.setJsonString(jsonValue);
		emailDbBean.setEmailTemplate(EmailConstant.TEMPLATE_FORM_FOR_OTP);
		emailDbBean.setEmailStatus(EmailConstant.LOAD_STATUS);
		emailDbBean.setEmailQueuedAt(new Timestamp(System.currentTimeMillis()));
		emailDbBean.setEmailDelivaryStatus(EmailConstant.LOADED_EMAIL_DELIVARY_STATUS);		
		return emailDbBean;
	}
	
	
	public static EmailDbBean getEmailBeanForServiceTicket(String emailTo, String ticketNumber, String jsonValue) {
		
		EmailDbBean emailDbBean =  new EmailDbBean();
		emailDbBean.setSentTo(emailTo);
		emailDbBean.setSentFrom(EmailConstant.EMAIL_FROM_FOR_SERVICE);
		emailDbBean.setSubject(EmailConstant.SUBJECT_FROM_FOR_SERVICE + " : "+ticketNumber);
		emailDbBean.setJsonString(jsonValue);
		emailDbBean.setEmailTemplate(EmailConstant.TEMPLATE_FORM_FOR_SERVICE_REQUEST_UPDATE);
		emailDbBean.setEmailStatus(EmailConstant.LOAD_STATUS);
		emailDbBean.setEmailQueuedAt(new Timestamp(System.currentTimeMillis()));
		emailDbBean.setEmailDelivaryStatus(EmailConstant.LOADED_EMAIL_DELIVARY_STATUS);		
		return emailDbBean;
	}
	
	public static EmailDbBean getEmailBeanForAgentAssignment(String emailTo, String jsonValue) {
		
		EmailDbBean emailDbBean =  new EmailDbBean();
		emailDbBean.setSentTo(emailTo);
		emailDbBean.setSentFrom(EmailConstant.EMAIL_FROM_FOR_AGENT_UPDATE);
		emailDbBean.setSubject(EmailConstant.SUBJECT_FROM_FOR_AGENT_UPDATE);
		emailDbBean.setJsonString(jsonValue);
		emailDbBean.setEmailTemplate(EmailConstant.TEMPLATE_FORM_FOR_AGENT_ASSIGNMENT_UPDATE);
		emailDbBean.setEmailStatus(EmailConstant.LOAD_STATUS);
		emailDbBean.setEmailQueuedAt(new Timestamp(System.currentTimeMillis()));
		emailDbBean.setEmailDelivaryStatus(EmailConstant.LOADED_EMAIL_DELIVARY_STATUS);		
		return emailDbBean;
	}
	
	
    public static EmailDbBean getEmailBeanForPlanInvoive(String emailTo, String invoiveNo, String jsonValue) {
		
		EmailDbBean emailDbBean =  new EmailDbBean();
		emailDbBean.setSentTo(emailTo);
		emailDbBean.setSentFrom(EmailConstant.EMAIL_FROM_FOR_PLAN);
		emailDbBean.setSubject(EmailConstant.SUBJECT_FROM_FOR_PLAN_INVOIVE + " : "+invoiveNo);
		emailDbBean.setJsonString(jsonValue);
		emailDbBean.setEmailTemplate(EmailConstant.TEMPLATE_FORM_FOR_PURCHASE_INVOICE);
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
	
	
	public static File getDefaultProfileImages() 
			  throws FileNotFoundException {
			    return ResourceUtils.getFile(
			      "classpath:static/images/default_user.png");
			}


	public static String getBeneficiaryId(String custId) {
		String beneficiaryId = "";
		if(custId!=null) {
			beneficiaryId =  custId.replaceFirst("RU", "BE");
		}
		return beneficiaryId;
	}


	public static Timestamp getDob(String dateOfBirth) throws ParseException {
		if(dateOfBirth!=null && dateOfBirth.trim().length() == 10) {
			DateFormat df = new SimpleDateFormat(Constant.DATE_FORMAT_FOR_DOB);
			Date parseDate = df.parse(dateOfBirth);
			java.sql.Timestamp dob = new java.sql.Timestamp(parseDate.getTime());
			return dob;
		}else {
			return null;
		}
		
	}
	
	public static String timestampToFormattedString(Timestamp ts) {
		Date date = new Date();
		date.setTime(ts.getTime());
		String formattedDate = new SimpleDateFormat(Constant.DATE_FORMAT_FOR_TICKET_HISTORY).format(date);
		return formattedDate;
		
	}
	
	public static Timestamp getRequestFulFillDate(String fulFillDate) throws ParseException {
		if(fulFillDate!=null && fulFillDate.trim().length() == 10) {
			DateFormat df = new SimpleDateFormat(Constant.DATE_FORMAT_FOR_TICKET_COMPLETION_DATE);
			Date parseDate = df.parse(fulFillDate);
			java.sql.Timestamp fulFillSqlDate = new java.sql.Timestamp(parseDate.getTime());
			return fulFillSqlDate;
		}else {
			return null;
		}
		
	}
	
	//public static Timestamp getDateAdditionValue(int daysToAdd) throws ParseException {		
		//return new Timestamp(System.currentTimeMillis() + daysToAdd * 86400000);
	//}
	
	public static Timestamp getDateAdditionValue(int daysToAdd) throws ParseException {		
		LocalDateTime newDate = LocalDateTime.now().plusDays(daysToAdd);
		Timestamp newTimestamp = Timestamp.valueOf(newDate);
		return newTimestamp;
	}


	public static String calculateAge(String dateOfBirth) throws ParseException {
		if(dateOfBirth!=null && dateOfBirth.length() == 10) {
			DateFormat df = new SimpleDateFormat(Constant.DATE_FORMAT_FOR_DOB);
			Date parseDate = df.parse(dateOfBirth);
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(parseDate);
			
			Calendar cal2 = Calendar.getInstance();
			int i = 0;
			while (cal1.before(cal2)) {
				cal1.add(Calendar.YEAR, 1);
				i+=1;
			}
			return i + "years";
		}else {
			return "";
		}
	}
	
	
	
	public static long compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime)
	{
	  long milliseconds1 = oldTime.getTime();
	  long milliseconds2 = currentTime.getTime();

	  long diff = milliseconds2 - milliseconds1;
	  long diffSeconds = diff / 1000;
	  long diffMinutes = diff / (60 * 1000);
	  long diffHours = diff / (60 * 60 * 1000);
	  long diffDays = diff / (24 * 60 * 60 * 1000);

	    return diffMinutes;
	}
	
	
	public static String timestampToFormattedString(Timestamp ts , String format) {
		Date date = new Date();
		date.setTime(ts.getTime());
		String formattedDate = new SimpleDateFormat(format).format(date);
		return formattedDate;
	}

	
	
	
	
	
	public static boolean passwordLength(String password) {
	    /* Declare a boolean variable to hold the result of the method */
	    boolean correct = true;

	    /* Declare an int variable to hold the count of each digit */
	    int digit = 0; 

	    if (password.length() < 8) {
	        /* The password is less than 8 characters, return false */
	        return false;
	    }

	    /* Declare a char variable to hold each element of the String */
	    char element;

	    /* Check if the password has 1 or more digits */
	    for(int index = 0; index < password.length(); index++ ){

	        /* Check each char in the String */
	        element = password.charAt( index );

	        /* Check if it is a digit or not */
	        if( Character.isDigit(element) ){
	            /* It is a digit, so increment digit */
	            digit++;
	        } // End if block

	    } // End for loop 

	    /* Now check for the count of digits in the password */
	    if( digit < 1 ){
	        /* There are fewer than 2 digits in the password, return false */
	        return false;
	    }

	    /* Use a regular expression (regex) to check for only letters and numbers */
	    /* The regex will check for upper and lower case letters and digits */
	    /* || !password.matches("[!@#$*+-]+ */
	    if( !password.matches("[a-zA-Z0-9]+") ){
	        /* A non-alphanumeric character was found, return false */
	        return false;
	    }

	    /* All checks at this point have passed, the password is valid */
	    return correct;

	}
	
	public static boolean PasswordValidation(String password) 
	{

	    if(password.length()>=8)
	    {
	        Pattern letter = Pattern.compile("[a-zA-z]");
	        Pattern digit = Pattern.compile("[0-9]");
	        Pattern special = Pattern.compile ("[!@#$%*+-]");
	        //Pattern eight = Pattern.compile (".{8}");


	           Matcher hasLetter = letter.matcher(password);
	           Matcher hasDigit = digit.matcher(password);
	           Matcher hasSpecial = special.matcher(password);

	           return hasLetter.find() && hasDigit.find() && hasSpecial.find();

	    }
	    else
	        return false;

	}
	
	
	public static String formattedCurrency(String value) {		
		Float returnValue=Float.parseFloat(value);
		DecimalFormat df = new DecimalFormat("#,###.00");
		df.setMaximumFractionDigits(2);
		return df.format(returnValue);
		
	}


	//1BEN, 2BEN, 3BEN --> 1, 2, 3
	public static int getBeneficiaryCountFromDropdownVal(String beneficiaryCount) {
		if(StringUtils.isEmpty(beneficiaryCount)) {
			return 0;
		}
		
		int count = Integer.parseInt(StringUtils.substring(beneficiaryCount, 0, 1));
		return count;
		
	}



	public static double getDoubleValue(String priceStr) {
		double f = Double.parseDouble(priceStr.replace(",",""));
		return f;
	}



	public static Object getInvoiceIdFromPlan(String planId) {

		if(!(StringUtils.isEmpty(planId)) && planId.length() > 4) {
			planId = planId.substring(4);
		}
		return planId;
	}
	
	public static Object getInvoiceIdFromTicket(String ticketId) {

		if(!(StringUtils.isEmpty(ticketId)) && ticketId.length() > 3) {
			ticketId = ticketId.substring(3);
		}
		return ticketId;
	}

	

}
