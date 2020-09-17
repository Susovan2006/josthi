package com.josthi.web.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;

@Controller
public class EmailController {

	
	@Autowired
    private EmailSenderService emailService;
	
	public void sendEmail() throws Exception {
		
		System.out.println("START... Sending email");

        Mail mail = new Mail();
        mail.setFrom("myjosthi@gmail.com");//replace with your desired email
        mail.setMailTo("susovan2006@gmail.com");//replace with your desired email
        mail.setSubject("Email with Spring boot and thymeleaf template!");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Susovan!");
        model.put("password", "XXXXXXX");
        model.put("sign", "Java Developer");
        mail.setProps(model);

        emailService.sendEmail(mail);
        System.out.println("END... Email sent success");
		
	}
}
