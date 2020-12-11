package com.josthi.web.dao;

import com.josthi.web.bo.SchedulerTimerBean;

public interface HistoryDao {

	boolean logActivityHistory(String activityFor, String activityBy, String activityNotes) throws Exception;



}
