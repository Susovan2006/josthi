package com.josthi.web.constants;

public interface SchedulerConstant {

	//SCHEDULER STATUS
	
	String SCHEDULER_SUCCESS_STATUS = "COMPLETED";
	String SCHEDULER_FAILURE_STATUS = "FAILED";
	String SCHEDULER_EXCEPTION_STATUS = "Exception Occured while running the timer";
	
	//ID ROLLOVER TIMER
	String ID_ROLL_OVER_TASK_ID = "TSK_001";
	String ID_ROLL_OVER_TASK_NAME = "Daily UID Rollover";
	String ID_ROLL_OVER_TASK_DESC = "This Task will set the default id to 1 every midnight";
}
