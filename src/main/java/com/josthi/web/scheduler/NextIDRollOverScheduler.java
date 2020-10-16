package com.josthi.web.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.josthi.web.service.SchedulerService;

@Controller
public class NextIDRollOverScheduler {

	private static final Logger logger = LoggerFactory.getLogger(NextIDRollOverScheduler.class);
	
	
	@Autowired
	SchedulerService schedulerService;
	
	
	//Testing --> public static final String CRON_EXPRESSION = "0/30 * * * * ?";
	
	
	public static final String CRON_EXPRESSION = "0 0 0 * * ?";
	public static final String ZONE = "Indian/Maldives";
	
	
	//This Timer should run everyday at 12:00 AM IST
	@Scheduled(cron=CRON_EXPRESSION, zone = ZONE) 
	public void rollOverUserIDGen() {
		logger.info("-------------------- TASK : ID ROLL OVER---------------------");
		schedulerService.runIDRollOverTask(CRON_EXPRESSION);
		logger.info("-------------------------- Complete--------------------------");
	}

	
}
