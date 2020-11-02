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
import com.josthi.web.service.UserDetailService ;
import com.josthi.web.bo.UserDetailsBean ;
import com.josthi.web.bo.UserSessionBean;
import com.josthi.web.constants.Constant;
import com.josthi.web.constants.MappingConstant;
import com.josthi.web.constants.MessageConstant;

//@Help: https://www.dariawan.com/tutorials/spring/spring-boot-thymeleaf-crud-example/

@Controller
public class UserDetailsController{
	
	
	@Autowired
    private UserDetailService userDetailService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsController.class);
	
    @GetMapping("/user/profile")
	public String userProfile(Model model, UserDetailsBean userDetailsBean, 
											HttpServletRequest request) {
    	
    	UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
    	logger.debug("******"+userSessionBean.toString());  
    	    
    	UserDetailsBean userDetailsfromDb = userDetailService.getUserDetails(userSessionBean.getCustomerId());
    	userDetailService.setDataToDisplay(userDetailsfromDb, userDetailsBean);
    	
    	logger.debug("Bean :"+userDetailsBean.toString());
    		
		return MappingConstant.USER_PROFILE_DETAILS;
	}
    
    
    
    
    
    
    @RequestMapping(path ="/user/userProfileUpdate/{custId}", method = RequestMethod.POST)
	public String userProfileUpdate(Model model, UserDetailsBean userDetailsBean,  
									@PathVariable String custId,
									HttpServletRequest request) {
    		logger.info("Name "+userDetailsBean.getFirstName()+"--"+userDetailsBean.getLastName());
    		logger.info("Customer ID :"+userDetailsBean.getUid());
    		logger.info("Customer ID from Session:"+custId);
    		
    		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constant.USER_SESSION_OBJ_KEY);
    		String sessionCustomerId = userSessionBean.getCustomerId();
    		logger.info("Customer ID from Java Session:"+sessionCustomerId);
    		try {
    			
    			if(!(sessionCustomerId.equalsIgnoreCase(custId))) {
    				model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
        		    model.addAttribute("message", MessageConstant.USER_PROFILE_UPDATE_ERROR_MESSAGE);
    			    return MappingConstant.USER_PROFILE_DETAILS;
        		}
    			
    			String status = userDetailService.updateUserDetails(userDetailsBean, custId);
    		 
    		    model.addAttribute("status", MessageConstant.USER_SUCCESS_STATUS);
    		    model.addAttribute("message", MessageConstant.USER_PROFILE_UPDATE_SUCCESS_MESSAGE);
			    return MappingConstant.USER_PROFILE_DETAILS;
    		}catch(Exception ex) {
    			logger.error("custId :"+custId, ex);
    			model.addAttribute("status", MessageConstant.USER_FAILURE_STATUS);
    		    model.addAttribute("message", MessageConstant.USER_PROFILE_UPDATE_ERROR_MESSAGE);
			    return MappingConstant.USER_PROFILE_DETAILS;
    		}
	}
    
	/*
	 * private void setDataToDisplay(UserDetailsBean userDetailsfromDb,
	 * UserDetailsBean userDetailsBean) {
	 * userDetailsBean.setFirstName(userDetailsfromDb.getFirstName());
	 * userDetailsBean.setLastName(userDetailsfromDb.getLastName());
	 * userDetailsBean.setUid(userDetailsfromDb.getUid());
	 * userDetailsBean.setGender(userDetailsfromDb.getGender());
	 * 
	 * userDetailsBean.setUserAddressFirstLine(userDetailsfromDb.
	 * getUserAddressFirstLine());
	 * userDetailsBean.setUserAddressSecondLine(userDetailsfromDb.
	 * getUserAddressSecondLine());
	 * userDetailsBean.setCityTown(userDetailsfromDb.getCityTown());
	 * userDetailsBean.setState(userDetailsfromDb.getState());
	 * userDetailsBean.setCountyDistrict(userDetailsfromDb.getCountyDistrict());
	 * userDetailsBean.setCountry(userDetailsfromDb.getCountry());
	 * userDetailsBean.setZipPin(userDetailsfromDb.getZipPin());
	 * 
	 * userDetailsBean.setMobileNo1(userDetailsfromDb.getMobileNo1());
	 * userDetailsBean.setMobileNo2(userDetailsfromDb.getMobileNo2());
	 * userDetailsBean.setWhatsappNo(userDetailsfromDb.getWhatsappNo());
	 * userDetailsBean.setLandLineNo(userDetailsfromDb.getLandLineNo());
	 * userDetailsBean.setFaxNo(userDetailsfromDb.getFaxNo());
	 * userDetailsBean.setOfficePhNo(userDetailsfromDb.getOfficePhNo());
	 * 
	 * userDetailsBean.setSecondaryEmail(userDetailsfromDb.getSecondaryEmail());
	 * userDetailsBean.setWebsite(userDetailsfromDb.getWebsite());
	 * userDetailsBean.setFacebookLink(userDetailsfromDb.getFacebookLink());
	 * 
	 * userDetailsBean.setUserStatus(userDetailsfromDb.getUserStatus()); }
	 */
    
    
    
}
