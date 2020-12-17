package com.josthi.web.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.UserDetailsBean;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.BeneficiaryService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.service.UserDetailService;
import com.josthi.web.utils.Utils;
import com.josthi.web.utils.ValidateSession;

@Controller
public class UserBeneficiaryController {
	
	
	@Autowired
    private BeneficiaryService beneficiaryService;
	
	@Autowired
	private UserAuthService userAuthService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserBeneficiaryController.class);
	
	
	@GetMapping("/user/getBeneficiaryList")
	public String getBeneficiaryList(Model model, 
									@RequestParam (name="status", required = false, defaultValue = "") String status,
									@RequestParam (name="message", required = false, defaultValue = "") String message,									
									HttpServletRequest request,
									HttpSession session) {
		
		 try {
			 ValidateSession.isValidSession(request);
			 UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);    	
	    	 
			 List<BeneficiaryDetailBean> beneficiaryDetailBeanList= beneficiaryService.getBeneficiaryList(userSessionBean.getCustomerId());
			
			 //Initially this bean Class will be Blank. So that the user can populate the fields from UI.
			 BeneficiaryDetailBean beneficiaryDetailBean = new BeneficiaryDetailBean();
			 beneficiaryDetailBean.setState("West Bengal");
			 model.addAttribute("beneficiaryDetailBeanList",beneficiaryDetailBeanList);
	    	 model.addAttribute("beneficiaryDetailBean",beneficiaryDetailBean); 
	    	 model.addAttribute("action","Save");
	    	 model.addAttribute("header","Add");
	    	 
	    	 //get Blood Group List - We already fetched these vaules on startup, now setting in the Model.
	    	 List<DropDownBean> bloodGroupList = CacheConfigDataController.bloodGroupList;
	    	 model.addAttribute("bloodGroupList",bloodGroupList);
	    	 
	    	 //This portion is mainly used by the refresh for Save / Update and Delete.
	    	 if(status!=null && status.length() > 0) {
		    	 model.addAttribute("status", status);
				 model.addAttribute("message", message);
	    	 }
			 return MappingConstant.USER_BENEFICIARY_DETAILS;
			 
		 }catch(UserExceptionInvalidData ex) {
				logger.error(ex.getMessage(), ex);
				model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
				model.addAttribute("message", ex.getMessage());			
				//model.addAttribute("agentDetailsfromDb",new UserDetailsBean());
				return MappingConstant.USER_BENEFICIARY_DETAILS;
			
		 }catch(UserException ex) {
				logger.error(ex.getMessage(), ex);
				status = MessageConstant.USER_FAILURE_STATUS;
				message =  ex.getMessage();
				return "redirect:/login?status="+status+"&message="+message;
			
	    }catch(Exception ex) {
	    		logger.error(ex.getMessage(), ex);
				model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
				model.addAttribute("message", "System Error Occured while loading the Beneficiary details. Log-out and relogin.");							
				return MappingConstant.USER_BENEFICIARY_DETAILS;
			}
	}
	
	

	
	@RequestMapping(path ="/user/savebeneficiaryDetails/{custId}", method = RequestMethod.POST)
	public String savebeneficiaryDetails(Model model, BeneficiaryDetailBean beneficiaryDetailBean,
									@PathVariable String custId,
									HttpServletRequest request) {
		
    		logger.info("Save Beneficiary called by :"+custId);
    		

    		String actionStatus = "";
    		String message = "";
    		try {  
    			ValidateSession.isValidSession(request);
        		ValidateSession.isValidUser(request, custId.trim());
    			
    			//Here we are determining, if it will be save or update.
    			//for Update logic..
    		    if(beneficiaryDetailBean.getBeneficiaryID() != null && 
    		    					beneficiaryService.isValidBeneficiaryID(beneficiaryDetailBean.getBeneficiaryID())) {
    				logger.info("Update Request!!");
    				boolean status = beneficiaryService.updateBeneficiaryDetails(beneficiaryDetailBean, custId.trim());
    				return "redirect:/user/getBeneficiaryList?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="
    																+MessageConstant.USER_BENEFECIARY_UPDATE_SUCCESS_MESSAGE;    			
    			//Save Logic
    			}else {
    				logger.info("New Save Request!!");
    				int getNextID = userAuthService.getNextID();
    				String beneficiaryId = Utils.getNextCustomerID(Constant.USER_TYPE_BENEFICIARY,getNextID);
    				beneficiaryDetailBean.setBeneficiaryID(beneficiaryId);
    				
    				boolean status = beneficiaryService.saveBeneficiaryDetails(beneficiaryDetailBean, custId, getNextID);
    				return "redirect:/user/getBeneficiaryList?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="
    																 +MessageConstant.USER_BENEFECIARY_SAVE_SUCCESS_MESSAGE;
    			}   			
    		}catch(UserExceptionInvalidData ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/user/getBeneficiaryList?status="+actionStatus+"&message="+message;
    		}catch(UserException ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/login?status="+actionStatus+"&message="+message;
    		}catch(Exception ex) {    			
    			logger.error("custId :"+custId, ex);
    		    return "redirect:/user/getBeneficiaryList?status="+MessageConstant.USER_FAILURE_STATUS+"&message="
				 												+MessageConstant.USER_BENEFECIARY_SAVE_ERROR_MESSAGE;
    		}
	}
	
	
	
	
	
	
	
	@RequestMapping("/user/deleteBeneficiaryDetail/{beneficiaryID}")
	public String deleteProduct(Model model, @PathVariable(name = "beneficiaryID") String beneficiaryID,
																		HttpServletRequest request) {
		try {
			ValidateSession.isValidSession(request);
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY); 
			String sessionCustomerId = userSessionBean.getCustomerId();
			
			beneficiaryService.deleteBeneficiaryDetail(beneficiaryID, sessionCustomerId);
			logger.info("beneficiaryID :"+beneficiaryID+" deleted Successfully..");
			return "redirect:/user/getBeneficiaryList?status="+MessageConstant.USER_SUCCESS_STATUS+"&message="
																+MessageConstant.USER_BENEFECIARY_DELETE_SUCCESS_MESSAGE;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			String actionStatus = MessageConstant.USER_FAILURE_STATUS;
			String message = ex.getMessage();
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			logger.error("beneficiaryID :"+beneficiaryID, ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
		    model.addAttribute("message", MessageConstant.USER_GENERIC_ERROR);
		    return "redirect:/user/getBeneficiaryList?status="+MessageConstant.USER_FAILURE_STATUS+"&message="
																			+MessageConstant.USER_GENERIC_ERROR;
		}
	}
	
	
	
	
	
	@RequestMapping("/user/editBeneficiaryDetail/{beneficiaryID}")
	public String editEmergencyContact(Model model, @PathVariable(name = "beneficiaryID") String beneficiaryID,
										BeneficiaryDetailBean beneficiaryDetailBean,
										HttpServletRequest request) {
		String errorMessage = MessageConstant.USER_GENERIC_ERROR;
		
		
		String actionStatus = "";
		String message = "";
		try {
			
			ValidateSession.isValidSession(request);
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
			
			//Get the Beneficiary Detail Specific to the BeneficiaryID. This will be edited by the user.
			BeneficiaryDetailBean beneficiaryDetailtoEdit = beneficiaryService.getBeneficiaryDetailToEdit(beneficiaryID, userSessionBean.getCustomerId());
			
			if(beneficiaryDetailtoEdit == null) {
				 errorMessage = "There is no Data to Edit";
				 throw new Exception();				 
			 }
			
			
			//This Block is used to display the data in the UI screen.
			if(beneficiaryDetailtoEdit.getDateOfBirthInTimeStamp()!=null) {
				Timestamp ts = beneficiaryDetailtoEdit.getDateOfBirthInTimeStamp();
				Date date = new Date();
				date.setTime(ts.getTime());
				String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
				beneficiaryDetailtoEdit.setDateOfBirth(formattedDate);
			}
			
			logger.info("Beneficiary Update Request from :"+ userSessionBean.getCustomerId() +" for "+ beneficiaryID);
	    	 
			logger.info("beneficiaryDetailtoEdit :"+beneficiaryDetailtoEdit.toString());
			
			 model.addAttribute("beneficiaryDetailBean",beneficiaryDetailtoEdit);
			
			 //This will fetch all the records specific to the User. This is just for Display purpose in the UI.
			 List<BeneficiaryDetailBean> beneficiaryDetailBeanList= beneficiaryService.getBeneficiaryList(userSessionBean.getCustomerId());	 		
			 model.addAttribute("beneficiaryDetailBeanList",beneficiaryDetailBeanList);
	    	 
			 //get Blood Group List - We already fetched these vaules on startup, now setting in the Model.
	    	 List<DropDownBean> bloodGroupList = CacheConfigDataController.bloodGroupList;
	    	 model.addAttribute("bloodGroupList",bloodGroupList);
			 
	    	 //This Param is just to change the name of the Button.
	    	 model.addAttribute("action","Update");
	    	 model.addAttribute("header","Update");
				
	    	 return MappingConstant.USER_BENEFICIARY_DETAILS;
		
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/user/getBeneficiaryList?status="+actionStatus+"&message="+message;
		}catch(UserException ex) {
			logger.error(ex.getMessage(), ex);
			actionStatus = MessageConstant.USER_FAILURE_STATUS;
			message = ex.getMessage();
			return "redirect:/login?status="+actionStatus+"&message="+message;
		}catch(Exception ex) {
			logger.error("beneficiaryID :"+beneficiaryID, ex);
			return "redirect:/user/getBeneficiaryList?status="+MessageConstant.USER_FAILURE_STATUS+"&message="+errorMessage;
		}
	}
	
}
