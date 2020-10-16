CREATE TABLE `email_queue` (
  `EMAIL_UID` int NOT NULL AUTO_INCREMENT,
  `SENT_TO` varchar(200) NOT NULL,
  `SENT_FROM` varchar(200) NOT NULL,
  `SUBJECT` varchar(500) DEFAULT NULL,
  `JSON_STRING` varchar(2000) DEFAULT NULL,
  `EMAIL_TEMPLATE` varchar(100) DEFAULT NULL,
  `EMAIL_STATUS` varchar(10) DEFAULT 'LOADED',
  `EMAIL_QUEUED_AT` datetime NOT NULL,
  `EMAIL_SENT_AT` datetime DEFAULT NULL,
  `EMAIL_DELIVARY_STATUS` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`EMAIL_UID`)
) COMMENT='This table will have all the email details to be triggered'

CREATE TABLE `email_subscription` (
  `SUBSCRIPTION_ID` int NOT NULL AUTO_INCREMENT,
  `EMAIL_ID` varchar(100) NOT NULL,
  `USER_TYPE` varchar(45) DEFAULT NULL,
  `ACTIVE_USER` varchar(10) DEFAULT 'YES',
  `SUBSCRIBE_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `IS_EMAIL_VALID` varchar(10) DEFAULT 'NO',
  PRIMARY KEY (`SUBSCRIPTION_ID`),
  UNIQUE KEY `EMAIL_ID_UNIQUE` (`EMAIL_ID`)
) 

CREATE TABLE `scheduled_task_dashboard` (
  `TASK_ID` varchar(15) NOT NULL,
  `TASK_NAME` varchar(100) DEFAULT NULL,
  `TASK_DESC` varchar(200) DEFAULT NULL,
  `CRON_EXPRESSION` varchar(45) DEFAULT NULL,
  `LAST_RUN_TIME` datetime DEFAULT NULL,
  `NEXT_RUN_TIME` datetime DEFAULT NULL,
  `TASK_STATUS` varchar(45) DEFAULT NULL,
  `COMMENTS` varchar(200) DEFAULT NULL,
  UNIQUE KEY `TASK_ID_UNIQUE` (`TASK_ID`)
) 


CREATE TABLE `scheduled_task_history` (
  `TASK_ID` int NOT NULL AUTO_INCREMENT,
  `TASK_NAME` varchar(45) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `RUN_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ERROR_MESSAGE` varchar(400) DEFAULT NULL,
  `ACK_BY` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`TASK_ID`),
  UNIQUE KEY `TASK_ID_UNIQUE` (`TASK_ID`)
) 


CREATE TABLE `ui_verbiage` (
  `VERBIAGE_ID` int NOT NULL AUTO_INCREMENT,
  `SCREEN_NAME` varchar(100) NOT NULL,
  `SCREEN_SECTION` varchar(100) NOT NULL,
  `SCREEN_KEY` varchar(100) DEFAULT NULL,
  `VERBIAGE_SHORT_DESC` varchar(500) DEFAULT NULL,
  `VERBIAGE_DETAIL_DESC` varchar(2000) DEFAULT NULL,
  `LAST_UPDATE_TIME_STAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATE_USER` varchar(50) DEFAULT NULL,
  `COMMENTS` varchar(100) DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT 'ACTIVE',
  PRIMARY KEY (`VERBIAGE_ID`)
)  COMMENT='This table contains the Verbiage that are being displayed on various UI screen. these will be stored in session'

CREATE TABLE `user_auth_table` (
  `CUSTOMER_ID` varchar(15) NOT NULL,
  `USERID_EMAIL` varchar(60) NOT NULL,
  `WORDAPP` varchar(200) NOT NULL,
  `REGISTRATION_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `STATUS` varchar(10) NOT NULL DEFAULT 'ACTIVE',
  `LOGIN_TIME` datetime DEFAULT NULL,
  `LOGIN_STATUS` varchar(10) DEFAULT NULL,
  `SECURITY_QUESTION_1` varchar(300) DEFAULT NULL,
  `SECURITY_QUESTION_2` varchar(300) DEFAULT NULL,
  `SECURITY_ANSWER_1` varchar(100) DEFAULT NULL,
  `SECURITY_ANSWER_2` varchar(100) DEFAULT NULL,
  `ROLE` varchar(45) DEFAULT NULL,
  `COMMENTS` varchar(200) DEFAULT NULL,
  `LOGIN_RETRY_COUNT` int DEFAULT '0',
  `TEMPORARY_LOCK_ENABLED` varchar(3) DEFAULT 'NO',
  PRIMARY KEY (`CUSTOMER_ID`),
  UNIQUE KEY `USERID_EMAIL_UNIQUE` (`USERID_EMAIL`)
)  COMMENT='Table containing all the user Auth details.'

CREATE TABLE `user_detail` (
  `UID` varchar(15) NOT NULL,
  `FIRST_NAME` varchar(50) NOT NULL,
  `MIDDLE_NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) NOT NULL,
  `USER_ADDRESS_FIRST_LINE` varchar(100) DEFAULT NULL,
  `USER_ADDRESS_SECOND_LINE` varchar(100) DEFAULT NULL,
  `CITY_TOWN` varchar(45) DEFAULT NULL,
  `STATE` varchar(45) DEFAULT NULL,
  `COUNTY_DISTRICT` varchar(45) DEFAULT NULL,
  `COUNTRY` varchar(45) DEFAULT NULL,
  `MOBILE_NO1` varchar(20) DEFAULT NULL,
  `MOBILE_NO2` varchar(20) DEFAULT NULL,
  `WHATSAPP_NO` varchar(20) DEFAULT NULL,
  `SECONDARY_EMAIL` varchar(45) DEFAULT NULL,
  `EMERGENCY_CONTACT` varchar(20) DEFAULT NULL,
  `WEBSITE` varchar(200) DEFAULT NULL,
  `FACEBOOK_LINK` varchar(200) DEFAULT NULL,
  `INSTAGRAM_LINK` varchar(200) DEFAULT NULL,
  `BENEFICIARY_COUNT` int DEFAULT '0',
  `PHOTO_ID` varchar(45) DEFAULT NULL,
  `PHOTO_PASSPORT_SIZE` blob,
  `PLAN_TYPE_NAME` varchar(45) DEFAULT NULL,
  `PLAN_ACTIVE` varchar(20) DEFAULT 'NO',
  `PLAN_SUBSCRIBE_DATE` datetime DEFAULT NULL,
  `MONTHLY_YEARLY_PLAN` varchar(45) DEFAULT NULL,
  `PLAN_RENUE_DATE` datetime DEFAULT NULL,
  `REMINDER_ENABLED` varchar(10) DEFAULT 'YES',
  `COMMENTS` varchar(200) DEFAULT NULL,
  `USER_STATUS` varchar(45) DEFAULT 'ACTIVE',
  `REGISTRATION_FOR` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`UID`)
) COMMENT='This table will have the details of the registered users'


CREATE TABLE `userid_generation_table` (
  `NextUserID` int NOT NULL,
  PRIMARY KEY (`NextUserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

INSERT INTO josthi_db.userid_generation_table (NextUserID) VALUES 
(1)
;

INSERT INTO josthi_db.scheduled_task_dashboard (TASK_ID,TASK_NAME,TASK_DESC,CRON_EXPRESSION,LAST_RUN_TIME,NEXT_RUN_TIME,TASK_STATUS,COMMENTS) VALUES 
('TSK_001','Daily UID Rollover','This Task will set the default id to 1 every midnight','0 0 0 * * ?','2020-09-25 14:59:50','2020-09-26 00:00:00','COMPLETED',NULL)
,('TSK_002','Daily Account unlock','This Task will remove all the temporary locks on the Account','0 0 0 * * ?','2020-09-25 14:59:51','2020-09-26 00:00:00','COMPLETED',NULL)
,('TSK_003',NULL,NULL,NULL,NULL,NULL,NULL,NULL)
,('TSK_004',NULL,NULL,NULL,NULL,NULL,NULL,NULL)
,('TSK_005',NULL,NULL,NULL,NULL,NULL,NULL,NULL)
;

INSERT INTO josthi_db.ui_verbiage (SCREEN_NAME,SCREEN_SECTION,SCREEN_KEY,VERBIAGE_SHORT_DESC,VERBIAGE_DETAIL_DESC,LAST_UPDATE_TIME_STAMP,LAST_UPDATE_USER,COMMENTS,STATUS) VALUES 
('service_details.html','BasicService','BasicPlan1','Doctor Appointments','It''s a pain to schedule a Dr appointment, we will arrange it for you and send reminder.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','BasicService','BasicPlan2','Medical Diagnostics','We will arrange a preferred lab or nearby lab who can conduct the Medical Diagnostic. Also we will deliver the results at your door step',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','BasicService','BasicPlan3','Arranging Nursing Services','In case of illness nursing service is very essential, we can contact the nearest nursing center and arrange a nursing facility.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','BasicService','BasicPlan4','24X7 Oncall Support','Health Emergency don''t have any time, it''s required any time. We are always there to help. just a single call and we will help you out.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','BasicService','BasicPlan5','Arranging Physiotherapy','Sometimes Physiotherapy is needed for speedy recovery. we can arrange one for you.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','BasicService','BasicPlan6','Medicines Delivery','We can order the Medicines for you, and it will be delivered at your door step.',NULL,'Susovan',NULL,'ACTIVE')
;


INSERT INTO josthi_db.ui_verbiage (SCREEN_NAME,SCREEN_SECTION,SCREEN_KEY,VERBIAGE_SHORT_DESC,VERBIAGE_DETAIL_DESC,LAST_UPDATE_TIME_STAMP,LAST_UPDATE_USER,COMMENTS,STATUS) VALUES 
('service_details.html','EmergencyService','EmergencyPlan1','Ambulance Assistance','Emergency has no time, it can happen any time, we will provide our best support and arrange ambulance.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','EmergencyService','EmergencyPlan2','Non Medical Emergency','Non Medical Emergency need also needs special attention, we will provide service like door lockout, calling fire dept ',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','EmergencyService','EmergencyPlan3','Arranging Hospitalization','it`s difficult get a hospital bed in the time of emergency, we are connected with many hospital and nursing homes. We can arrange a bed for you.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','EmergencyService','EmergencyPlan4','Status Update','We all care for our near and dear ones, we will provide regular status update of the patient to the close relatives.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','EmergencyService','EmergencyPlan5','Providing necessary Contact','During an emergency there are so many things to take case, like medicine, blood bank etc. we can provide all the necessary contacts. We have also on demand services.',NULL,'Susovan',NULL,'ACTIVE')

INSERT INTO josthi_db.ui_verbiage (SCREEN_NAME,SCREEN_SECTION,SCREEN_KEY,VERBIAGE_SHORT_DESC,VERBIAGE_DETAIL_DESC,LAST_UPDATE_TIME_STAMP,LAST_UPDATE_USER,COMMENTS,STATUS) VALUES 
('service_details.html','GeneralService','GeneralPlan1','Sending Notifications', 'Now no need to remember your Dr appointments, We will send you notification before hand.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan2','Hearing aids & Vision aids', 'Having problem hearing or with vision, we will have some one to help with the hearing and vision aids',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan3','Cooked Food Delivery', 'Not able to cook everyday, Don`t worry, we will arrange home delivery service for you.',NULL, 'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan4','Cook Service at Home', 'Don`t like outside food, and waiting for some homely meals, we can arrange cook for your home.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan5','Transportation Arrangement', 'Want to go for a outing, but not able to arrange a transportation facility, we can arrange a transportation for you.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan6','Plumbing, Electrician, carpenter Services', 'Home Appliances out of order!, the kitchen cabinet needs maintenance? , now we can help you arranging some one to help you out.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan7','Grocery Delivery', 'Not able to shop your Grocery regularly, we can help you with the delivery once a week.' ,NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan8','Helping with electronic Devices', 'Don`t know how to setup your whatsapp, don`t know how to setup your wifi on phone? we can help you arranging the service.' ,NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan9','Utility Bills', 'Unable to pay your electric bill in time, don`t to stand in the long queue, we can help you with your bill payments.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan10','Visa & Passport Assistance', 'Want to visit you son or daughter in US, but don`t know how to get the Visa done? he can help you arranging the Visa & Passport, we can arrange for Flight ticket booking as well',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan11','Laundry Services' ,'Don`t like to iron your clothe, We can arrange local Laundry service for you.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan12','Insurance Services' ,'Car insurance expiring soon, don`t know how to renew it? we can arrange an agent who can help you.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan13','Assisted day Outing', 'Want assistance for Doctor`s visit, we can arrange an associate on hourly basis, they can take care of you and safely drop home.',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan14','BirthDay Celebration', 'Birthday is a special moment in everyones`s life. We can arrange a small celebration to make it memorable. ',NULL,'Susovan',NULL,'ACTIVE')
,('service_details.html','GeneralService','GeneralPlan15','Annual Picnic' ,'To bring back the smile, we will arrange a paid picnic once a year. let make every year memorable.' ,NULL,'Susovan',NULL,'ACTIVE')




