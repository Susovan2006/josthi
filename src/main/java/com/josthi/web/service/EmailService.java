package com.josthi.web.service;

import org.springframework.stereotype.Service;

import com.josthi.web.po.EmailDbBean;


public interface EmailService {

	boolean queuePasswordRecoveryEmail(EmailDbBean emailDbBean);

}
