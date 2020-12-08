package com.josthi.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.josthi.web.bo.AgentRegistrationBean;
import com.josthi.web.bo.UserAuthBo;
import com.josthi.web.bo.UserRegistrationBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.scheduler.EmailScheduler;
import com.josthi.web.service.EmailService;
import com.josthi.web.service.UserAuthService;
import com.josthi.web.service.UserRegistrationService;
import com.josthi.web.utils.Security;
import com.josthi.web.utils.Utils;


@Controller
public class AgentRegistrationController {

	
	@Autowired
	UserAuthService userAuthService;
	
	@Autowired
	UserRegistrationService userRegistrationService;
	
	@Autowired
	EmailService emailService;
	
	private static final Logger logger = LoggerFactory.getLogger(AgentRegistrationController.class);
	
	    		
		@GetMapping("/forAgents")
		public String forAgents(AgentRegistrationBean agentRegistrationBean) {
			return "for_agents";
		}
		
		
		@RequestMapping(path ="/registerAgent", method = RequestMethod.POST)
		public String registerAgent(UserAuthBo userAuthBo, AgentRegistrationBean agentRegistrationBean,
											HttpSession session, 
											Model model) throws Exception {
			
			logger.info("###############"+agentRegistrationBean.toString());
			try {
				//Basic Validation
				if(!(agentRegistrationBean.getPassword().trim().equals(agentRegistrationBean.getConfirmPassword().trim()))) {
					model.addAttribute("errorMessage", MessageConstant.NEW_REGISTRATION_FAILED_UNMATCH_PASSWORD);
					return MappingConstant.AGENT_REGISTRATION;
				}
				
				//Null Check
				//All the field should have the values.
				if(StringUtils.isEmpty(agentRegistrationBean.getFirstName().trim()) || 
						StringUtils.isEmpty(agentRegistrationBean.getLastName().trim())	||
						StringUtils.isEmpty(agentRegistrationBean.getEmailID().trim()) ||
						StringUtils.isEmpty(agentRegistrationBean.getContactNo().trim()) ||
						StringUtils.isEmpty(agentRegistrationBean.getPassword().trim()) ||
						StringUtils.isEmpty(agentRegistrationBean.getConfirmPassword().trim())
						){
					model.addAttribute("errorMessage", MessageConstant.NEW_REGISTRATION_FAILED_REQUIRED_FIELDS);
					return MappingConstant.AGENT_REGISTRATION;
				}
				
				
				//Not an existing User.
				if(!(isValidUserIDOnly(agentRegistrationBean.getEmailID().trim()))) {
					int getNextID = userAuthService.getNextID();
					String agentID = Utils.getNextCustomerID(Constant.USER_TYPE_AGENT,getNextID);
					logger.info("Agent ID"+agentID);
					
					agentRegistrationBean.setAgentID(agentID);
					
					//Here we are encrypting the Password and storing in the Database.
					agentRegistrationBean.setConfirmPassword(Security.encrypt(agentRegistrationBean.getConfirmPassword().trim()));
					agentRegistrationBean.setFirstName(Utils.convertToCamelCase(agentRegistrationBean.getFirstName().trim()));
					agentRegistrationBean.setLastName(Utils.convertToCamelCase(agentRegistrationBean.getLastName().trim()));
					
					boolean status = userRegistrationService.registerNewAgent(agentRegistrationBean, getNextID);
					if(status) {
						System.out.println("---- SUCCESS ------");
						//email section
						//Customer ID
						// F & L Name.
						Map<String, String> map = new HashMap<String, String>();
				        map.put("name", agentRegistrationBean.getFirstName()+" "+agentRegistrationBean.getLastName());
				        map.put("custID", agentID);
				        
				        EmailDbBean emailDbBean = Utils.getEmailBeanForWelcome(agentRegistrationBean.getEmailID().trim(), Utils.mapToString(map));
				        System.out.println(emailDbBean);
						boolean emailQueueStatus = emailService.queueEmail(emailDbBean);
						if(emailQueueStatus) {				
							model.addAttribute("successMessage", MessageConstant.NEW_REGISTRATION_SUCCESSFUL);
							EmailScheduler.ENAMBLE_TIMER = true;  //enable timer for all
							return MappingConstant.LOGIN_PAGE;
						}else{
							model.addAttribute("successMessage", MessageConstant.NEW_REGISTRATION_SUCCESSFUL_CONDITIONAL);
							return MappingConstant.LOGIN_PAGE;
						}
					
					}else {
						model.addAttribute("errorMessage", MessageConstant.NEW_REGISTRATION_FAILED);
						return MappingConstant.AGENT_REGISTRATION;
					}
				
					
				}else {
					model.addAttribute("errorMessage", MessageConstant.NEW_REGISTRATION_DUPLICATE_USER_ID);
					return MappingConstant.AGENT_REGISTRATION;
				}
			}catch(Exception ex) {
				model.addAttribute("errorMessage", "System Error occured during the registration process, please try later or contact the System Admin.");
				return MappingConstant.AGENT_REGISTRATION;
			}
			
		}
				
		
		
		private boolean isValidUserIDOnly(String emailID) {
			
			 boolean result = false; 
			 int count = userAuthService.isValidUserID(emailID);
			 if (count > 0) { result = true; }
			 return result;
		}
	
}
