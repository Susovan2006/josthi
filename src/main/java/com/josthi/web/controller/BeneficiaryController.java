package com.josthi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.josthi.web.bo.BeneficiaryDetailBean;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.service.BeneficiaryService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.service.UserDetailService;
import com.josthi.web.utils.Utils;

@Controller
public class BeneficiaryController {
	
	
	@Autowired
    private BeneficiaryService beneficiaryService;
	
	@Autowired
	private UserAuthService userAuthService;
	
	private static final Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);
	
	
	@GetMapping("/user/getBeneficiaryList")
	public String getBeneficiaryList(Model model, 
									@RequestParam (name="status", required = false, defaultValue = "") String status,
									@RequestParam (name="message", required = false, defaultValue = "") String message,									
									HttpServletRequest request) {
		
		 UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);    	
    	 
		 List<BeneficiaryDetailBean> beneficiaryDetailBeanList= beneficiaryService.getBeneficiaryList(userSessionBean.getCustomerId());
		
		 logger.info(beneficiaryDetailBeanList.toString());
		 logger.info("SIZE ="+beneficiaryDetailBeanList.size());
		 //Initially this bean Class will be Blank. So that the user can populate the fields from UI.
		 BeneficiaryDetailBean beneficiaryDetailBean = new BeneficiaryDetailBean();
		 //beneficiaryDetailBean.setFirstName("Susovan");
		 beneficiaryDetailBean.setState("West Bengal");
		 model.addAttribute("beneficiaryDetailBeanList",beneficiaryDetailBeanList);
    	 model.addAttribute("beneficiaryDetailBean",beneficiaryDetailBean); 
    	 model.addAttribute("action","Save");
    	 
    	 //This portion is mainly used by the refresh for Save / Update and Delete.
    	 if(status!=null && status.length() > 0) {
	    	 model.addAttribute("status", status);
			 model.addAttribute("message", message);
    	 }
		 return MappingConstant.USER_BENEFICIARY_DETAILS;
	}
	
	

	
	@RequestMapping(path ="/user/savebeneficiaryDetails/{custId}", method = RequestMethod.POST)
	public String savebeneficiaryDetails(Model model, BeneficiaryDetailBean beneficiaryDetailBean,
									@PathVariable String custId,
									HttpServletRequest request) {
		
    		logger.info("Save Beneficiary valled by :"+custId);
    		
    		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
    		String sessionCustomerId = userSessionBean.getCustomerId();
    		logger.debug("Customer ID from Java Session:"+sessionCustomerId);
    		
    		try {  
    			
    			//The Session ID and the One coming from the UI should Match, else it will throw an exception.
    			if(!(sessionCustomerId.equalsIgnoreCase(custId))) {
    				model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
        		    model.addAttribute("message", MessageConstant.USER_SESSION_VALIDATION_ERROR_MESSAGE);
    			    return MappingConstant.USER_BENEFICIARY_DETAILS;
        		}
    			
    			logger.info("%^%^%^%^ :"+beneficiaryDetailBean.toString());
    			
    			//Here we are determining, if it will be save or update.
    			//for Update logic..
    			//if(emergencyContactBean.getContactId() != null && userDetailService.isValidContactId(emergencyContactBean.getContactId())) {
    			//	logger.info("Update Request!!");
    			//	boolean status = userDetailService.updateEmergencyDetails(emergencyContactBean, custId);
    			//	return "redirect:/user/emergencyContacts?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="
    			//													+MessageConstant.USER_EMERGENCY_UPDATE_SUCCESS_MESSAGE;
    			
    			//Save Logic
    			//}else {
    				logger.info("New Save Request!!");
    				int getNextID = userAuthService.getNextID();
    				String beneficiaryId = Utils.getNextCustomerID(Constant.USER_TYPE_BENEFICIARY,getNextID);
    				beneficiaryDetailBean.setBeneficiaryID(beneficiaryId);
    				
    				boolean status = beneficiaryService.saveBeneficiaryDetails(beneficiaryDetailBean, custId, getNextID);
    				return "redirect:/user/getBeneficiaryList?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="
    																 +MessageConstant.USER_BENEFECIARY_SAVE_SUCCESS_MESSAGE;
    			//}   			
    		}catch(Exception ex) {
    			logger.error("custId :"+custId, ex);
    			//model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
    		    //model.addAttribute("message", MessageConstant.USER_EMERGENCY_SAVE_ERROR_MESSAGE);
    		    //return MappingConstant.USER_BENEFICIARY_DETAILS;
    		    return "redirect:/user/getBeneficiaryList?status="+MessageConstant.USER_FAILURE_STATUS+"&message="
				 												+MessageConstant.USER_BENEFECIARY_SAVE_ERROR_MESSAGE;
    		}
	}
	
	
	
	
	
	
	
	@GetMapping("/user/beneficiaryDetails")
	public String showBeneficiaryForm(Model model, HttpServletRequest request) {
    	
    	UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
    	logger.debug("******"+userSessionBean.toString());  
    	  
    	
    	try {

    		//For Primary
    		UserDetailsBean primaryBeneficiaryDetailsBean = beneficiaryService.geBeneficiaryDetails(
    														userSessionBean.getCustomerId(), Constant.BENEFICIARY_TYPE_PRIMARY);
    		UserDetailsBean secondaryBeneficiaryDetailsBean = beneficiaryService.geBeneficiaryDetails(
															userSessionBean.getCustomerId(), Constant.BENEFICIARY_TYPE_SECONDARY);
    		
    		//primaryBeneficiaryDetailsBean.setFirstName("Susovan");
    		model.addAttribute("primaryBeneficiaryDetailsBean", primaryBeneficiaryDetailsBean);
        	model.addAttribute("secondaryBeneficiaryDetailsBean", secondaryBeneficiaryDetailsBean);
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
		return MappingConstant.USER_BENEFICIARY_DETAILS;
	}

}
