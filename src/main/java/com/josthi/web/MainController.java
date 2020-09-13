package com.josthi.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {
	
	@GetMapping("/")
	public String hello(Model model) {
		model.addAttribute("message","HelloWorld");
		return "Landing_page_josthi";
	}
	
	@GetMapping("/forAgents")
	public String forAgents(Model model) {
		model.addAttribute("message","HelloWorld");
		return "for_agents";
	}
	
	//login_simple
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("message","HelloWorld");
		return "login_simple";
	}
	
	
	//josthi_signup.html
	@GetMapping("/userSignup")
	public String userSignup(Model model) {
		model.addAttribute("message","HelloWorld");
		return "josthi_signup";
	}
	
	//account_recovery
	@GetMapping("/accountRecovery")
	public String accountRecovery(Model model) {
		model.addAttribute("message","HelloWorld");
		return "account_recovery";
	}
	
	
	//account_recovery
	@GetMapping("/contactUs")
	public String contactUs(Model model) {
			model.addAttribute("message","HelloWorld");
			return "contact_us";
	}
	
	
	//search_lawyers
	@GetMapping("/searchLawyers")
	public String searchLawyers(Model model) {
			model.addAttribute("message","HelloWorld");
			return "search_lawyers";
	}
	
	
	//form-layouts-filters
	@GetMapping("/test")
	public String test(Model model) {
			model.addAttribute("message","HelloWorld");
			return "form-layouts-filters";
	}
	
	//josthi_pricing
	@GetMapping("/pricing")
	public String pricing(Model model) {
			model.addAttribute("message","HelloWorld");
			return "josthi_pricing";
	}
	
	//service_details.html
	@GetMapping("/planDetails")
	public String planDetails(Model model) {
			model.addAttribute("message","HelloWorld");
			return "service_details";
	}
	
	//faq
	@GetMapping("/faq")
	public String faq(Model model) {
				model.addAttribute("message","HelloWorld");
				return "faq";
	}
	
	//faq
	@GetMapping("/terms")
	public String terms(Model model) {
				model.addAttribute("message","HelloWorld");
				return "terms";
	}
	
	//privacy
	@GetMapping("/privacy")
	public String privacy(Model model) {
				model.addAttribute("message","HelloWorld");
				return "privacy";
	}
	
	//user_home_page
	@GetMapping("/userHomePage")
	public String userHomePage(Model model) {
			model.addAttribute("message","HelloWorld");
			return "user_home_page";
	}
	
	
	//user_personal_details
	@GetMapping("/userPersonalDetails")
	public String userPersonalDetails(Model model) {
			model.addAttribute("message","HelloWorld");
			return "user_personal_details";
	}
}
