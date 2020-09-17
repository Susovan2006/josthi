package com.josthi.web.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;


@Controller
public class Scheduler {
	
	@Scheduled(cron="0/5 * * * * ?") 
	public void runMe5sec() {
		System.out.println(" Timer1 runs after 5 sec");
	}
	
	@Scheduled(cron="0/10 * * * * ?") 
	public void runMe10sec() {
		System.out.println(" Timer2 runs after 10 sec");
	}

	@Scheduled(cron="0/15 * * * * ?") 
	public void runMe15sec() {
		System.out.println(" Timer3 runs after 15 sec");
	}
}
