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
)

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
) COMMENT='This table contains the Verbiage that are being displayed on various UI screen. these will be stored in session'

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
  `ENABLED` tinyint DEFAULT '1',
  `VERIFIED_USER` varchar(2) NOT NULL DEFAULT 'N',
  `OTP` varchar(45) DEFAULT NULL,
  `VALID_EMAIL` varchar(10) NOT NULL DEFAULT 'NO',
  `OTP_GEN_DATE_TIME` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`CUSTOMER_ID`),
  UNIQUE KEY `USERID_EMAIL_UNIQUE` (`USERID_EMAIL`)
) COMMENT='Table containing all the user Auth details.'


CREATE TABLE `user_detail` (
  `UID` varchar(15) NOT NULL,
  `FIRST_NAME` varchar(50) NOT NULL,
  `MIDDLE_NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) NOT NULL,
  `GENDER` varchar(10) DEFAULT NULL,
  `USER_ADDRESS_FIRST_LINE` varchar(100) DEFAULT NULL,
  `USER_ADDRESS_SECOND_LINE` varchar(100) DEFAULT NULL,
  `ADDITIONAL_ADDRESS_LINE` varchar(100) DEFAULT NULL,
  `POLICE_STATION` varchar(50) DEFAULT NULL,
  `POST_OFFICE` varchar(50) DEFAULT NULL,
  `NEAREST_LAND_MARK` varchar(100) DEFAULT NULL,
  `COVERAGE_AREA` varchar(50) DEFAULT NULL,
  `CITY_TOWN` varchar(45) DEFAULT NULL,
  `STATE` varchar(45) DEFAULT NULL,
  `COUNTY_DISTRICT` varchar(45) DEFAULT NULL,
  `COUNTRY` varchar(45) DEFAULT NULL,
  `ZIP_PIN` varchar(20) DEFAULT NULL,
  `MOBILE_NO1` varchar(20) DEFAULT NULL,
  `MOBILE_NO2` varchar(20) DEFAULT NULL,
  `WHATSAPP_NO` varchar(20) DEFAULT NULL,
  `LAND_LINE_NO` varchar(20) DEFAULT NULL,
  `FAX_NO` varchar(20) DEFAULT NULL,
  `OFFICE_PH_NO` varchar(20) DEFAULT NULL,
  `SECONDARY_EMAIL` varchar(45) DEFAULT NULL,
  `EMERGENCY_CONTACT` varchar(20) DEFAULT NULL,
  `WEBSITE` varchar(200) DEFAULT NULL,
  `FACEBOOK_LINK` varchar(200) DEFAULT NULL,
  `INSTAGRAM_LINK` varchar(200) DEFAULT NULL,
  `BENEFICIARY_COUNT` int DEFAULT '0',
  `PHOTO_ID` varchar(45) DEFAULT NULL,
  `PHOTO_PASSPORT_SIZE` blob,
  `PROFILE_PIC_PATH` varchar(300) DEFAULT NULL,
  `PLAN_TYPE_NAME` varchar(45) DEFAULT NULL,
  `PLAN_ACTIVE` varchar(20) DEFAULT 'NO',
  `PLAN_SUBSCRIBE_DATE` datetime DEFAULT NULL,
  `MONTHLY_YEARLY_PLAN` varchar(45) DEFAULT NULL,
  `PLAN_RENUE_DATE` datetime DEFAULT NULL,
  `REMINDER_ENABLED` varchar(10) DEFAULT 'YES',
  `COMMENTS` varchar(200) DEFAULT NULL,
  `AGENCY_NAME` varchar(100) DEFAULT NULL,
  `AGENCY_DESCRIPTION` varchar(300) DEFAULT NULL,
  `USER_STATUS` varchar(45) DEFAULT 'ACTIVE',
  `REGISTRATION_FOR` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`UID`)
) COMMENT='This table will have the details of the registered users'


CREATE TABLE `userid_generation_table` (
  `NextUserID` int NOT NULL,
  PRIMARY KEY (`NextUserID`)
)

INSERT INTO userid_generation_table (NextUserID) VALUES 
(1)
;

INSERT INTO scheduled_task_dashboard (TASK_ID,TASK_NAME,TASK_DESC,CRON_EXPRESSION,LAST_RUN_TIME,NEXT_RUN_TIME,TASK_STATUS,COMMENTS) VALUES 
('TSK_001','Daily UID Rollover','This Task will set the default id to 1 every midnight','0 0 0 * * ?','2020-09-25 14:59:50','2020-09-26 00:00:00','COMPLETED',NULL)
,('TSK_002','Daily Account unlock','This Task will remove all the temporary locks on the Account','0 0 0 * * ?','2020-09-25 14:59:51','2020-09-26 00:00:00','COMPLETED',NULL)
,('TSK_003',NULL,NULL,NULL,NULL,NULL,NULL,NULL)
,('TSK_004',NULL,NULL,NULL,NULL,NULL,NULL,NULL)
,('TSK_005',NULL,NULL,NULL,NULL,NULL,NULL,NULL)
;


CREATE TABLE `agent_enquery` (
  `ENQUERY_ID` int NOT NULL AUTO_INCREMENT,
  `EMAIL_ID` varchar(60) NOT NULL,
  `AGENT_NAME` varchar(110) NOT NULL,
  `AGENT_QUERY` varchar(400) DEFAULT NULL,
  `AGENT_QUERY_TYPE` varchar(100) DEFAULT NULL,
  `REQUEST_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `QUERY_RESOLVED_ON` datetime DEFAULT NULL,
  `ENQUERY_STATUS` varchar(10) NOT NULL DEFAULT 'OPEN' COMMENT 'If any Agent has any Query, It will be tracked in this Table',
  `AGENT_CONTACT_NO` varchar(20) DEFAULT NULL,
  `AGENT_COVERAGE_AREA` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ENQUERY_ID`)
)

CREATE TABLE `emergency_contact_details` (
  `CONTACT_ID` int NOT NULL AUTO_INCREMENT,
  `PRIMARY_UID` varchar(15) NOT NULL,
  `BEN_ID` varchar(15) DEFAULT NULL,
  `EMERGENCY_CONTACT_NAME` varchar(100) DEFAULT NULL,
  `EMERGENCY_CONTACT_NUMBER` varchar(20) NOT NULL,
  `EMERGENCY_CONTACT_STAY_LOCATION` varchar(100) DEFAULT NULL,
  `RELATION` varchar(50) DEFAULT NULL,
  `CONTACT_NO_VALIDATED` varchar(5) DEFAULT 'NO',
  `INSERT_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `NOTES` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`CONTACT_ID`),
  UNIQUE KEY `INSERT_DATE_UNIQUE` (`INSERT_DATE`)
)

CREATE TABLE `relation` (
  `RELATION_ID` int NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` varchar(15) NOT NULL,
  `BENEFICIARY_ID` varchar(15) DEFAULT NULL,
  `AGENT_ID` varchar(15) DEFAULT NULL,
  `BENEFICIARY_TYPE` varchar(20) DEFAULT NULL,
  `INSERT_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `PLAN_NAME` varchar(200) DEFAULT NULL,
  `PLAN_PRICE_BREAKUP_ID` int DEFAULT NULL,
  `PLAN_EXPIRE_DATE` datetime DEFAULT NULL,
  `PLAN_INVOICE_ID` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`RELATION_ID`)
)

CREATE TABLE `beneficiary_detail` (
  `TID` int NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` varchar(15) DEFAULT NULL,
  `BENEFICIARY_ID` varchar(15) NOT NULL,
  `RELATION_WITH_CUSTOMER` varchar(45) DEFAULT NULL,
  `DATE_OF_BIRTH` datetime DEFAULT NULL,
  `AGE` varchar(20) DEFAULT NULL,
  `HEIGHT` varchar(20) DEFAULT NULL,
  `WEIGHT` varchar(20) DEFAULT NULL,
  `BLOOD_GROUP` varchar(20) DEFAULT NULL,
  `PREFFERED_HOSPITAL` varchar(200) DEFAULT NULL,
  `MEDICLAME_NAME` varchar(200) DEFAULT NULL,
  `INSURANCE_NOTE` varchar(200) DEFAULT NULL,
  `HEALTH_CONDITION` varchar(1000) DEFAULT NULL,
  `MEDICAL_CHALLENGES` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`TID`)
)

CREATE TABLE `dropdown_metadata` (
  `TID` int NOT NULL AUTO_INCREMENT,
  `DROPDOWN_TYPE` varchar(100) NOT NULL,
  `KEY_ID` varchar(300) NOT NULL,
  `DROPDOWN_VALUE` varchar(300) NOT NULL,
  `ACTIVE` varchar(1) NOT NULL DEFAULT 'Y',
  `UPDATE_DATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`TID`)
)

INSERT INTO dropdown_metadata (DROPDOWN_TYPE,KEY_ID,DROPDOWN_VALUE,ACTIVE) VALUES 
('BLOOD_GROUP','O-','O-','Y')
,('BLOOD_GROUP','O+','O+','Y')
,('BLOOD_GROUP','A-','A-','Y')
,('BLOOD_GROUP','A+','A+','Y')
,('BLOOD_GROUP','B-','B-','Y')
,('BLOOD_GROUP','B+','B+','Y')
,('BLOOD_GROUP','AB-','AB-','Y')
,('BLOOD_GROUP','AB+','AB+','Y')
,('BLOOD_GROUP','Others','Others','Y')
,('BLOOD_GROUP','No Idea','No Idea','Y');

INSERT INTO dropdown_metadata (DROPDOWN_TYPE, KEY_ID, DROPDOWN_VALUE, ACTIVE, UPDATE_DATE)
VALUES('Language', 'Bengali', 'Bengali', 'Y', CURRENT_TIMESTAMP),
('Language', 'English', 'English', 'Y', CURRENT_TIMESTAMP),
('Language', 'Hindi', 'Hindi', 'Y', CURRENT_TIMESTAMP),
('Language', 'All', 'All', 'Y', CURRENT_TIMESTAMP);

-- TIME ZONE SCRIPT
INSERT INTO dropdown_metadata (DROPDOWN_TYPE, KEY_ID, DROPDOWN_VALUE, ACTIVE, UPDATE_DATE) VALUES
('TimeZone', '106IST', 'IST ~ Indian Standard Time ~ UTC+05:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '1ACDT', 'ACDT ~ Australian Central Daylight Saving Time ~ UTC+10:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '2ACST', 'ACST ~ Australian Central Standard Time ~ UTC+09:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '3ACT', 'ACT ~ Acre Time ~ UTC−05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '4ACT', 'ACT ~ ASEAN Common Time (unofficial) ~ UTC+06:30 – UTC+09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '5ACWST', 'ACWST ~ Australian Central Western Standard Time (unofficial) ~ UTC+08:45', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '6ADT', 'ADT ~ Atlantic Daylight Time ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '7AEDT', 'AEDT ~ Australian Eastern Daylight Saving Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '8AEST', 'AEST ~ Australian Eastern Standard Time ~ UTC+10', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '9AET', 'AET ~ Australian Eastern Time ~ UTC+10/UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '10AFT', 'AFT ~ Afghanistan Time ~ UTC+04:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '11AKDT', 'AKDT ~ Alaska Daylight Time ~ UTC−08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '12AKST', 'AKST ~ Alaska Standard Time ~ UTC−09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '13ALMT', 'ALMT ~ AlmaAta Time[1] ~ UTC+06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '14AMST', 'AMST ~ Amazon Summer Time (Brazil)[2] ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '15AMT', 'AMT ~ Amazon Time (Brazil)[3] ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '16AMT', 'AMT ~ Armenia Time ~ UTC+04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '17ANAT', 'ANAT ~ Anadyr Time[4] ~ UTC+12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '18AQTT', 'AQTT ~ Aqtobe Time[5] ~ UTC+05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '19ART', 'ART ~ Argentina Time ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '20AST', 'AST ~ Arabia Standard Time ~ UTC+03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '21AST', 'AST ~ Atlantic Standard Time ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '22AWST', 'AWST ~ Australian Western Standard Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '23AZOST', 'AZOST ~ Azores Summer Time ~ UTC±00', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '24AZOT', 'AZOT ~ Azores Standard Time ~ UTC−01', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '25AZT', 'AZT ~ Azerbaijan Time ~ UTC+04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '26BDT', 'BDT ~ Brunei Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '27BIOT', 'BIOT ~ British Indian Ocean Time ~ UTC+06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '28BIT', 'BIT ~ Baker Island Time ~ UTC−12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '29BOT', 'BOT ~ Bolivia Time ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '30BRST', 'BRST ~ Brasília Summer Time ~ UTC−02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '31BRT', 'BRT ~ Brasília Time ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '32BST', 'BST ~ Bangladesh Standard Time ~ UTC+06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '33BST', 'BST ~ Bougainville Standard Time[6] ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '34BST', 'BST ~ British Summer Time (British Standard Time from Feb 1968 to Oct 1971) ~ UTC+01', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '35BTT', 'BTT ~ Bhutan Time ~ UTC+06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '36CAT', 'CAT ~ Central Africa Time ~ UTC+02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '37CCT', 'CCT ~ Cocos Islands Time ~ UTC+06:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '38CDT', 'CDT ~ Central Daylight Time (North America) ~ UTC−05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '39CDT', 'CDT ~ Cuba Daylight Time[7] ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '40CEST', 'CEST ~ Central European Summer Time (Cf. HAEC) ~ UTC+02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '41CET', 'CET ~ Central European Time ~ UTC+01', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '42CHADT', 'CHADT ~ Chatham Daylight Time ~ UTC+13:45', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '43CHAST', 'CHAST ~ Chatham Standard Time ~ UTC+12:45', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '44CHOT', 'CHOT ~ Choibalsan Standard Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '45CHOST', 'CHOST ~ Choibalsan Summer Time ~ UTC+09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '46CHST', 'CHST ~ Chamorro Standard Time ~ UTC+10', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '47CHUT', 'CHUT ~ Chuuk Time ~ UTC+10', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '48CIST', 'CIST ~ Clipperton Island Standard Time ~ UTC−08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '49WITA', 'WITA ~ Central Indonesia Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '50CKT', 'CKT ~ Cook Island Time ~ UTC−10', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '51CLST', 'CLST ~ Chile Summer Time ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '52CLT', 'CLT ~ Chile Standard Time ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '53COST', 'COST ~ Colombia Summer Time ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '54COT', 'COT ~ Colombia Time ~ UTC−05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '55CST', 'CST ~ Central Standard Time (North America) ~ UTC−06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '56CST', 'CST ~ China Standard Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '57CST', 'CST ~ Cuba Standard Time ~ UTC−05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '58CT', 'CT ~ Central Time ~ UTC06/UTC05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '59CVT', 'CVT ~ Cape Verde Time ~ UTC−01', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '60CWST', 'CWST ~ Central Western Standard Time (Australia) unofficial ~ UTC+08:45', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '61CXT', 'CXT ~ Christmas Island Time ~ UTC+07', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '62DAVT', 'DAVT ~ Davis Time ~ UTC+07', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '63DDUT', 'DDUT ~ Dumont dUrville Time ~ UTC+10', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '64DFT', 'DFT ~ AIXspecific equivalent of Central European Time[NB 1] ~ UTC+01', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '65EASST', 'EASST ~ Easter Island Summer Time ~ UTC−05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '66EAST', 'EAST ~ Easter Island Standard Time ~ UTC−06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '67EAT', 'EAT ~ East Africa Time ~ UTC+03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '68ECT', 'ECT ~ Eastern Caribbean Time (does not recognise DST) ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '69ECT', 'ECT ~ Ecuador Time ~ UTC−05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '70EDT', 'EDT ~ Eastern Daylight Time (North America) ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '71EEST', 'EEST ~ Eastern European Summer Time ~ UTC+03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '72EET', 'EET ~ Eastern European Time ~ UTC+02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '73EGST', 'EGST ~ Eastern Greenland Summer Time ~ UTC±00', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '74EGT', 'EGT ~ Eastern Greenland Time ~ UTC−01', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '75WIT', 'WIT ~ Eastern Indonesian Time ~ UTC+09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '76EST', 'EST ~ Eastern Standard Time (North America) ~ UTC−05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '77FET', 'FET ~ Furthereastern European Time ~ UTC+03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '78FJT', 'FJT ~ Fiji Time ~ UTC+12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '79FKST', 'FKST ~ Falkland Islands Summer Time ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '80FKT', 'FKT ~ Falkland Islands Time ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '81FNT', 'FNT ~ Fernando de Noronha Time ~ UTC−02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '82GALT', 'GALT ~ Galápagos Time ~ UTC−06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '83GAMT', 'GAMT ~ Gambier Islands Time ~ UTC−09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '84GET', 'GET ~ Georgia Standard Time ~ UTC+04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '85GFT', 'GFT ~ French Guiana Time ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '86GILT', 'GILT ~ Gilbert Island Time ~ UTC+12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '87GIT', 'GIT ~ Gambier Island Time ~ UTC−09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '88GMT', 'GMT ~ Greenwich Mean Time ~ UTC±00', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '89GST', 'GST ~ South Georgia and the South Sandwich Islands Time ~ UTC−02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '90GST', 'GST ~ Gulf Standard Time ~ UTC+04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '91GYT', 'GYT ~ Guyana Time ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '92HDT', 'HDT ~ Hawaii–Aleutian Daylight Time ~ UTC−09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '93HAEC', 'HAEC ~ Heure Avancée dEurope Centrale Frenchlanguage name for CEST ~ UTC+02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '94HST', 'HST ~ Hawaii–Aleutian Standard Time ~ UTC−10', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '95HKT', 'HKT ~ Hong Kong Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '96HMT', 'HMT ~ Heard and McDonald Islands Time ~ UTC+05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '97HOVST', 'HOVST ~ Hovd Summer Time (not used from 2017present) ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '98HOVT', 'HOVT ~ Hovd Time ~ UTC+07', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '99ICT', 'ICT ~ Indochina Time ~ UTC+07', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '100IDLW', 'IDLW ~ International Day Line West time zone ~ UTC−12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '101IDT', 'IDT ~ Israel Daylight Time ~ UTC+03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '102IOT', 'IOT ~ Indian Ocean Time ~ UTC+03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '103IRDT', 'IRDT ~ Iran Daylight Time ~ UTC+04:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '104IRKT', 'IRKT ~ Irkutsk Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '105IRST', 'IRST ~ Iran Standard Time ~ UTC+03:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '107IST', 'IST ~ Irish Standard Time[8] ~ UTC+01', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '108IST', 'IST ~ Israel Standard Time ~ UTC+02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '109JST', 'JST ~ Japan Standard Time ~ UTC+09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '110KALT', 'KALT ~ Kaliningrad Time ~ UTC+02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '111KGT', 'KGT ~ Kyrgyzstan Time ~ UTC+06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '112KOST', 'KOST ~ Kosrae Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '113KRAT', 'KRAT ~ Krasnoyarsk Time ~ UTC+07', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '114KST', 'KST ~ Korea Standard Time ~ UTC+09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '115LHST', 'LHST ~ Lord Howe Standard Time ~ UTC+10:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '116LHST', 'LHST ~ Lord Howe Summer Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '117LINT', 'LINT ~ Line Islands Time ~ UTC+14', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '118MAGT', 'MAGT ~ Magadan Time ~ UTC+12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '119MART', 'MART ~ Marquesas Islands Time ~ UTC−09:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '120MAWT', 'MAWT ~ Mawson Station Time ~ UTC+05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '121MDT', 'MDT ~ Mountain Daylight Time (North America) ~ UTC−06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '122MET', 'MET ~ Middle European Time Same zone as CET ~ UTC+01', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '123MEST', 'MEST ~ Middle European Summer Time Same zone as CEST ~ UTC+02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '124MHT', 'MHT ~ Marshall Islands Time ~ UTC+12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '125MIST', 'MIST ~ Macquarie Island Station Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '126MIT', 'MIT ~ Marquesas Islands Time ~ UTC−09:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '127MMT', 'MMT ~ Myanmar Standard Time ~ UTC+06:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '128MSK', 'MSK ~ Moscow Time ~ UTC+03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '129MST', 'MST ~ Malaysia Standard Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '130MST', 'MST ~ Mountain Standard Time (North America) ~ UTC−07', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '131MUT', 'MUT ~ Mauritius Time ~ UTC+04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '132MVT', 'MVT ~ Maldives Time ~ UTC+05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '133MYT', 'MYT ~ Malaysia Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '134NCT', 'NCT ~ New Caledonia Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '135NDT', 'NDT ~ Newfoundland Daylight Time ~ UTC−02:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '136NFT', 'NFT ~ Norfolk Island Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '137NOVT', 'NOVT ~ Novosibirsk Time [9] ~ UTC+07', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '138NPT', 'NPT ~ Nepal Time ~ UTC+05:45', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '139NST', 'NST ~ Newfoundland Standard Time ~ UTC−03:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '140NT', 'NT ~ Newfoundland Time ~ UTC−03:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '141NUT', 'NUT ~ Niue Time ~ UTC−11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '142NZDT', 'NZDT ~ New Zealand Daylight Time ~ UTC+13', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '143NZST', 'NZST ~ New Zealand Standard Time ~ UTC+12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '144OMST', 'OMST ~ Omsk Time ~ UTC+06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '145ORAT', 'ORAT ~ Oral Time ~ UTC+05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '146PDT', 'PDT ~ Pacific Daylight Time (North America) ~ UTC−07', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '147PET', 'PET ~ Peru Time ~ UTC−05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '148PETT', 'PETT ~ Kamchatka Time ~ UTC+12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '149PGT', 'PGT ~ Papua New Guinea Time ~ UTC+10', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '150PHOT', 'PHOT ~ Phoenix Island Time ~ UTC+13', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '151PHT', 'PHT ~ Philippine Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '152PKT', 'PKT ~ Pakistan Standard Time ~ UTC+05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '153PMDT', 'PMDT ~ Saint Pierre and Miquelon Daylight Time ~ UTC−02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '154PMST', 'PMST ~ Saint Pierre and Miquelon Standard Time ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '155PONT', 'PONT ~ Pohnpei Standard Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '156PST', 'PST ~ Pacific Standard Time (North America) ~ UTC−08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '157PST', 'PST ~ Philippine Standard Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '158PYST', 'PYST ~ Paraguay Summer Time[10] ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '159PYT', 'PYT ~ Paraguay Time[11] ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '160RET', 'RET ~ Réunion Time ~ UTC+04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '161ROTT', 'ROTT ~ Rothera Research Station Time ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '162SAKT', 'SAKT ~ Sakhalin Island Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '163SAMT', 'SAMT ~ Samara Time ~ UTC+04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '164SAST', 'SAST ~ South African Standard Time ~ UTC+02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '165SBT', 'SBT ~ Solomon Islands Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '166SCT', 'SCT ~ Seychelles Time ~ UTC+04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '167SDT', 'SDT ~ Samoa Daylight Time ~ UTC−10', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '168SGT', 'SGT ~ Singapore Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '169SLST', 'SLST ~ Sri Lanka Standard Time ~ UTC+05:30', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '170SRET', 'SRET ~ Srednekolymsk Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '171SRT', 'SRT ~ Suriname Time ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '172SST', 'SST ~ Samoa Standard Time ~ UTC−11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '173SST', 'SST ~ Singapore Standard Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '174SYOT', 'SYOT ~ Showa Station Time ~ UTC+03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '175TAHT', 'TAHT ~ Tahiti Time ~ UTC−10', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '176THA', 'THA ~ Thailand Standard Time ~ UTC+07', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '177TFT', 'TFT ~ French Southern and Antarctic Time[12] ~ UTC+05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '178TJT', 'TJT ~ Tajikistan Time ~ UTC+05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '179TKT', 'TKT ~ Tokelau Time ~ UTC+13', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '180TLT', 'TLT ~ Timor Leste Time ~ UTC+09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '181TMT', 'TMT ~ Turkmenistan Time ~ UTC+05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '182TRT', 'TRT ~ Turkey Time ~ UTC+03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '183TOT', 'TOT ~ Tonga Time ~ UTC+13', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '184TVT', 'TVT ~ Tuvalu Time ~ UTC+12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '185ULAST', 'ULAST ~ Ulaanbaatar Summer Time ~ UTC+09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '186ULAT', 'ULAT ~ Ulaanbaatar Standard Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '187UTC', 'UTC ~ Coordinated Universal Time ~ UTC±00', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '188UYST', 'UYST ~ Uruguay Summer Time ~ UTC−02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '189UYT', 'UYT ~ Uruguay Standard Time ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '190UZT', 'UZT ~ Uzbekistan Time ~ UTC+05', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '191VET', 'VET ~ Venezuelan Standard Time ~ UTC−04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '192VLAT', 'VLAT ~ Vladivostok Time ~ UTC+10', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '193VOLT', 'VOLT ~ Volgograd Time ~ UTC+04', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '194VOST', 'VOST ~ Vostok Station Time ~ UTC+06', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '195VUT', 'VUT ~ Vanuatu Time ~ UTC+11', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '196WAKT', 'WAKT ~ Wake Island Time ~ UTC+12', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '197WAST', 'WAST ~ West Africa Summer Time ~ UTC+02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '198WAT', 'WAT ~ West Africa Time ~ UTC+01', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '199WEST', 'WEST ~ Western European Summer Time ~ UTC+01', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '200WET', 'WET ~ Western European Time ~ UTC±00', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '201WIB', 'WIB ~ Western Indonesian Time ~ UTC+07', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '202WGST', 'WGST ~ West Greenland Summer Time[13] ~ UTC−02', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '203WGT', 'WGT ~ West Greenland Time[14] ~ UTC−03', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '204WST', 'WST ~ Western Standard Time ~ UTC+08', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '205YAKT', 'YAKT ~ Yakutsk Time ~ UTC+09', 'Y', CURRENT_TIMESTAMP),
('TimeZone', '206YEKT', 'YEKT ~ Yekaterinburg Time ~ UTC+05', 'Y', CURRENT_TIMESTAMP);


INSERT INTO dropdown_metadata (DROPDOWN_TYPE, KEY_ID, DROPDOWN_VALUE, ACTIVE, UPDATE_DATE)
VALUES
('UrgencyType', 'Low', 'Low', 'Y', CURRENT_TIMESTAMP),
('UrgencyType', 'Medium', 'Medium', 'Y', CURRENT_TIMESTAMP),
('UrgencyType', 'High', 'High', 'Y', CURRENT_TIMESTAMP),
('UrgencyType', 'Urgent', 'Urgent', 'Y', CURRENT_TIMESTAMP);


CREATE TABLE `service_request_table` (
  `UID` int NOT NULL AUTO_INCREMENT,
  `TICKET_NO` varchar(15) NOT NULL,
  `REQUESTED_BY` varchar(200) NOT NULL DEFAULT 'CUSTOMER',
  `REQUESTER_ID` varchar(15) NOT NULL,
  `REQUESTED_FOR` varchar(200) NOT NULL,
  `BENEFICIARY_ID` varchar(15) DEFAULT NULL,
  `REQUESTED_VIA` varchar(20) DEFAULT NULL,
  `ASSIGNED_TO` varchar(15) DEFAULT NULL,
  `REQUESTED_ON` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `TO_BE_COMPLETED_BY` datetime DEFAULT NULL,
  `SERVICE_TYPE` varchar(45) DEFAULT NULL,
  `SERVICE_CATEGORY` varchar(200) DEFAULT NULL,
  `SERVICE_REQ_DESCRIPTION` varchar(3000) NOT NULL DEFAULT 'Service Request',
  `SERVICE_URGENCY` varchar(100) DEFAULT NULL,
  `SERVICE_STATUS` varchar(45) DEFAULT NULL,
  `LAST_UPDATE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `COMMENTS` varchar(200) DEFAULT NULL,
  `LAST_UPDATE_COMMENTS` varchar(2000) DEFAULT NULL,
  `LAST_UPDATE_USER` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`UID`),
  UNIQUE KEY `TICKET_NO_UNIQUE` (`TICKET_NO`)
)

CREATE TABLE `user_preferences` (
  `TID` int NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(15) NOT NULL,
  `TIME_ZONE` varchar(200) DEFAULT ' IST ~ Indian Standard Time ~ UTC+05:30 ',
  `PREFERED_LANGUAGE` varchar(45) DEFAULT 'English',
  `PLAN_RENEWAL_REMINDER` tinyint NOT NULL DEFAULT '0',
  `WHATSAPP_NOTIFICATION` tinyint NOT NULL DEFAULT '0',
  `EMAIL_NOTIFICATION_FOR_AD` tinyint DEFAULT '0',
  `LAST_UPDATE_TIMESTAMP` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`TID`),
  UNIQUE KEY `USER_ID_UNIQUE` (`USER_ID`)
)

CREATE TABLE `service_ticket_history` (
  `HISTORY_ID` int NOT NULL AUTO_INCREMENT,
  `TICKET_NUMBER` varchar(15) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `COMMENTS` varchar(2000) DEFAULT NULL,
  `UPDATE_TIMESTAMP` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_BY_NAME` varchar(200) DEFAULT NULL,
  `UPDATED_BY_ID` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`HISTORY_ID`)
)

CREATE TABLE `agency_details` (
  `AID` int NOT NULL AUTO_INCREMENT,
  `AGENT_ID` varchar(15) NOT NULL,
  `AGENCY_NAME` varchar(200) NOT NULL,
  `AGENCY_DESCRIPTION` varchar(1000) DEFAULT NULL,
  `AGENCY_LIC_DETAIL` varchar(200) DEFAULT NULL,
  `VERIFICATION_DOC` varchar(1000) DEFAULT NULL,
  `DOC_PATH` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`AID`),
  UNIQUE KEY `AGENT_ID_UNIQUE` (`AGENT_ID`)
) 

INSERT INTO dropdown_metadata (DROPDOWN_TYPE, KEY_ID, DROPDOWN_VALUE, ACTIVE, UPDATE_DATE)
VALUES('TicketStatus', 'Pre-Paid Service', 'Pre-Paid Service', 'Y', CURRENT_TIMESTAMP)

CREATE TABLE `activity_history_table` (
  `ACTIVITY_ID` int NOT NULL AUTO_INCREMENT,
  `ACTIVITY_FOR` varchar(15) DEFAULT NULL,
  `ACTIVITY_BY` varchar(15) DEFAULT NULL,
  `ACTIVITY_DATE_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `ACTIVITY_NOTE` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`ACTIVITY_ID`)
)



INSERT INTO dropdown_metadata (DROPDOWN_TYPE, KEY_ID, DROPDOWN_VALUE, ACTIVE, UPDATE_DATE)
VALUES
('ServiceTypeOnDemand', 'On Demand Basic Service', 'On Demand Basic Service', 'Y', CURRENT_TIMESTAMP),
('ServiceTypeOnDemand', 'On Demand Emergency Service', 'On Demand Emergency Service', 'Y', CURRENT_TIMESTAMP),
('ServiceTypeOnDemand', 'On Demand General Service', 'On Demand General Service', 'Y', CURRENT_TIMESTAMP),
('ServiceTypePlanGold', 'Pre-Paid Gold Plan', 'Pre-Paid Gold Plan', 'Y', CURRENT_TIMESTAMP),
('ServiceTypePlanSilver', 'Pre-Paid Silver Plan', 'Pre-Paid Silver Plan', 'Y', CURRENT_TIMESTAMP),
('ServiceTypePlanBasic', 'Pre-Paid Basic Plan', 'Pre-Paid Basic Plan', 'Y', CURRENT_TIMESTAMP),
('FamailyPlanType', '1BEN', 'Single Beneficiary', 'Y', CURRENT_TIMESTAMP),
('FamailyPlanType', '2BEN', 'Family Plan (2 Beneficiary)', 'Y', CURRENT_TIMESTAMP),
('FamailyPlanType', '3BEN', 'Family Plan (3 Beneficiary)', 'Y', CURRENT_TIMESTAMP);




--Not in use
--create table josthi_db.PLAN_TO_OFFER (
/*PLAN_NAME VARCHAR(100) NOT NULL,
DESCRIPTION VARCHAR(255) default null,
ACTIVE CHAR(1) default 'Y',
primary key (PLAN_NAME)
)COMMENT='List of plans to be offered';*/

--not in use
/*insert into PLAN_TO_OFFER (PLAN_NAME, DESCRIPTION)
VALUES('Basic','Economic Plan, covering just the basic service.'),
('Silver','Plan with essential Services preferred by many customer.'),
('Gold','Plan to fulfill all your needs, Best plan preferred by customers.')*/


CREATE TABLE service_type (
  ID int NOT NULL AUTO_INCREMENT,
  TYPE_KEY varchar(100) NOT NULL,
  TYPE_DESCRIPTION varchar(255) DEFAULT NULL,
  ACTIVE char(1) DEFAULT 'Y',
  CATEGORY varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY TYPE_KEY_UNIQUE (TYPE_KEY)
) COMMENT='List of service types (Medical/general/Misc)'

INSERT INTO service_type
(ID, TYPE_KEY, TYPE_DESCRIPTION, ACTIVE, CATEGORY)
VALUES(1, 'ODBASIC', 'On Demand - Basic', 'Y', 'OnDemand');
INSERT INTO service_type
(ID, TYPE_KEY, TYPE_DESCRIPTION, ACTIVE, CATEGORY)
VALUES(2, 'ODEMERGENCY', 'On Demand - Emergency', 'Y', 'OnDemand');
INSERT INTO service_type
(ID, TYPE_KEY, TYPE_DESCRIPTION, ACTIVE, CATEGORY)
VALUES(3, 'ODGENERAL', 'On Demand - General', 'Y', 'OnDemand');
INSERT INTO service_type
(ID, TYPE_KEY, TYPE_DESCRIPTION, ACTIVE, CATEGORY)
VALUES(4, 'PLANBASIC', 'Pre-Paid Basic Plan', 'Y', 'PlanBasic');
INSERT INTO service_type
(ID, TYPE_KEY, TYPE_DESCRIPTION, ACTIVE, CATEGORY)
VALUES(5, 'PLANSILVER', 'Pre-Paid Silver Plan', 'Y', 'PlanSilver');
INSERT INTO service_type
(ID, TYPE_KEY, TYPE_DESCRIPTION, ACTIVE, CATEGORY)
VALUES(6, 'PLANGOLD', 'Pre-Paid Gold Plan', 'Y', 'PlanGold');


CREATE TABLE service (
  ID int NOT NULL AUTO_INCREMENT,
  SERVICE_CODE varchar(20) NOT NULL,
  SERVICE_NAME varchar(400) NOT NULL,
  DESCRIPTION varchar(2000) DEFAULT NULL,
  ACTIVE char(1) DEFAULT 'Y',
  SERVICE_TYPE varchar(100) NOT NULL,
  INCLUDED_IN_PLAN varchar(6) DEFAULT 'NNNNNN',
  ON_DEMAND_FLAG char(1) DEFAULT 'Y',
  ON_DEMANT_PRICE_INR decimal(10,2) DEFAULT '0.00',
  ON_DEMANT_PRICE_USD decimal(10,2) DEFAULT '0.00',
  DISCLAIMER varchar(1000) DEFAULT NULL,
  SORT_ORDER int DEFAULT NULL,
  ICON varchar(100) DEFAULT 'images/josthi/pricing/pin_2.png',
  PRIMARY KEY (ID),
  UNIQUE KEY SERVICE_CODE_UNIQUE (SERVICE_CODE),
  KEY IX_SERVICE_NAME (SERVICE_NAME)
) COMMENT='Master table of all services to be offered'


CREATE TABLE purchase_history (
  PURCHASE_ID_TKT varchar(20) NOT NULL,
  PURCHASE_ITEM varchar(100) DEFAULT NULL,
  PURCHASE_DETAILS varchar(200) DEFAULT NULL,
  PURCHASE_DATE datetime DEFAULT NULL,
  PAYMENT_STATUS varchar(45) DEFAULT NULL,
  PAYMENT_INVOICE_ID varchar(45) DEFAULT NULL,
  PRICE_IN_USD varchar(45) DEFAULT NULL,
  PRICE_IN_INR varchar(45) DEFAULT NULL,
  TX_BY varchar(15) NOT NULL,
  UNIQUE KEY PURCHASE_ID_UNIQUE (PURCHASE_ID_TKT),
  KEY PURCHASE_ID_TKT_INDX (PURCHASE_ID_TKT)
) 
  
/*CREATE TABLE service_type (
  ID int NOT NULL AUTO_INCREMENT,
  TYPE_KEY varchar(100) NOT NULL,
  TYPE_DESCRIPTION varchar(255) DEFAULT NULL,
  ACTIVE char(1) DEFAULT 'Y',
  CATEGORY varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE KEY TYPE_KEY_UNIQUE (TYPE_KEY)
) COMMENT='List of service types (Medical/general/Misc)'

INSERT INTO josthi_db.service_type
(TYPE_KEY, TYPE_DESCRIPTION, ACTIVE, CATEGORY)
VALUES('ODBASIC', 'On Demand - Basic', 'Y', 'OnDemand'),
('ODEMERGENCY', 'On Demand - Emergency', 'Y', 'OnDemand'),
('ODGENERAL', 'On Demand - General', 'Y', 'OnDemand'),
('PLANBASIC', 'Pre-Paid Basic Plan', 'Y', 'PlanBasic'),
('PLANSILVER', 'Pre-Paid Silver Plan', 'Y', 'PlanSilver'),
('PLANGOLD', 'Pre-Paid Gold Plan', 'Y', 'PlanGold');*/


/*INSERT INTO service
(SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER)
VALUES
('BS001','Doctor Appointments', 'It`s a pain to schedule a Dr appointment, we will arrange it for you and send reminder.', 'Y', 'BasicService', 'YYYNNN', 'Y', 500.00, 10.00,'NB: The price inclused only setting the appointment, The Doctor`s fee has to be paid by the customer.' , 10),
('BS001','Medical Diagnostics', 'We will arrange a preferred lab or nearby lab who can conduct the Medical Diagnostic. Also we will deliver the results at your door step', 'Y', 'BasicService', 'YYYNNN', 'Y', 500.00, 10.00,'The price includes setting up an appointment and deliver the report at door step. Lab fee and tax are not included.', 20),
('BS001','Arranging Nursing Services', 'In case of illness nursing service is very essential, we can contact the nearest nursing center and arrange a nursing facility.', 'Y', 'BasicService', 'YYYNNN', 'Y', 200.00, 3.00,'The price includes just arranging a nurse, the charge for the service will have to be paid by the customer.' ,30),
('BS001','24X7 Oncall Support', 'Health Emergency don`t have any time, it`s required any time. We are always there to help. just a single call and we will help you out.', 'Y', 'BasicService', 'YYYNNN', 'N', 0.00, 0.00,'We are happy to help during emergency, but there has to be some relatives/friends for hospitalization, This is just for security and safety.', 40),
('BS001','Arranging Physiotherapy', 'Sometimes Physiotherapy is needed for speedy recovery. we can arrange one for you.', 'Y', 'BasicService', 'NYYNNN', 'Y', 200.00, 3.00,'The price includes only arranging the Physiotherapist, physiotherapy fee has to be paid by the customer.', 50),
('BS001','Medicines Delivery', 'We can order the Medicines for you, and it will be delivered at your door step.', 'Y', 'BasicService', 'NYYNNN', 'Y', 200.00, 3.00,'The price includes only', 60),
('ES001','Ambulance Assistance', 'Emergency has no time, it can happen any time, we will provide our best support and arrange ambulance.', 'Y', 'EmergencyService', 'YYYNNN', 'N', 200.00, 3.00,'The price includes only', 70),
('ES001','Non Medical Emergency', 'Non Medical Emergency need also needs special attention, we will provide service like door lockout, calling fire dept ', 'Y', 'EmergencyService', 'NYYNNN', 'N', 200.00, 3.00,'The price includes only', 80),
('ES001','Arranging Hospitalization', 'it`s difficult get a hospital bed in the time of emergency, we are connected with many hospital and nursing homes. We can arrange a bed for you.', 'Y', 'EmergencyService', 'YYYNNN', 'N', 200.00, 3.00,'The price includes only', 90),
('ES001','Status Update', 'We all care for our near and dear ones, we will provide regular status update of the patient to the close relatives.', 'Y', 'EmergencyService', 'NNNNNN', 'N', 200.00, 3.00,'The price includes only', 100),
('ES001','Providing necessary Contact', 'During an emergency there are so many things to take case, like medicine, blood bank etc. we can provide all the necessary contacts. We have also on demand services.', 'Y', 'EmergencyService', 'NNNNNN', 'N', 200.00, 3.00,'The price includes only', 110),
('GS001','Sending Notifications', 'Now no need to remember your Dr appointments, We will send you notification before hand.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 120),
('GS001','Hearing aids & Vision aids', 'Having problem hearing or with vision, we will have some one to help with the hearing and vision aids', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 130),
('GS001','Cooked Food Delivery', 'Not able to cook everyday, Don`t worry, we will arrange home delivery service for you.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 140),
('GS001','Cook Service at Home', 'Don`t like outside food, and waiting for some homely meals, we can arrange cook for your home.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 150),
('GS001','Transportation Arrangement', 'Want to go for a outing, but not able to arrange a transportation facility, we can arrange a transportation for you.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00, 'The price includes only', 160),
('GS001','Plumbing, Electrician, carpenter Services', 'Home Appliances out of order!, the kitchen cabinet needs maintenance? , now we can help you arranging some one to help you out.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 170),
('GS001','Grocery Delivery', 'Not able to shop your Grocery regularly, we can help you with the delivery once a week.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 180),
('GS001','Helping with electronic Devices', 'Don`t know how to setup your whatsapp, don`t know how to setup your wifi on phone? we can help you arranging the service.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 190),
('GS001','Utility Bills', 'Unable to pay your electric bill in time, don`t to stand in the long queue, we can help you with your bill payments.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 200),
('GS001','Visa & Passport Assistance', 'Want to visit you son or daughter in US, but don`t know how to get the Visa done? he can help you arranging the Visa & Passport, we can arrange for Flight ticket booking as well', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 210),
('GS001','Laundry Services', 'Don`t like to iron your clothe, We can arrange local Laundry service for you.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 220),
('GS001','Insurance Services', 'Car insurance expiring soon, don`t know how to renew it? we can arrange an agent who can help you.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 230),
('GS001','Assisted day Outing', 'Want assistance for Doctor`s visit, we can arrange an associate on hourly basis, they can take care of you and safely drop home.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 240),
('GS001','BirthDay Celebration', 'Birthday is a special moment in everyones`s life. We can arrange a small celebration to make it memorable. ', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 250),
('GS001','Annual Picnic', 'To bring back the smile, we will arrange a paid picnic once a year. let make every year memorable.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 260),
('GS001','Puja Services', 'We know your parents always pray for all, but there are situations where they might not be able to go to temple physically, we can help and offer puja on behalf of them.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 270),
('GS001','Postal Service', 'Want to parcel something, but not able to go physically. We can help with these postal services.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 280),
('GS001','Event Arrahgement', 'Want to have a small party? we can arrange a party for you.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00,'The price includes only', 290);
*/


INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(1, 'BS001', 'Doctor Appointments', 'It`s a pain to schedule a Dr appointment, we will arrange it for you and send reminder.', 'Y', 'BasicService', 'YYYNNN', 'Y', 500.00, 10.00, 'NB: The price inclused only setting the appointment, The Doctor`s fee has to be paid by the customer.', 10, 'images/josthi/pricing/Doctor_Appointments.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(2, 'BS002', 'Medical Diagnostics', 'We will arrange a preferred lab or nearby lab who can conduct the Medical Diagnostic. Also we will deliver the results at your door step', 'Y', 'BasicService', 'YYYNNN', 'Y', 500.00, 10.00, 'The price includes setting up an appointment and deliver the report at door step. Lab fee and tax are not included.', 20, 'images/josthi/pricing/Medical_Diagnostics.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(3, 'BS003', 'Arranging Nursing Services', 'In case of illness nursing service is very essential, we can contact the nearest nursing center and arrange a nursing facility.', 'Y', 'BasicService', 'YYYNNN', 'Y', 200.00, 3.00, 'The price includes just arranging a nurse, the charge for the service will have to be paid by the customer.', 30, 'images/josthi/pricing/Nursing_Services.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(4, 'BS004', '24X7 Oncall Support', 'Health Emergency don`t have any time, it`s required any time. We are always there to help. just a single call and we will help you out.', 'Y', 'BasicService', 'YYYNNN', 'N', 0.00, 0.00, 'We are happy to help during emergency, but there has to be some relatives/friends for hospitalization, This is just for security and safety.', 40, 'images/josthi/pricing/24X7_service.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(5, 'BS005', 'Arranging Physiotherapy', 'Sometimes Physiotherapy is needed for speedy recovery. we can arrange one for you.', 'Y', 'BasicService', 'NYYNNN', 'Y', 200.00, 3.00, 'The price includes only arranging the Physiotherapist, physiotherapy fee has to be paid by the customer.', 50, 'images/josthi/pricing/Arranging_Physiotherapy.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(6, 'BS006', 'Medicines Delivery', 'We can order the Medicines for you, and it will be delivered at your door step.', 'Y', 'BasicService', 'NYYNNN', 'Y', 200.00, 3.00, 'The price includes only', 60, 'images/josthi/pricing/Medicines_Delivery.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(7, 'BS007', 'Ambulance Assistance', 'Emergency has no time, it can happen any time, we will provide our best support and arrange ambulance.', 'Y', 'EmergencyService', 'YYYNNN', 'N', 200.00, 3.00, 'The price includes only', 70, 'images/josthi/pricing/Ambulance_Assistance.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(8, 'ES001', 'Non Medical Emergency', 'Non Medical Emergency need also needs special attention, we will provide service like door lockout, calling fire dept ', 'Y', 'EmergencyService', 'NYYNNN', 'N', 200.00, 3.00, 'The price includes only', 80, 'images/josthi/pricing/Non_Medical_Emergency.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(9, 'ES002', 'Arranging Hospitalization', 'it`s difficult get a hospital bed in the time of emergency, we are connected with many hospital and nursing homes. We can arrange a bed for you.', 'Y', 'EmergencyService', 'YYYNNN', 'N', 200.00, 3.00, 'The price includes only', 90, 'images/josthi/pricing/Arranging_Hospitalization.JPG');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(10, 'ES003', 'Status Update', 'We all care for our near and dear ones, we will provide regular status update of the patient to the close relatives.', 'Y', 'EmergencyService', 'NYYNNN', 'N', 200.00, 3.00, 'The price includes only', 100, 'images/josthi/pricing/Status_Update.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(11, 'ES004', 'Providing necessary Contact', 'During an emergency there are so many things to take case, like medicine, blood bank etc. we can provide all the necessary contacts. We have also on demand services.', 'Y', 'EmergencyService', 'NNYNNN', 'N', 200.00, 3.00, 'The price includes only', 110, 'images/josthi/pricing/Providing_Contact.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(12, 'GS001', 'Sending Notifications', 'Now no need to remember your Dr appointments, We will send you notification before hand.', 'Y', 'GeneralService', 'NNYNNN', 'N', 200.00, 3.00, 'The price includes only', 120, 'images/josthi/pricing/Sending_Notifications.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(13, 'GS002', 'Hearing aids & Vision aids', 'Having problem hearing or with vision, we will have some one to help with the hearing and vision aids', 'Y', 'GeneralService', 'NYYNNN', 'Y', 200.00, 3.00, 'The price includes only', 130, 'images/josthi/pricing/Hearing_aids.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(14, 'GS003', 'Cooked Food Delivery', 'Not able to cook everyday, Don`t worry, we will arrange home delivery service for you.', 'Y', 'GeneralService', 'NYYNNN', 'Y', 200.00, 3.00, 'The price includes only', 140, 'images/josthi/pricing/Cooked_Food_Delivery.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(15, 'GS004', 'Cook Service at Home', 'Don`t like outside food, and waiting for some homely meals, we can arrange cook for your home.', 'Y', 'GeneralService', 'NNYNNN', 'Y', 200.00, 3.00, 'The price includes only', 150, 'images/josthi/pricing/Cook_Service.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(16, 'GS005', 'Transportation Arrangement', 'Want to go for a outing, but not able to arrange a transportation facility, we can arrange a transportation for you.', 'Y', 'GeneralService', 'NNYNNN', 'Y', 200.00, 3.00, 'The price includes only', 160, 'images/josthi/pricing/Transportation_Arrangement.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(17, 'GS006', 'Plumbing, Electrician, carpenter Services', 'Home Appliances out of order!, the kitchen cabinet needs maintenance? , now we can help you arranging some one to help you out.', 'Y', 'GeneralService', 'NNYNNN', 'Y', 200.00, 3.00, 'The price includes only', 170, 'images/josthi/pricing/Plumbing_Electrician.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(18, 'GS007', 'Grocery Delivery', 'Not able to shop your Grocery regularly, we can help you with the delivery once a week.', 'Y', 'GeneralService', 'NNYNNN', 'Y', 200.00, 3.00, 'The price includes only', 180, 'images/josthi/pricing/Grocery_Delivery.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(19, 'GS008', 'Helping with electronic Devices', 'Don`t know how to setup your whatsapp, don`t know how to setup your wifi on phone? we can help you arranging the service.', 'Y', 'GeneralService', 'NNYNNN', 'Y', 200.00, 3.00, 'The price includes only', 190, 'images/josthi/pricing/electronic_Devices.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(20, 'GS009', 'Utility Bills', 'Unable to pay your electric bill in time, don`t to stand in the long queue, we can help you with your bill payments.', 'Y', 'GeneralService', 'NNYNNN', 'Y', 200.00, 3.00, 'The price includes only', 200, 'images/josthi/pricing/Utility_Bills.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(21, 'GS010', 'Visa & Passport Assistance', 'Want to visit you son or daughter in US, but don`t know how to get the Visa done? he can help you arranging the Visa & Passport, we can arrange for Flight ticket booking as well', 'Y', 'GeneralService', 'NNYNNN', 'Y', 200.00, 3.00, 'The price includes only', 210, 'images/josthi/pricing/Visa_Passport.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(22, 'GS011', 'Laundry Services', 'Don`t like to iron your clothe, We can arrange local Laundry service for you.', 'Y', 'GeneralService', 'NNYNNN', 'Y', 200.00, 3.00, 'The price includes only', 220, 'images/josthi/pricing/Laundry.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(23, 'GS012', 'Insurance Services', 'Car insurance expiring soon, don`t know how to renew it? we can arrange an agent who can help you.', 'Y', 'GeneralService', 'NNYNNN', 'Y', 200.00, 3.00, 'The price includes only', 230, 'images/josthi/pricing/Insurance.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(24, 'GS013', 'Assisted day Outing', 'Want assistance for Doctor`s visit, we can arrange an associate on hourly basis, they can take care of you and safely drop home.', 'Y', 'GeneralService', 'NNYNNN', 'Y', 200.00, 3.00, 'The price includes only', 240, 'images/josthi/pricing/Assisted_day_Outing.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(25, 'GS014', 'BirthDay Celebration', 'Birthday is a special moment in everyones`s life. We can arrange a small celebration to make it memorable. ', 'Y', 'GeneralService', 'NNYNNN', 'N', 200.00, 3.00, 'The price includes only', 250, 'images/josthi/pricing/BirthDay.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(26, 'GS015', 'Annual Picnic', 'To bring back the smile, we will arrange a paid picnic once a year. let make every year memorable.', 'Y', 'GeneralService', 'NNYNNN', 'N', 200.00, 3.00, 'The price includes only', 260, 'images/josthi/pricing/Annual_Picnic.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(27, 'GS016', 'Puja Services', 'We know your parents always pray for all, but there are situations where they might not be able to go to temple physically, we can help and offer puja on behalf of them.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00, 'The price includes only', 270, 'images/josthi/pricing/Puja_Services.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(28, 'GS017', 'Postal Service', 'Want to parcel something, but not able to go physically. We can help with these postal services.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00, 'The price includes only', 280, 'images/josthi/pricing/Postal_Service.jpg');
INSERT INTO service
(ID, SERVICE_CODE, SERVICE_NAME, DESCRIPTION, ACTIVE, SERVICE_TYPE, INCLUDED_IN_PLAN, ON_DEMAND_FLAG, ON_DEMANT_PRICE_INR, ON_DEMANT_PRICE_USD, DISCLAIMER, SORT_ORDER, ICON)
VALUES(29, 'GS018', 'Event Arrangement', 'Want to have a small party? we can arrange a party for you.', 'Y', 'GeneralService', 'NNNNNN', 'Y', 200.00, 3.00, 'The price includes only', 290, 'images/josthi/pricing/Event_Arrangement.jpg');



--XXXXXXXXXXXXXXXXXXXXX P L A N XXXXXXXXXXXXXXXXXXXXX


CREATE TABLE plan_type (
  ID int NOT NULL AUTO_INCREMENT,
  PLAN_TYPE_ID varchar(10) NOT NULL,
  PLAN_NAME varchar(100) NOT NULL,
  DESCRIPTION varchar(255) DEFAULT NULL,
  ACTIVE char(1) DEFAULT 'Y',
  PRIMARY KEY (PLAN_TYPE_ID),
  UNIQUE KEY ID_UNIQUE (ID),
  KEY IX_PLAN_NAME (PLAN_NAME)
) COMMENT='List of plans to be offered'

INSERT INTO plan_type (PLAN_TYPE_ID, PLAN_NAME, DESCRIPTION, ACTIVE)
VALUES('Basic', 'Basic Plan', 'Economic Plan, covering just the basic service.', 'Y'),
('Gold', 'Gold Plan', 'Plan with all most all Services. Best plan ever.', 'Y'),
('Silver', 'Silver Plan', 'Plan to fulfill all your essential needs in India. Good plan.', 'Y')

CREATE TABLE plan_discount_on_duration (
  ID int NOT NULL AUTO_INCREMENT,
  DISCOUNT_ID varchar(10) NOT NULL,
  DURATION_NAME varchar(50) NOT NULL,
  MULTIPLICATION_FACTOR int NOT NULL,
  DICSOUNT_PERCENTAGE int NOT NULL,
  ACTIVE varchar(1) DEFAULT 'Y',
  PRIMARY KEY (DISCOUNT_ID),
  UNIQUE KEY DISCOUNT_ID_UNIQUE (ID)
) COMMENT='Multiplication Factor is the base Price, Discount in Percent.'

INSERT INTO plan_discount_on_duration
(ID, DISCOUNT_ID, DURATION_NAME, MULTIPLICATION_FACTOR, DICSOUNT_PERCENTAGE, ACTIVE)
VALUES(1, '30DAY', 'Monthly Plan (30 days)', 1, 0, 'Y');
INSERT INTO plan_discount_on_duration
(ID, DISCOUNT_ID, DURATION_NAME, MULTIPLICATION_FACTOR, DICSOUNT_PERCENTAGE, ACTIVE)
VALUES(3, '90DAY', '3 Months Plan (90 Days)', 3, 5, 'Y');
INSERT INTO plan_discount_on_duration
(ID, DISCOUNT_ID, DURATION_NAME, MULTIPLICATION_FACTOR, DICSOUNT_PERCENTAGE, ACTIVE)
VALUES(4, '180DAY', '6 Months Plan(180 Days)', 6, 10, 'Y');
INSERT INTO plan_discount_on_duration
(ID, DISCOUNT_ID, DURATION_NAME, MULTIPLICATION_FACTOR, DICSOUNT_PERCENTAGE, ACTIVE)
VALUES(5, '365DAY', '1 Year Plan (365 Days)', 12, 20, 'Y');


CREATE TABLE plan_price_detail (
  ID int NOT NULL AUTO_INCREMENT,
  PLAN_TYPE_ID varchar(10) NOT NULL,
  PRICE_FOR_1_PERSON decimal(10,2) DEFAULT NULL,
  PRICE_FOR_2_PERSON decimal(10,2) DEFAULT NULL,
  PRICE_FOR_3_PERSON decimal(10,2) DEFAULT NULL,
  CURRENCY varchar(3) DEFAULT NULL,
  ACTIVE char(1) DEFAULT 'Y',
  PRICE_UPDATED_ON datetime DEFAULT NULL,
  PRIMARY KEY (ID),
  KEY FK_PLAN_PRICE_ID (PLAN_TYPE_ID),
  CONSTRAINT FK_PLAN_PRICE_ID FOREIGN KEY (PLAN_TYPE_ID) REFERENCES plan_type (PLAN_TYPE_ID)
) COMMENT='Pricing master table for plans'

INSERT INTO plan_price_detail
(ID, PLAN_TYPE_ID, PRICE_FOR_1_PERSON, PRICE_FOR_2_PERSON, PRICE_FOR_3_PERSON, CURRENCY, ACTIVE, PRICE_UPDATED_ON)
VALUES(1, 'Basic', 1000.00, 1700.00, 2500.00, 'INR', 'Y', NULL);
INSERT INTO plan_price_detail
(ID, PLAN_TYPE_ID, PRICE_FOR_1_PERSON, PRICE_FOR_2_PERSON, PRICE_FOR_3_PERSON, CURRENCY, ACTIVE, PRICE_UPDATED_ON)
VALUES(2, 'Silver', 1500.00, 2500.00, 3700.00, 'INR', 'Y', NULL);
INSERT INTO plan_price_detail
(ID, PLAN_TYPE_ID, PRICE_FOR_1_PERSON, PRICE_FOR_2_PERSON, PRICE_FOR_3_PERSON, CURRENCY, ACTIVE, PRICE_UPDATED_ON)
VALUES(3, 'Gold', 2500.00, 4000.00, 6000.00, 'INR', 'Y', NULL);


CREATE TABLE price_breakup_offer (
  OFFER_ID int NOT NULL AUTO_INCREMENT,
  PLAN_START_DATE datetime NOT NULL,
  PLAN_END_DATE datetime NOT NULL,
  PLAN_NAME varchar(45) NOT NULL,
  PLAN_DURATION_CODE varchar(20) NOT NULL,
  PLAN_BENEFICIARY_COUNT_CODE varchar(20) DEFAULT NULL,
  BREAKUP_REQUESTED_BY varchar(15) DEFAULT NULL,
  BREAKUP_REQUESTED_FOR varchar(100) DEFAULT NULL,
  PLAN_PRICE_PER_PERSON_PER_MONTH varchar(20) DEFAULT NULL,
  BENEFECICIARY_COUNT_TO_DISPLAY varchar(100) DEFAULT NULL,
  BASE_PRICE_FOR_TOTAL_BENEFECIARY varchar(20) DEFAULT NULL,
  DISCOUNTED_PRICE_FOR_TOTAL_BENEFECIARY varchar(20) DEFAULT NULL,
  PLAN_DURATION_TO_DISPLAY varchar(20) DEFAULT NULL,
  DISCOUNT_PERCENTAGE_FOR_FAMILY_PLAN varchar(20) DEFAULT NULL,
  BASE_PRICE_FOR_SELECTED_DURATION varchar(20) DEFAULT NULL,
  DISCOUNTED_PRICE_FOR_SELECTED_DURATION varchar(20) DEFAULT NULL,
  FINAL_DISCOUNTED_PRICE varchar(20) DEFAULT NULL,
  TOTAL_GAIN varchar(45) DEFAULT NULL,
  BREAKUP_CREATED_ON datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  IS_PLAN_PURCHASED varchar(1) NOT NULL DEFAULT 'N',
  FAMILY_DISCOUNT decimal(10,2) DEFAULT NULL,
  LONG_TERM_DISCOUNT decimal(10,2) DEFAULT NULL,
  NON_DISCOUNTED_PRICE decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (OFFER_ID)
) 
  
  
  
CREATE TABLE agentfeedback (
  FID int NOT NULL AUTO_INCREMENT,
  FEEDBACK_FOR varchar(200) DEFAULT NULL,
  FEEDBACK_BY varchar(20) DEFAULT NULL,
  PROACTIVENESS decimal(10,2) NOT NULL DEFAULT '0.00',
  RESPONSIBILITY decimal(10,2) NOT NULL DEFAULT '0.00',
  AVAILABILITY decimal(10,2) NOT NULL DEFAULT '0.00',
  BEHAVIOR decimal(10,2) NOT NULL DEFAULT '0.00',
  CARE_AND_HANDLING decimal(10,2) NOT NULL DEFAULT '0.00',
  OVERALL_RATING decimal(10,2) NOT NULL DEFAULT '0.00',
  AREA_OF_IMPROVEMENT varchar(1000) DEFAULT NULL,
  OTHER_COMMENTS varchar(500) DEFAULT NULL,
  FEEDBACK_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (FID)
)
  
  
  
CREATE TABLE josthi_feedback (
  FID int NOT NULL AUTO_INCREMENT,
  FEEDBACK_BY varchar(20) DEFAULT NULL,
  SUGGESTIONS_NOTES varchar(500) DEFAULT NULL,
  FEEDBACK_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  ACTION_TAKEN varchar(100) DEFAULT NULL,
  PRIMARY KEY (FID)
)
  
  
CREATE TABLE customer_enquire (
  ENQID int NOT NULL AUTO_INCREMENT,
  CUSTOMER_NAME varchar(200) DEFAULT NULL,
  CUSTOMER_EMAIL varchar(200) DEFAULT NULL,
  MESSAGE varchar(100) DEFAULT NULL,
  ENQUERY_DATE datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ENQID)
)


INSERT INTO ui_verbiage (SCREEN_NAME,SCREEN_SECTION,SCREEN_KEY,VERBIAGE_SHORT_DESC,VERBIAGE_DETAIL_DESC,LAST_UPDATE_TIME_STAMP,LAST_UPDATE_USER,COMMENTS,STATUS) VALUES 
('service_details.html','BasicService','BasicPlan1','Doctor Appointments','It''s a pain to schedule a Dr appointment, we will arrange it for you and send reminder.',NULL,'Susovan',NULL,'ACTIVE')

INSERT INTO ui_verbiage (SCREEN_NAME,SCREEN_SECTION,SCREEN_KEY,VERBIAGE_SHORT_DESC,VERBIAGE_DETAIL_DESC,LAST_UPDATE_TIME_STAMP,LAST_UPDATE_USER,COMMENTS,STATUS) VALUES 
('service_details.html','EmergencyService','EmergencyPlan1','Ambulance Assistance','Emergency has no time, it can happen any time, we will provide our best support and arrange ambulance.',NULL,'Susovan',NULL,'ACTIVE')


INSERT INTO ui_verbiage (SCREEN_NAME,SCREEN_SECTION,SCREEN_KEY,VERBIAGE_SHORT_DESC,VERBIAGE_DETAIL_DESC,LAST_UPDATE_TIME_STAMP,LAST_UPDATE_USER,COMMENTS,STATUS) VALUES 
('service_details.html','GeneralService','GeneralPlan1','Sending Notifications', 'Now no need to remember your Dr appointments, We will send you notification before hand.',NULL,'Susovan',NULL,'ACTIVE')