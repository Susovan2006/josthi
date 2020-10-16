package com.josthi.web.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;

import com.josthi.web.po.EmailDbBean;

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

        emailService.sendEmail(mail,"email_Account_Recovery");
        System.out.println("END... Email sent success");
		
	}
	

	
	//This method is called from the Cron Job.
   
	public void sendEmailForPasswordRecovery(EmailDbBean emailDbBean, Map<String, String> emailDetailsModel) throws Exception {
		
		System.out.println("START... Sending email");

        Mail mail = new Mail();
        mail.setFrom(emailDbBean.getSentFrom());//replace with your desired email
        mail.setMailTo(emailDbBean.getSentTo());//replace with your desired email
        mail.setSubject(emailDbBean.getSubject());

        Map<String, Object> model = new HashMap<String, Object>();
        //Type casting to Map<String, Object> from Map<String, String>
        model = (Map) emailDetailsModel;
        mail.setProps(model);

        emailService.sendEmail(mail, emailDbBean.getEmailTemplate());
        System.out.println("END... Email sent success");
		
	}



}
