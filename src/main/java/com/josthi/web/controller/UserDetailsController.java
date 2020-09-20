package com.josthi.web.controller ;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import com.josthi.web.service.UserDetailService ;
import com.josthi.web.bo.UserDetailsBean ;
//@Controller
public class UserDetailsController{
	//@Autowired
    private UserDetailService userDetailService;
	
	
	
	//@RequestMapping(value="/url1.do",method = RequestMethod.POST)
	public ModelAndView doUserDetailsBean(@ModelAttribute("userDetailsBean") UserDetailsBean userDetailsBean,BindingResult result) {
		//userDetailService.service(userDetailsBean);
		 return new ModelAndView("jspPageName","modelAttribute",userDetailsBean);
		  }
	}
