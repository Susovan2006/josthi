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
public class WebPageImageController {
	
	
	@RequestMapping(value = "/josthiLogoFooter", method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE)

    public void josthiLogoFooter(HttpServletResponse response) throws IOException {

		ClassPathResource imgFile = new ClassPathResource("/static/images/josthi/logo/josthi_footer_logo_user.png");
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }
	
	
	
	
	
	
	@RequestMapping(value = "/josthiVerifiedUserIcon", method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE)

    public void josthiVerifiedUserIcon(HttpServletResponse response) throws IOException {
		ClassPathResource imgFile = new ClassPathResource("/static/images/josthi/status/nav_plain_blue.png");
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }
	
	
	
	@RequestMapping(value = "/josthiHeaderBanner", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)

    public void josthiHeaderBanner(HttpServletResponse response) throws IOException {

		ClassPathResource imgFile = new ClassPathResource("/static/vendor/svg/components/abstract-shapes-17.svg");

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }
	
	

}
