package com.josthi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.PasswordResetBean;
import com.josthi.web.bo.UserPreferencesBean;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.ValidateSession;

@Controller
public class UserPreferenceController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserPreferenceController.class);
	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	CacheConfigService cacheConfigService;
	
	@GetMapping("/user/preferences")
	public String userPreferences(Model model,
			@RequestParam (name="status", required = false, defaultValue = "") String status,
			@RequestParam (name="message", required = false, defaultValue = "") String message,
			HttpServletRequest request) {
		
			String actionStatus = "";
			String displayMsg = "";
		try {
			
			ValidateSession.isValidSession(request);
			
			if(status!=null && status.length() > 0) {
		    	 model.addAttribute("status", status);
				 model.addAttribute("message", message);
	   	 	}
			
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY); 
			String userId = userSessionBean.getCustomerId();
			
			UserPreferencesBean userPreferencesBean = userAuthService.getUserPref(userId);			
			
			//logger.info("userPreferencesBean :"+userPreferencesBean);
			
			List<DropDownBean> languageDropdownList = cacheConfigService.getLanguages();
			List<DropDownBean> timezoneDropdownList = cacheConfigService.getTimeZones();
			
			
			model.addAttribute("languageDropdownList",languageDropdownList);
			model.addAttribute("timezoneDropdownList",timezoneDropdownList);
			model.addAttribute("userPreferencesBean",userPreferencesBean);
			
			return "user/preferences_user";
		} catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());
			return "user/preferences_user";
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return "redirect:/login";
		}catch(Exception ex) {
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());
			return "user/preferences_user";
		}
		
		
			
	}

	
	
	@RequestMapping(path ="/user/saveUserPref/{custId}", method = RequestMethod.POST)
	public String saveUserPref(Model model, UserPreferencesBean userPreferencesBean,
									@PathVariable String custId,
									HttpServletRequest request) {
		logger.info("userPreferencesBean before Update:"+userPreferencesBean.toString());
		
		String actionStatus = "";
		String message = "";
		try {		
			
			ValidateSession.isValidSession(request);
			ValidateSession.isValidUser(request, custId.trim());
			
				boolean status = userAuthService.updateUserPreperences(userPreferencesBean, custId.trim());
				actionStatus = MessageConstant.USER_SUCCESS_STATUS;
				message = "Record Saved Successfully.";
				return "redirect:/user/preferences?status="+actionStatus+"&message="+message;
			
			
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/user/preferences?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			return "redirect:/login";
		}catch(Exception ex) {
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/user/preferences?status="+actionStatus+"&message="+message;
		}
		
		
	}
	
	
	
}
