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
}
