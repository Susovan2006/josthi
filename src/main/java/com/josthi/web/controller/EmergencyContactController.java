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
	 * Initially there will be no status or message, but for Save, Update delete we will be passing the message.
	 * We are taking the User ID from the session.
	 * 
	 */
	@GetMapping("/user/emergencyContacts")
	public String loadUserEmergencyContacts(Model model, 
									@RequestParam (name="status", required = false, defaultValue = "") String status,
									@RequestParam (name="message", required = false, defaultValue = "") String message,									
									EmergencyContactBean emergencyContactBean ,
									HttpServletRequest request) {
		
		 UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);    	
    	 
		 List<EmergencyContactBean> emergencyContactBeanList= userDetailService.getEmergencyContactForUser(userSessionBean.getCustomerId());
		
		 //Initially this bean Class will be Blank. So that the user can populate the fields from UI.
    	 model.addAttribute("emergencyContactBeanList",emergencyContactBeanList); 
    	 model.addAttribute("action","Save");
    	 
    	 //This portion is mainly used by the refresh for Save / Update and Delete.
    	 if(status!=null && status.length() > 0) {
	    	 model.addAttribute("status", status);
			 model.addAttribute("message", message);
    	 }
		 return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
	}
	
	
	
	/**
	 * 
	 * @param model
	 * @param emergencyContactBean
	 * @param custId
	 * @param request
	 * @return
	 * 
	 * This Method is used for the Save and Update of the Emergency Contact Details.
	 * Here the important Param is the ContactID.
	 * If the ContactID is null that means --> Save
	 * If the CountactID is not Null and has entry in Database --> Update.
	 * Customer ID we will be getting from the UI and the session, both has to match.
	 * 
	 */
	@RequestMapping(path ="/user/saveEmergencyContact/{custId}", method = RequestMethod.POST)
	public String saveUpdateEmergencyContact(Model model, EmergencyContactBean emergencyContactBean,
									@PathVariable String custId,
									HttpServletRequest request) {
		
    		logger.debug("Customer ID from Session:"+custId);
    		
    		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
    		String sessionCustomerId = userSessionBean.getCustomerId();
    		logger.debug("Customer ID from Java Session:"+sessionCustomerId);
    		
    		try {  
    			
    			//The Session ID and the One coming from the UI should Match, else it will throw an exception.
    			if(!(sessionCustomerId.equalsIgnoreCase(custId))) {
    				model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
        		    model.addAttribute("message", MessageConstant.USER_EMERGENCY_SAVE_VALIDATION_ERROR_MESSAGE);
    			    return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
        		}
    			
    			logger.debug("Emergency Contact Bean :"+emergencyContactBean.toString());
    			
    			
    			//Here we are determining, if it will be save or update.
    			//for Update logic..
    			if(emergencyContactBean.getContactId() != null && userDetailService.isValidContactId(emergencyContactBean.getContactId())) {
    				logger.info("Update Request!!");
    				boolean status = userDetailService.updateEmergencyDetails(emergencyContactBean, custId);
    				return "redirect:/user/emergencyContacts?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="
    																+MessageConstant.USER_EMERGENCY_UPDATE_SUCCESS_MESSAGE;
    			
    			//Save Logic
    			}else {
    				logger.info("New Save Request!!");
    				boolean status = userDetailService.saveEmergencyDetails(emergencyContactBean, custId);
    				return "redirect:/user/emergencyContacts?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="
    																+MessageConstant.USER_EMERGENCY_SAVE_SUCCESS_MESSAGE;
    			}   			
    		}catch(Exception ex) {
    			logger.error("custId :"+custId, ex);
    			//model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
    		    //model.addAttribute("message", MessageConstant.USER_EMERGENCY_SAVE_ERROR_MESSAGE);
			    //return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
    		    return "redirect:/user/emergencyContacts?status="+MessageConstant.USER_FAILURE_STATUS+"&message="
																+MessageConstant.USER_EMERGENCY_SAVE_ERROR_MESSAGE;
    		}
	}
	
	
	
	
	/**
	 * 
	 * @param model
	 * @param contactId
	 * @return
	 * This method is used for deleting the Contact info.
	 * The deletion will occure based on the ContactID.
	 */
	@RequestMapping("/user/deleteEmergencyContact/{contactId}")
	public String deleteProduct(Model model, @PathVariable(name = "contactId") int contactId) {
		try {
			userDetailService.deleteEmergencyContact(contactId);
			logger.info("contactId :"+contactId+" deleted Successfully..");
			return "redirect:/user/emergencyContacts?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="
																+MessageConstant.USER_EMERGENCY_DELETE_SUCCESS_MESSAGE;
		}catch(Exception ex) {
			logger.error("contactId :"+contactId, ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
		    model.addAttribute("message", MessageConstant.USER_GENERIC_ERROR);
		    return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
		}
	}
	
	
	/**
	 *  
	 * @param model
	 * @param contactId
	 * @param emergencyContactBean
	 * @param request
	 * @return
	 * 
	 * This method is used for editing the Emergency Contact ID. The edit will open the same 
	 * screen but with the value populated from the database.
	 * 
	 */
	@RequestMapping("/user/editEmergencyContact/{contactId}")
	public String editEmergencyContact(Model model, @PathVariable(name = "contactId") int contactId,
										EmergencyContactBean emergencyContactBean,
										HttpServletRequest request) {
		try {
			
			
			
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
			
			//Get the contact details Specific to the ContactID. This will be edited by the user.
			 userDetailService.getEmergencyContact(emergencyContactBean, contactId, userSessionBean.getCustomerId());
			 logger.info("Emergency Contact Update Request from :"+ userSessionBean.getCustomerId());
	    	 
			 //This will fetch all the records specific to the User. This is just for Display purpose in the UI.
			 List<EmergencyContactBean> emergencyContactBeanList= userDetailService.getEmergencyContactForUser(userSessionBean.getCustomerId());	 		
	    	 model.addAttribute("emergencyContactBeanList",emergencyContactBeanList);
	    	 
	    	 //This Param is just to change the name of the Button.
	    	 model.addAttribute("action","Update");
				
	    	 return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
		
		}catch(Exception ex) {
			logger.error("contactId :"+contactId, ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
		    model.addAttribute("message", MessageConstant.USER_GENERIC_ERROR);
		    return MappingConstant.USER_EMERGENCY_CONTACT_DETAILS;
		}
	}
	

}
