package com.josthi.web.serviceimpl;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Service;

import com.josthi.web.bo.SchedulerTimerBean;
import com.josthi.web.constants.SchedulerConstant;
import com.josthi.web.dao.SchedulerDao;
import com.josthi.web.service.SchedulerService;
import com.josthi.web.service.UserAuthService;

@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService{

	private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
	
	@Autowired
	public SchedulerDao schedulerDao;
	
	@Autowired
	public UserAuthService userAuthService;
	
	@Override
	public void updateSchedulerDetails(SchedulerTimerBean schedulerTimerBean) {
		schedulerDao.updateSchedulerDashBoard(schedulerTimerBean);
		schedulerDao.insertSchedulerHistory(schedulerTimerBean);
		
	}
	
	
	public void runIDRollOverTask(String cronExpression) {
		
		
		SchedulerTimerBean schedulerTimerBean = new SchedulerTimerBean();
		schedulerTimerBean = populateSchedulerBeanForIdRollOver(schedulerTimerBean, cronExpression);
		
		try {
			boolean status = userAuthService.resetUserIdGenTable();
			if(status) {				
				logger.info(" USER ID  GENERATION  TABLE  RESET TO 1");
				schedulerTimerBean.setStatus(SchedulerConstant.SCHEDULER_SUCCESS_STATUS);
			}else {
				logger.error("=== Error Occured while resetting the AutoGen User ID");
				schedulerTimerBean.setStatus(SchedulerConstant.SCHEDULER_FAILURE_STATUS);
				schedulerTimerBean.setErrorMessage(SchedulerConstant.SCHEDULER_EXCEPTION_STATUS);
			}
		}catch(Exception ex) {
			
			logger.error(ex.getMessage(), ex);
			schedulerTimerBean.setStatus(SchedulerConstant.SCHEDULER_FAILURE_STATUS);
			schedulerTimerBean.setErrorMessage(ex.getMessage());
		}finally {
			try {
				updateSchedulerDetails(schedulerTimerBean);
				
			}catch(Exception ex) {
				ex.printStackTrace();
				//Suppress the exception
			}
		}
	}
	
	
	
	
     private SchedulerTimerBean populateSchedulerBeanForIdRollOver(SchedulerTimerBean schedulerTimerBean, String cronExpression) {
		
		schedulerTimerBean.setTaskId(SchedulerConstant.ID_ROLL_OVER_TASK_ID);
		schedulerTimerBean.setTaskName(SchedulerConstant.ID_ROLL_OVER_TASK_NAME);
		schedulerTimerBean.setCronExpresion(cronExpression);
		schedulerTimerBean.setLastRunTime(new Timestamp(System.currentTimeMillis()));
		
		CronSequenceGenerator cronTrigger = new CronSequenceGenerator(cronExpression);
        Date next = cronTrigger.next(new Date());
		schedulerTimerBean.setNextRunTime(new Timestamp(next.getTime()));
		return schedulerTimerBean;
	}


     
     
     
    //**************************************************************************************************/
    //******************** A C C O U N T   U N L O C K   ***********************************************/
    //**************************************************************************************************/
     @Override
	public void midNightAccountUnlock(String cronExpression) {
		SchedulerTimerBean schedulerTimerBean = new SchedulerTimerBean();
		schedulerTimerBean = populateSchedulerBeanForA(schedulerTimerBean, cronExpression);
		try {
			boolean status = userAuthService.removeTempLockFromUserAccount();
			if(status) {				
				logger.info(" USER ACCOUNT UNLOCKED SUCCESSFULLY");
				schedulerTimerBean.setStatus(SchedulerConstant.SCHEDULER_SUCCESS_STATUS);
			}else {
				logger.error("NO DATA TO UPDATE");
				schedulerTimerBean.setStatus(SchedulerConstant.SCHEDULER_SUCCESS_STATUS);
				schedulerTimerBean.setErrorMessage(SchedulerConstant.SCHEDULER_NO_DATA_UPDATE_STATUS);
			}
		}catch(Exception ex) {
			
			logger.error(ex.getMessage(), ex);
			schedulerTimerBean.setStatus(SchedulerConstant.SCHEDULER_FAILURE_STATUS);
			schedulerTimerBean.setErrorMessage(ex.getMessage());
		}finally {
			try {
				updateSchedulerDetails(schedulerTimerBean);
				
			}catch(Exception ex) {
				ex.printStackTrace();
				//Suppress the exception
			}
		}
		
	}


	private SchedulerTimerBean populateSchedulerBeanForA(SchedulerTimerBean schedulerTimerBean, String cronExpression) {
		schedulerTimerBean.setTaskId(SchedulerConstant.ACCOUNT_UNLOCK_TASK_ID);
		schedulerTimerBean.setTaskName(SchedulerConstant.ACCOUNT_UNLOCK_TASK_NAME);
		schedulerTimerBean.setCronExpresion(cronExpression);
		schedulerTimerBean.setLastRunTime(new Timestamp(System.currentTimeMillis()));
		
		CronSequenceGenerator cronTrigger = new CronSequenceGenerator(cronExpression);
        Date next = cronTrigger.next(new Date());
		schedulerTimerBean.setNextRunTime(new Timestamp(next.getTime()));
		return schedulerTimerBean;
	}

}
