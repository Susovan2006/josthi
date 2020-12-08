package com.josthi.web.controller ;
 import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import com.josthi.web.service.UserAuthService;
import com.josthi.web.service.UserDetailService ;
import com.josthi.web.utils.ValidateSession;
import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserDetailsBean ;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;

//@Help: https://www.dariawan.com/tutorials/spring/spring-boot-thymeleaf-crud-example/

@Controller
public class UserDetailsController{
	
	
	@Autowired
    private UserDetailService userDetailService;
	
	@Autowired
	UserAuthService userAuthService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsController.class);
	
    @GetMapping("/user/profile")
	public String userProfile(Model model, UserDetailsBean userDetailsBean, 
											HttpServletRequest request) {
    	
    	try {
    		
    		ValidateSession.isValidSession(request);
    		
	    	UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
	    	logger.debug("******"+userSessionBean.toString());  
	    	    
	    	UserDetailsBean userDetailsfromDb = userDetailService.getUserDetails(userSessionBean.getCustomerId());
	    	userDetailService.setDataToDisplay(userDetailsfromDb, userDetailsBean);
	    	
	    	logger.debug("Bean :"+userDetailsBean.toString());
	    		
			return MappingConstant.USER_PROFILE_DETAILS;
			
    	}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());			
			return MappingConstant.USER_PROFILE_DETAILS;
		
    	}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			String status = MessageConstant.USER_FAILURE_STATUS;
			String message =  ex.getMessage();
			return "redirect:/login?status="+status+"&message="+message;
		
    	}catch(Exception ex) {
    		logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());						
			return MappingConstant.USER_PROFILE_DETAILS;
		}
	}
    
    
    
    
    
    
    @RequestMapping(path ="/user/userProfileUpdate/{custId}", method = RequestMethod.POST)
	public String userProfileUpdate(Model model, UserDetailsBean userDetailsBean,  
									@PathVariable String custId,
									HttpServletRequest request) {
    		
    		
    		String actionStatus = "";
			String message = "";
    		try {
    			ValidateSession.isValidSession(request);
        		ValidateSession.isValidUser(request, custId.trim());
    			
    			
    			String status = userDetailService.updateUserDetails(userDetailsBean, custId);
    		 
    		    model.addAttribute("status", MessageConstant.USER_SUCCESS_STATUS);
    		    model.addAttribute("message", MessageConstant.USER_PROFILE_UPDATE_SUCCESS_MESSAGE);
			    return MappingConstant.USER_PROFILE_DETAILS;
    		
    		}catch(UserExceptionInvalidData ex) {
    			logger.error(ex.getMessage(), ex);
    			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
    		    model.addAttribute("message", ex.getMessage());
			    return MappingConstant.USER_PROFILE_DETAILS;
    		
    		}catch(UserException ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/login?status="+actionStatus+"&message="+message;
    		
    		}catch(Exception ex) {
    			logger.error("custId :"+custId, ex);
    			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
    		    model.addAttribute("message", MessageConstant.USER_PROFILE_UPDATE_ERROR_MESSAGE);
			    return MappingConstant.USER_PROFILE_DETAILS;
    		}
	}
    

    /* ============================================================================================================================ */
    /* ============================== U S E R    P R O F I L E   F O R     A G E N T   A N D   A D M I N ========================== */
    /* ============================================================================================================================ */
    
    @GetMapping("/common/viewProfileUser/{userID}")
	public String viewProfile(Model model, @PathVariable(name = "userID") String userID,
												UserDetailsBean userDetailsBean,
												HttpServletRequest request) {
		String actionStatus = "";
		String message = "";
    	try {
    		
    		ValidateSession.isValidSession(request);
    		
    		UserDetailsBean userDetailsfromDb = userDetailService.getUserDetails(userID);
    		UserAuthBo userAuthBo = userAuthService.getProfileDisplayDetails(userID);
    		model.addAttribute("userDetailsfromDb",userDetailsfromDb);
    		model.addAttribute("userType","Josthi User");
    		model.addAttribute("userAuthBo",userAuthBo);
    		
    		return "/common/user_profile_view_in_frame";
    	}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "/common/user_profile_view_in_frame?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			logger.error("userID :"+userID, ex);
			message = "Error Occured while Fetching the User details.";
			return "/common/user_profile_view_in_frame?status="+MessageConstant.USER_FAILURE_STATUS+"&message="+message;
		}
    	
			
	}
    
    
    
}
