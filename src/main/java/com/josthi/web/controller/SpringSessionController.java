package com.josthi.web.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.josthi.web.bo.UserAuthBo;



@Controller
public class SpringSessionController {

	private static final Logger logger = LoggerFactory.getLogger(SpringSessionController.class);
	/*
	 * @GetMapping("/") public String process(Model model, HttpSession session) {
	 * 
	 * @SuppressWarnings("unchecked") List<String> messages = (List<String>)
	 * session.getAttribute("MY_SESSION_MESSAGES");
	 * 
	 * if (messages == null) { messages = new ArrayList<>(); }
	 * model.addAttribute("sessionMessages", messages); return "index"; }
	 */

	@PostMapping("/persistMessage")
	public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (messages == null) {
			messages = new ArrayList<>();
			request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		}
		messages.add(msg);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
		return "redirect:/";
	}

	@GetMapping("/destroy")
	public String destroySession(HttpServletRequest request, UserAuthBo userAuthBo, Model model) {
		logger.info("SESSION to Destroy :"+request.getSession().toString());
		request.getSession().invalidate();
		return "login_simple";
	}
}
