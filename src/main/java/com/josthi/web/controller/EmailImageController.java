package com.josthi.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class EmailImageController {

	
	
	    //Image Controller
		//http://zetcode.com/springboot/serveimage/
		@RequestMapping(value = "/josthiLogo", method = RequestMethod.GET,
	            produces = MediaType.IMAGE_JPEG_VALUE)

	    public void getImage(HttpServletResponse response) throws IOException {

			ClassPathResource imgFile = new ClassPathResource("/emailImages/logo_240X70.png");

	        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
	    }
		
		//http://zetcode.com/springboot/serveimage/
		@RequestMapping(value = "/button", method = RequestMethod.GET,
			         produces = MediaType.IMAGE_JPEG_VALUE)

	    public void getButtonImage(HttpServletResponse response) throws IOException {
					ClassPathResource imgFile = new ClassPathResource("/emailImages/button-background.png");
			        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
	    }
		
		@RequestMapping(value = "/header", method = RequestMethod.GET,
		         produces = MediaType.IMAGE_JPEG_VALUE)

		   public void getheaderImage(HttpServletResponse response) throws IOException {
						ClassPathResource imgFile = new ClassPathResource("/emailImages/stripes.png");
				        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		   }
		
		@RequestMapping(value = "/headerBanner", method = RequestMethod.GET,
		         produces = MediaType.IMAGE_JPEG_VALUE)

		   public void getheaderShineImage(HttpServletResponse response) throws IOException {
						ClassPathResource imgFile = new ClassPathResource("/emailImages/head-shine.png");
				        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		   }
		
		
		
		//----------------------- Welcome Page ----------------------------------------
		
		@RequestMapping(value = "/basicMedical", method = RequestMethod.GET,
		         produces = MediaType.IMAGE_JPEG_VALUE)

		   public void basicMedical(HttpServletResponse response) throws IOException {
						ClassPathResource imgFile = new ClassPathResource("/emailImages/basic-medical.jpg");
				        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		   }
		
		   
		@RequestMapping(value = "/emailLanding", method = RequestMethod.GET,
		         produces = MediaType.IMAGE_JPEG_VALUE)
		   public void emailLanding(HttpServletResponse response) throws IOException {
						ClassPathResource imgFile = new ClassPathResource("/emailImages/email-landing.jpg");
				        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		   }
		
		@RequestMapping(value = "/emergency", method = RequestMethod.GET,
		         produces = MediaType.IMAGE_JPEG_VALUE)
		   public void emergency(HttpServletResponse response) throws IOException {
						ClassPathResource imgFile = new ClassPathResource("/emailImages/emergency.jpg");
				        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		   }
		
		@RequestMapping(value = "/icoFacebook", method = RequestMethod.GET,
		         produces = MediaType.IMAGE_JPEG_VALUE)
		   public void icoFacebook(HttpServletResponse response) throws IOException {
						ClassPathResource imgFile = new ClassPathResource("/emailImages/ico_facebook.jpg");
				        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		   }
		
		@RequestMapping(value = "/icoInstagram", method = RequestMethod.GET,
		         produces = MediaType.IMAGE_JPEG_VALUE)
		   public void icoInstagram(HttpServletResponse response) throws IOException {
						ClassPathResource imgFile = new ClassPathResource("/emailImages/ico_instagram.jpg");
				        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		   }
		
		@RequestMapping(value = "/icoLinkedin", method = RequestMethod.GET,
		         produces = MediaType.IMAGE_JPEG_VALUE)
		   public void icoLinkedin(HttpServletResponse response) throws IOException {
						ClassPathResource imgFile = new ClassPathResource("/emailImages/ico_linkedin.jpg");
				        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		   }
		
		@RequestMapping(value = "/icoTwitter", method = RequestMethod.GET,
		         produces = MediaType.IMAGE_JPEG_VALUE)
		   public void icoTwitter(HttpServletResponse response) throws IOException {
						ClassPathResource imgFile = new ClassPathResource("/emailImages/ico_twitter.jpg");
				        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		   }
		
		@RequestMapping(value = "/regularService", method = RequestMethod.GET,
		         produces = MediaType.IMAGE_JPEG_VALUE)
		   public void regularService(HttpServletResponse response) throws IOException {
						ClassPathResource imgFile = new ClassPathResource("/emailImages/regular-service.jpg");
				        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
		   }
		
		
}
