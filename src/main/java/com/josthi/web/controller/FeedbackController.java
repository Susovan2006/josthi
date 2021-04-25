package com.josthi.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.josthi.web.bo.AgentFeedbackBean;
import com.josthi.web.bo.DropDownBean;
import com.josthi.web.bo.EmergencyContactBean;
import com.josthi.web.bo.JosthiFeedbackBean;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.exception.UserException;
import com.josthi.web.exception.UserExceptionInvalidData;
import com.josthi.web.service.CacheConfigService;
import com.josthi.web.service.FeedbackService;
import com.josthi.web.utils.ValidateSession;

@Controller
public class FeedbackController {

	
	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);
	
	@Autowired
	CacheConfigService cacheConfigService;
	
	@Autowired
	FeedbackService feedbackService;
	
	
	@GetMapping("/user/feedback")
	public String userFeedback(Model model, 
			@RequestParam (name="status", required = false, defaultValue = "") String status,
			@RequestParam (name="message", required = false, defaultValue = "") String message,	
			@RequestParam (name="statusJosthi", required = false, defaultValue = "") String statusJosthi,
			@RequestParam (name="messageJosthi", required = false, defaultValue = "") String messageJosthi,
			HttpServletRequest request,
			HttpSession session) {
		try {
			 ValidateSession.isValidSession(request);   	
	    	 String customerId = ValidateSession.getUserId(request);
	    	 
	    	 
	    	 List<DropDownBean> agentList = cacheConfigService.getAgentList(customerId);
	    	 model.addAttribute("agentList", agentList);
	    	 model.addAttribute("agentFeedbackBean", new AgentFeedbackBean());
	    	 
	    	 model.addAttribute("josthiFeedbackBean", new JosthiFeedbackBean());
	    	 
	    	//This portion is mainly used by the refresh for Save / Update and Delete.
	    	 if(status!=null && status.length() > 0) {
		    	 model.addAttribute("status", status);
				 model.addAttribute("message", message);
	    	 }
	    	 
	    	 if(statusJosthi!=null && statusJosthi.length() > 0) {
		    	 model.addAttribute("statusJosthi", statusJosthi);
				 model.addAttribute("messageJosthi", messageJosthi);
				 model.addAttribute("gotoPoint","card2");
	    	 }
	    	 
	    	 return MappingConstant.USER_FEEDBACK;
	    	 
		}catch(UserExceptionInvalidData ex) {
			logger.error(ex.getMessage(), ex);
			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
			model.addAttribute("message", ex.getMessage());			
			return MappingConstant.USER_FEEDBACK;
		
		 }catch(UserException ex) {
				logger.error(ex.getMessage(), ex);
				status = MessageConstant.USER_FAILURE_STATUS;
				message =  ex.getMessage();
				return "redirect:/login?status="+status+"&message="+message;
			
	    }catch(Exception ex) {
	    		logger.error(ex.getMessage(), ex);
				model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
				model.addAttribute("message", "System Error Occured while loading the details. Log-out and relogin.");							
				return MappingConstant.USER_FEEDBACK;
			}
		
	}
	
	
	
	@RequestMapping(path ="/user/saveFeedbackForAgent/{custId}", method = RequestMethod.POST)
	public String saveFeedbackForAgent(Model model, AgentFeedbackBean agentFeedbackBean,
									@PathVariable String custId,
									HttpServletRequest request) {
		
    		logger.debug("Customer ID from Session:"+custId);
    		
    		String actionStatus = "";
    		String message = "";  		
    		try { 
    			
    			ValidateSession.isValidSession(request);
        		ValidateSession.isValidUser(request, custId.trim());    			
    			
        		logger.info("FEEDBACK Before Save : "+agentFeedbackBean.toString());
        		agentFeedbackBean.setFeedbackBy(custId);
        		
        		boolean status = feedbackService.saveFeedbackAgent(agentFeedbackBean);
        		
    			actionStatus = MessageConstant.USER_SUCCESS_STATUS;
    			message = "Feedback saved successfully, thanks for your time !!";
    			return "redirect:/user/feedback?status="+actionStatus+"&message="+message;
    			 			
    		}catch(UserExceptionInvalidData ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/user/feedback?status="+actionStatus+"&message="+message;
    		}catch(UserException ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/login?status="+actionStatus+"&message="+message;
    		}catch(Exception ex) {
    			logger.error("custId :"+custId, ex);
    			message = "Application error occured while saving the Agent's Feedback, please try later, else contact the Customer Service.";
    		    return "redirect:/user/feedback?status="+MessageConstant.USER_FAILURE_STATUS+"&message="
																+message;
    		}
	}
	
	
	
	
	
	@RequestMapping(path ="/user/saveFeedbackForJosthi/{custId}", method = RequestMethod.POST)
	public String saveFeedbackForJosthi(Model model, JosthiFeedbackBean josthiFeedbackBean,
									@PathVariable String custId,
									HttpServletRequest request) {
		
    		logger.debug("Customer ID from Session:"+custId);
    		
    		String actionStatus = "";
    		String message = "";  		
    		try { 
    			
    			ValidateSession.isValidSession(request);
        		ValidateSession.isValidUser(request, custId.trim());    			
    			
        		logger.info("FEEDBACK Before Save : "+josthiFeedbackBean.toString());
        		josthiFeedbackBean.setFeedbackBy(custId);
        		
        		boolean status = feedbackService.saveFeedbackJosthi(josthiFeedbackBean);
        		
    			actionStatus = MessageConstant.USER_SUCCESS_STATUS;
    			message = "Feedback saved successfully, thanks for your time !!";
    			return "redirect:/user/feedback?statusJosthi="+actionStatus+"&messageJosthi="+message;
    			 			
    		}catch(UserExceptionInvalidData ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/user/feedback?statusJosthi="+actionStatus+"&messageJosthi="+message;
    		}catch(UserException ex) {
    			logger.error(ex.getMessage(), ex);
    			actionStatus = MessageConstant.USER_FAILURE_STATUS;
    			message = ex.getMessage();
    			return "redirect:/login?status="+actionStatus+"&message="+message;
    		}catch(Exception ex) {
    			logger.error("custId :"+custId, ex);
    			message = "Application error occured while saving the Feedback, please try later, else contact the Customer Service.";
    		    return "redirect:/user/feedback?statusJosthi="+MessageConstant.USER_FAILURE_STATUS+"&messageJosthi="
																+message;
    		}
	}
	
	
	
}
