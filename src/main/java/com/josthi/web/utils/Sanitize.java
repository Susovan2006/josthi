package com.josthi.web.utils;

import java.util.regex.Pattern;

import javax.xml.transform.TransformerFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @author Susovan Gumtya
 * 
 * This class is mainly used to resolve security issues in Josthi.com
 *
 */
public class Sanitize {
	
	private static final String ACCESS_EXTERNAL_DID = "http://javax.xml.XMLConstants/property/accessExternalDTD";
	
	
	
	
	//========================================= PATH MANIPULATION ==========================================
	/**
	 * This will be applicable for LDAPInjection and Path Manipulation.
	 * It will change the \n or \\n to /n
	 * same for \t or \\t to /t. and others.
	 * @param var
	 * @return
	 */
	public static String pathManipulation(String var) {
		String str = null;
		if(var !=null) {
			str = FilenameUtils.normalize(replaceBackSlash(var));
		}
		
		return str;
		
	}
	
	private static String replaceBackSlash(String var) {
		StringBuilder manipulatedString =  new StringBuilder(var.length());
		   for(int i = 0 ; i < var.length(); i++) {
			   char ch = var.charAt(i);
			   if(ch != '\\') {
				   manipulatedString.append(ch);
			   }else {
				   manipulatedString.append('/');
			   }
		   }
		   
		   return manipulatedString.toString();
	}
	
	
	//========================================= LOG FORGING ==========================================
	
	public static String logForging(String var) {
		String str = null;
		if(var !=null) {
			str = StringEscapeUtils.escapeJava(var);
		}
		return str;
	}
	
	public static String logForgingSplunk(String inputString) {
		if(!StringUtils.isEmpty(inputString)) {
			String str = StringEscapeUtils.escapeJava(inputString).replaceAll(Pattern.quote("\\\""), "\"");
			return str;
		}else {
			return "";
		}
	}
	
	//========================================= PRIVACY  VIOLATION ==========================================
	
	public static String privacyViolation(String var, boolean isPath) {
		String str = null;
		if(var != null) {
			if(isPath) {
				var = replaceBackSlash(var);
			}
			str = FilenameUtils.normalize(var);
		}
		return str;
	}
	
	//==================================== SERVER SIDE REQUEST FORGING ======================================
	public static String serverSideRequestForgery(String inputString) {
		String str = null;
		if(inputString!=null) {
			str = FilenameUtils.separatorsToUnix(inputString);
		}
		
		return str;
	}
	
	//================================= CHECK VALID DATA or SCRIPT =========================================
	
	public boolean isDateValid(String inValue) {
		boolean outResult = true;
		
		if(inValue != null) {
			inValue = inValue.toUpperCase();
			if((inValue.indexOf("ALERT") != -1)
					|| (inValue.indexOf("JAVASCRIPT") != -1)
					|| (inValue.indexOf("<IMG") != -1)
					|| (inValue.indexOf("IMG") != -1)
					|| (inValue.indexOf("<SCRIPT") != -1)
					|| (inValue.indexOf("</SCRIPT") != -1)
					|| (inValue.indexOf("FOOBAR") != -1)
					|| (inValue.indexOf("<%") != -1)
					|| (inValue.indexOf("%") != -1)
					|| (inValue.indexOf("||") != -1)
					|| (inValue.indexOf("HAVING") != -1)
					|| (inValue.indexOf("--") != -1)
					|| (inValue.indexOf("{") != -1)
					|| (inValue.indexOf("}") != -1)
					|| (inValue.indexOf("<") != -1)
					|| (inValue.indexOf(">") != -1)
					|| (inValue.indexOf("DECLARE") != -1)
					|| (inValue.indexOf("EXEC") != -1)
					|| (inValue.indexOf("DELETE") != -1)
					|| (inValue.indexOf("%>") != -1)					
					) {
				outResult = false;
			}
		}
		return outResult;
	}
	
	
	//=============================== CROSSSITE SCRIPTING =============================
	
	public static String crossSiteScripting(String requestParam) {
		String modifiedReqString = null;
		
		if(requestParam!=null && !StringUtils.isBlank(requestParam)) {
			modifiedReqString = modifiedReqString.replaceAll("(?i)ALERT", "")
												 .replaceAll("(?i)JAVASCRIPT", "")
												 .replaceAll("(?i)<IMG", "")
												 .replaceAll("(?i)IMG", "")
												 .replaceAll("(?i)<SCRIPT", "")
												 .replaceAll("(?i)</SCRIPT", "")
												 .replaceAll("(?i)FOOBAR", "")
												 .replaceAll("<%", "")
												 .replaceAll("(?i)EXEC", "")
												 .replaceAll("%>", "")
												 .replaceAll("alert()", "")
												 .replaceAll("<sCrIpT>", "")
												 .replaceAll("</sCrIpT>", "")
												 .replaceAll("<", "")
												 .replaceAll(">", "")
												 .replaceAll("(?i).jsp", "")
												 .replaceAll("/", "")
												 .replaceAll("()", "")
												 .replaceAll("&", "amp")
												 .replaceAll("--", "");
	
		}else {
			modifiedReqString = requestParam;
		}
		
		return modifiedReqString;
	}
	
	
	//============================= CROSS SITE SCRIPTING WHITELISTING CHAR =======================
	public static String crossSiteScriptingWithWhiteListedChar(String reqParam) {		
		return cleanString(reqParam);
	}

	private static String cleanString(String reqParam) {
		if(reqParam == null) {
			return null;
		}
		String cleanString = "";
		
			for(int charPos = 0 ; charPos < reqParam.length() ; ++charPos) {
				cleanString +=clearChar(reqParam.charAt(charPos));
			}
		return cleanString;
	}

	private static char clearChar(char aChar) {
		//0-9
		for(int i = 48; i < 58 ; ++i) {
			if(aChar == i) {
				return (char) i;
			}
		}
		//A-Z & a-z
		for(int i = 65; i < 91 ; ++i) {
			if(aChar == i) {
				return (char) i;
			}
		}
		
		switch(aChar) {
		case '[' :
			return '[';
		case ']' :
			return ']';
		case '{' :
			return '}';
		case '"' :
			return '"';
		case ':' :
			return ':';
		case '|' :
			return '|';
		case ',' :
			return ',';
		case '.' :
			return '.';
		case '?' :
			return '?';
		case '/' :
			return '/';
		case '\\' :
			return '\\';
		case '+' :
			return '+';
		case '=' :
			return '=';
		case '_' :
			return '_';
		case '-' :
			return '-';
		case ')' :
			return ')';
		case '(' :
			return '(';
		case '*' :
			return '*';			
		case '&' :
			return '&';
		case '^' :
			return '^';
		case '%' :
			return '%';
		case '$' :
			return '$';
		case '#' :
			return '#';
		case '@' :
			return '@';
		case '!' :
			return '!';
		case '~' :
			return '~';
		default :
			return ' ';
		}
	}

	
	//====================== PASSWORD MANAGEMENT ================================================
	public static String passwordManagement(String var) {
		String str = null;
		if(var !=null) {
			str = FilenameUtils.normalize(var);
		}
		return str;
	}
	
	//===================== SYSTEM INFORMATION LEAK ==============================================
	
	public static String systemInfoLeakExternal(String var) {
		String str = null;
		if(var!=null) {
			str = FilenameUtils.normalize(replaceBackSlash(var));
		}
		return str;
	}
	
	//==================== TRUSTED BOUNDARY VIOLATION ============================================
	public static String trustedBoundaryViolation(String var) {
		String str = "";
		if(var!=null) {
			for(int i = 0 ; i < var.length() ; ++i) {
				str +=trustBoundary(var.charAt(i));
			}
		}
		
		return str;
	}

	private static char trustBoundary(char ch) {
		//0-9
		for(int i = 48 ; i < 58 ; ++i) {
			if (ch == i) return (char) i;
		}
		//A - Z
		for(int i = 65 ; i < 91 ; ++i) {
			if (ch == i) return (char) i;
		}
		//a-z
		for(int i = 97 ; i < 123 ; ++i) {
			if (ch == i) return (char) i;
		}
		
		switch(ch) {
		case '/':
			return '/';
		case '.':
			return '.';
		case ',':
			return ',';
		case ':':
			return ':';
		}
		
		return ch;
	}
	
	
	//================================= XML EXTERNAL ENTITY INJECTION ===================================
	public static void xmlExternalEntityInjection (TransformerFactory factory) {
		factory.setAttribute(ACCESS_EXTERNAL_DID, "");
		
	}
	
	
	//=================================== FORMATTED ERROR STACK =========================================
	public static String getErrorStack(Throwable e) {
		return logForging(org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
	}
	
}
