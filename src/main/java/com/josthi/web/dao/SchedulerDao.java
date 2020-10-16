package com.josthi.web.dao;

import com.josthi.web.bo.SchedulerTimerBean;

public interface SchedulerDao {

	//void updateSchedulerDashBoard();

	void updateSchedulerDashBoard(SchedulerTimerBean schedulerTimerBean);

	void insertSchedulerHistory(SchedulerTimerBean schedulerTimerBean);

}
