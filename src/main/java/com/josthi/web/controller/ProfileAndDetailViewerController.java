package com.josthi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.bo.UserDetailsBeanForProfile;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.BeneficiaryService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.service.UserDetailService;
import com.josthi.web.utils.ValidateSession;

@Controller
public class ProfileAndDetailViewerController {
	
	@Autowired
    private UserDetailService userDetailService;
	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
    private BeneficiaryService beneficiaryService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileAndDetailViewerController.class);

	
	
	/* ============================================================================================================================ */
    /* ============================== U S E R    P R O F I L E   F O R     A G E N T   A N D   A D M I N ========================== */
    /* ============================================================================================================================ */
    
    @GetMapping("/common/viewProfileUser/{userID}")
	public String viewProfileUser(Model model, @PathVariable(name = "userID") String userID,
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
    
    
    
    @GetMapping("/common/viewBeneficiaryList/{userID}")
	public String viewBeneficiaryList(Model model, @PathVariable(name = "userID") String userID,												
																HttpServletRequest request) {
		String actionStatus = "";
		String message = "";
    	try {
    		
    		ValidateSession.isValidSession(request);
    		
    		//Bad Query - need to resolve later, need to pick up, what ever is required.
    		List<BeneficiaryDetailBean> beneficiaryDetailBeanList= beneficiaryService.getBeneficiaryList(userID);
    		
    		UserDetailsBeanForProfile userDetailsBeanForProfile = userDetailService.getProfileDisplayDetails(userID);
    		
    		model.addAttribute("beneficiaryDetailBeanList",beneficiaryDetailBeanList);
    		model.addAttribute("userDetailsBeanForProfile",userDetailsBeanForProfile);
    		model.addAttribute("userType","Josthi User");
    		//model.addAttribute("userAuthBo",userAuthBo);
    		
    		return "/common/beneficiary_list_view_in_frame";
    	}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "/common/beneficiary_list_view_in_frame?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			logger.error("userID :"+userID, ex);
			message = "Error Occured while Fetching the User details.";
			return "/common/beneficiary_list_view_in_frame?status="+MessageConstant.USER_FAILURE_STATUS+"&message="+message;
		}
    	
			
	}
    
    
    //
    
    @GetMapping("/common/viewEmergencyContact/{userID}")
	public String viewEmergencyContact(Model model, @PathVariable(name = "userID") String userID,												
																HttpServletRequest request) {
		String actionStatus = "";
		String message = "";
    	try {
    		
    		ValidateSession.isValidSession(request);
    		
    		//Bad Query - need to resolve later, need to pick up, what ever is required.
    		List<EmergencyContactBean> emergencyContactBeanList= userDetailService.getEmergencyContactForUser(userID);
    		
    		UserDetailsBeanForProfile userDetailsBeanForProfile = userDetailService.getProfileDisplayDetails(userID);
    		
    		model.addAttribute("emergencyContactBeanList",emergencyContactBeanList);
    		model.addAttribute("userDetailsBeanForProfile",userDetailsBeanForProfile);
    		model.addAttribute("userType","Josthi User");
    		//model.addAttribute("userAuthBo",userAuthBo);
    		
    		return "/common/emergency_contact_list_view_in_frame";
    	}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "/common/emergency_contact_list_view_in_frame?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			logger.error("userID :"+userID, ex);
			message = "Error Occured while Fetching the Emergency Contact details.";
			return "/common/emergency_contact_list_view_in_frame?status="+MessageConstant.USER_FAILURE_STATUS+"&message="+message;
		}
    	
			
	}
    
    
    @GetMapping("/common/viewProfileBeneficiary/{userID}")
	public String viewProfileBeneficiary(Model model, @PathVariable(name = "userID") String userID,												
																HttpServletRequest request) {
		String actionStatus = "";
		String message = "";
    	try {
    		
    		ValidateSession.isValidSession(request);
    		
    		
    		
    		BeneficiaryDetailBean beneficiaryDetailBean = beneficiaryService.getBeneficiaryDetailToView(userID);   		
    		UserDetailsBeanForProfile userDetailsBeanForProfile = userDetailService.getProfileDisplayDetails(userID);
    		
    		String primaryCustomerId = beneficiaryDetailBean.getCustomerID();
    		String hostUserFirstAndLastName = userAuthService.getUserDetails(primaryCustomerId);
    		
    		model.addAttribute("beneficiaryDetailBean",beneficiaryDetailBean);
    		model.addAttribute("userDetailsBeanForProfile",userDetailsBeanForProfile);
    		model.addAttribute("userType","Josthi Beneficiary");
    		model.addAttribute("hostUserFirstAndLastName",hostUserFirstAndLastName);
    		
    		//model.addAttribute("userAuthBo",userAuthBo);
    		
    		return "/common/beneficiary_profile_view_in_frame";
    	}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "/common/beneficiary_profile_view_in_frame?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			logger.error("userID :"+userID, ex);
			message = "Error Occured while Fetching the User details.";
			return "/common/beneficiary_profile_view_in_frame?status="+MessageConstant.USER_FAILURE_STATUS+"&message="+message;
		}
    	
			
	}
	
	
}
