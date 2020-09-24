package com.josthi.web.service;

import com.josthi.web.bo.SchedulerTimerBean;

public interface SchedulerService {

	void updateSchedulerDetails(SchedulerTimerBean schedulerTimerBean);

	void runIDRollOverTask(String cronExpression);

	void midNightAccountUnlock(String cronExpression);

}
