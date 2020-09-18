package com.josthi.web.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.josthi.web.dao.EmailDao;
import com.josthi.web.po.EmailDbBean;
import com.josthi.web.service.EmailService;

@Service("emailService")
public class EmailServiceImpl implements EmailService{

	@Autowired
	EmailDao emailDao;
	
	@Override
	public boolean queuePasswordRecoveryEmail(EmailDbBean emailDbBean) {
		return emailDao.queuePasswordRecoveryEmail(emailDbBean);
	}

}
