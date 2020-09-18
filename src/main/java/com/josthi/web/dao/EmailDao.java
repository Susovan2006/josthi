package com.josthi.web.dao;

import java.util.List;

import com.josthi.web.po.EmailDbBean;

public interface EmailDao {

	List<EmailDbBean> getEmailsListToTrigger();

	boolean queuePasswordRecoveryEmail(EmailDbBean emailDbBean);

	boolean updateEmailSentStatus(EmailDbBean emailBean);

}
