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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.service.UserDetailService;

@Controller
public class EmergencyContactController {
	
	private static final Logger logger = LoggerFactory.getLogger(EmergencyContactController.class);
	
	@Autowired
    private UserDetailService userDetailService;
	
	/*
	 * Load the emergency Contact details Page.
	 */
	@GetMapping("/user/emergencyContacts")
	public String loadUserEmergencyContacts(Model model, 
									@RequestParam (name="status", required = false, defaultValue = "") String status,
									@RequestParam (name="message", required = false, defaultValue = "") String message,
									
									EmergencyContactBean emergencyContactBean ,
									HttpServletRequest request) {
		
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
    	logger.debug("******"+userSessionBean.toString());
    	
    	 List<EmergencyContactBean> emergencyContactBeanList= userDetailService.getEmergencyContactForUser(userSessionBean.getCustomerId());
		
    	 model.addAttribute("emergencyContactBeanList",emergencyContactBeanList);
    	 
    	 if(status!=null && status.length() > 0) {
	    	 model.addAttribute("status", status);
			 model.addAttribute("message", message);
    	 }
		 return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
	}
	
	
	
	
	@RequestMapping(path ="/user/saveEmergencyContact/{custId}", method = RequestMethod.POST)
	public String saveEmergencyContact(Model model, EmergencyContactBean emergencyContactBean,
									@PathVariable String custId,
									HttpServletRequest request) {
		
    		//logger.info("Name "+userDetailsBean.getFirstName()+"--"+userDetailsBean.getLastName());
    		//logger.info("Customer ID :"+userDetailsBean.getUid());
    		logger.info("Customer ID from Session:"+custId);
    		
    		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
    		String sessionCustomerId = userSessionBean.getCustomerId();
    		logger.info("Customer ID from Java Session:"+sessionCustomerId);
    		try {
    			
    			if(!(sessionCustomerId.equalsIgnoreCase(custId))) {
    				model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
        		    model.addAttribute("message", MessageConstant.USER_EMERGENCY_SAVE_VALIDATION_ERROR_MESSAGE);
    			    return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
        		}
    			
    			logger.info("###########"+emergencyContactBean.toString());
    			
    			if(emergencyContactBean.getContactId() != null && userDetailService.isValidContactId(emergencyContactBean.getContactId())) {
    				logger.info("Update Request!!");
    				boolean status = userDetailService.updateEmergencyDetails(emergencyContactBean, custId);
    				return "redirect:/user/emergencyContacts?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="+MessageConstant.USER_EMERGENCY_UPDATE_SUCCESS_MESSAGE;
    			}else {
    				logger.info("New Save Request!!");
    				boolean status = userDetailService.saveEmergencyDetails(emergencyContactBean, custId);
    				return "redirect:/user/emergencyContacts?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="+MessageConstant.USER_EMERGENCY_SAVE_SUCCESS_MESSAGE;
    			}
    			
    			
    	    	
    			//emergencyContactBean.setEmergencyContactName("");
    			//emergencyContactBean.setEmergencyContactNumber("");
    			//emergencyContactBean.setRelation("");
    			//emergencyContactBean.setNotes("");
    			
    			//List<EmergencyContactBean> emergencyContactBeanList= userDetailService.getEmergencyContactForUser(userSessionBean.getCustomerId());   	 		
    	    	//model.addAttribute("emergencyContactBeanList",emergencyContactBeanList);
    		 
    		    //model.addAttribute("status", MessageConstant.USER_SUCCESS_STATUS);
    		    //model.addAttribute("message", MessageConstant.USER_EMERGENCY_SAVE_SUCCESS_MESSAGE);
			    //return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
    			//return "redirect:/user/emergencyContacts?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="+MessageConstant.USER_EMERGENCY_SAVE_SUCCESS_MESSAGE;
    		}catch(Exception ex) {
    			logger.error("custId :"+custId, ex);
    			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
    		    model.addAttribute("message", MessageConstant.USER_EMERGENCY_SAVE_ERROR_MESSAGE);
			    return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
    		}
	}
	
	
	
	@RequestMapping("/user/deleteEmergencyContact/{contactId}")
	public String deleteProduct(Model model, @PathVariable(name = "contactId") int contactId) {
		try {
			userDetailService.deleteEmergencyContact(contactId);
			logger.info("contactId :"+contactId+" deleted Successfully..");
			//model.addAttribute("status", MessageConstant.USER_SUCCESS_STATUS);
		    //model.addAttribute("message", MessageConstant.USER_EMERGENCY_DELETE_SUCCESS_MESSAGE);
			//return "redirect:/user/emergencyContacts"; 
			//return "redirect:/user/emergencyContacts/"+MessageConstant.USER_SUCCESS_STATUS+"/"+MessageConstant.USER_EMERGENCY_DELETE_SUCCESS_MESSAGE;
			return "redirect:/user/emergencyContacts?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="+MessageConstant.USER_EMERGENCY_DELETE_SUCCESS_MESSAGE;
		}catch(Exception ex) {
			logger.error("contactId :"+contactId, ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
		    model.addAttribute("message", MessageConstant.USER_EMERGENCY_GENERIC_ERROR);
		    return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
		}
	}
	
	
	//EDIT Emergency Contact 
	@RequestMapping("/user/editEmergencyContact/{contactId}")
	public String editEmergencyContact(Model model, @PathVariable(name = "contactId") int contactId,
										EmergencyContactBean emergencyContactBean,
										HttpServletRequest request) {
		try {
			
			
			
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
			userDetailService.getEmergencyContact(emergencyContactBean, contactId, userSessionBean.getCustomerId());
			logger.info("Emergency Contact Update Request from :"+ userSessionBean.getCustomerId());
	    	 List<EmergencyContactBean> emergencyContactBeanList= userDetailService.getEmergencyContactForUser(userSessionBean.getCustomerId());	 		
	    	 model.addAttribute("emergencyContactBeanList",emergencyContactBeanList);
	    	 model.addAttribute("action","Update");
				
	    	 return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
		
		}catch(Exception ex) {
			logger.error("contactId :"+contactId, ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
		    model.addAttribute("message", MessageConstant.USER_EMERGENCY_GENERIC_ERROR);
		    return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
		}
	}
	

}
