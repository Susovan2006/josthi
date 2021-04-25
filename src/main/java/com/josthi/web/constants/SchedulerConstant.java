package com.josthi.web.constants;

public interface SchedulerConstant {

	//SCHEDULER STATUS
	
	String SCHEDULER_SUCCESS_STATUS = "COMPLETED";
	String SCHEDULER_FAILURE_STATUS = "FAILED";
	String SCHEDULER_EXCEPTION_STATUS = "Exception Occurred while running the timer";
	String SCHEDULER_NO_DATA_UPDATE_STATUS ="No records to Update";
	
	//ID ROLLOVER TIMER
	String ID_ROLL_OVER_TASK_ID = "TSK_001";
	String ID_ROLL_OVER_TASK_NAME = "Daily UID Rollover";
	String ID_ROLL_OVER_TASK_DESC = "This Task will set the default id to 1 every midnight";
	
	//ID ROLLOVER TIMER
	String ACCOUNT_UNLOCK_TASK_ID = "TSK_002";
	String ACCOUNT_UNLOCK_TASK_NAME = "Daily Account unlock";
	String ACCOUNT_UNLOCK_TASK_DESC = "This Task will remove all the temporary locks on the Account";
}
