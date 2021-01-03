ALTER TABLE user_detail ADD COLUMN PROFILE_PIC_PATH VARCHAR(300) NULL AFTER PHOTO_PASSPORT_SIZE

--26th Oct 2020
ALTER TABLE josthi_db.user_detail ADD COLUMN GENDER VARCHAR(10) NULL AFTER LAST_NAME;
ALTER TABLE josthi_db.user_detail ADD COLUMN ZIP_PIN VARCHAR(20) NULL AFTER COUNTRY;
ALTER TABLE josthi_db.user_detail ADD COLUMN LAND_LINE_NO VARCHAR(20) NULL AFTER WHATSAPP_NO;
ALTER TABLE josthi_db.user_detail ADD COLUMN FAX_NO VARCHAR(20) NULL AFTER LAND_LINE_NO;
ALTER TABLE josthi_db.user_detail ADD COLUMN OFFICE_PH_NO VARCHAR(20) NULL AFTER FAX_NO;


ALTER TABLE josthi_db.user_detail ADD COLUMN ADDITIONAL_ADDRESS_LINE VARCHAR(100) NULL AFTER USER_ADDRESS_SECOND_LINE
ALTER TABLE josthi_db.user_detail ADD COLUMN POLICE_STATION VARCHAR(50) NULL AFTER ADDITIONAL_ADDRESS_LINE
ALTER TABLE josthi_db.user_detail ADD COLUMN POST_OFFICE VARCHAR(50) NULL AFTER POLICE_STATION
ALTER TABLE josthi_db.user_detail ADD COLUMN NEAREST_LAND_MARK VARCHAR(100) NULL AFTER POST_OFFICE
ALTER TABLE josthi_db.user_detail ADD COLUMN COVERAGE_AREA VARCHAR(50) NULL AFTER NEAREST_LAND_MARK

ALTER TABLE josthi_db.user_detail ADD COLUMN AGENCY_NAME VARCHAR(100) NULL AFTER COMMENTS
ALTER TABLE josthi_db.user_detail ADD COLUMN AGENCY_DESCRIPTION VARCHAR(300) NULL AFTER AGENCY_NAME

ALTER TABLE josthi_db.user_auth_table ADD COLUMN ENABLED tinyint NOT NULL DEFAULT '1' AFTER TEMPORARY_LOCK_ENABLED;
ALTER TABLE josthi_db.user_auth_table ADD COLUMN VERIFIED_USER VARCHAR(2) NOT NULL DEFAULT 'N' AFTER `ENABLED`;


CREATE TABLE josthi_db.agent_enquery (
  ENQUERY_ID INT NOT NULL AUTO_INCREMENT,
  EMAIL_ID VARCHAR(60) NOT NULL,
  AGENT_NAME VARCHAR(110) NOT NULL,
  AGENT_QUERY VARCHAR(400) NULL,
  AGENT_QUERY_TYPE VARCHAR(100) NULL,
  REQUEST_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  QUERY_RESOLVED_ON DATETIME NULL,
  ENQUERY_STATUS VARCHAR(10) NOT NULL DEFAULT 'OPEN',
  AGENT_CONTACT_NO VARCHAR(20) NULL,
  AGENT_COVERAGE_AREA VARCHAR(10) NULL,
  PRIMARY KEY (ENQUERY_ID));

  -- *************************** Installed on Nov 11 2020 ********************************
  CREATE TABLE emergency_contact_details (
  CONTACT_ID INT NOT NULL AUTO_INCREMENT,
  PRIMARY_UID VARCHAR(15) NOT NULL,
  BEN_ID VARCHAR(15) NULL,
  EMERGENCY_CONTACT_NAME VARCHAR(100) NULL,
  EMERGENCY_CONTACT_NUMBER VARCHAR(20) NOT NULL,
  EMERGENCY_CONTACT_STAY_LOCATION VARCHAR(100) NULL,
  RELATION VARCHAR(50) NULL,
  CONTACT_NO_VALIDATED VARCHAR(5) NULL DEFAULT 'NO',
  INSERT_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  NOTES VARCHAR(1000) NULL,
  PRIMARY KEY (CONTACT_ID),
  UNIQUE INDEX INSERT_DATE_UNIQUE (INSERT_DATE ASC));
  
  
  CREATE TABLE relation (
  RELATION_ID INT NOT NULL AUTO_INCREMENT,
  CUSTOMER_ID VARCHAR(15) NOT NULL,
  BENEFICIARY_ID VARCHAR(15) NULL,
  AGENT_ID VARCHAR(15) NULL,
  BENEFICIARY_TYPE VARCHAR(20) NULL,
  INSERT_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATE_DATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (RELATION_ID));
  
  
  CREATE TABLE beneficiary_detail (
  tid INT NOT NULL AUTO_INCREMENT,
  CUSTOMER_ID VARCHAR(15) NULL,
  BENEFICIARY_ID VARCHAR(15) NOT NULL,
  RELATION_WITH_CUSTOMER VARCHAR(45) NULL,
  DATE_OF_BIRTH DATETIME NULL,
  AGE VARCHAR(20) NULL,
  HEIGHT VARCHAR(20) NULL,
  WEIGHT VARCHAR(20) NULL,
  BLOOD_GROUP VARCHAR(20) NULL,
  PREFFERED_HOSPITAL VARCHAR(200) NULL,
  MEDICLAME_NAME VARCHAR(200) NULL,
  INSURANCE_NOTE VARCHAR(200) NULL,
  HEALTH_CONDITION VARCHAR(1000) NULL,
  MEDICAL_CHALLENGES VARCHAR(500) NULL,
  PRIMARY KEY (tid));
  
  CREATE TABLE dropdown_metadata (
  TID int NOT NULL AUTO_INCREMENT,
  DROPDOWN_TYPE varchar(100) NOT NULL,
  KEY_ID varchar(300) NOT NULL,
  DROPDOWN_VALUE varchar(300) NOT NULL,
  ACTIVE varchar(1) NOT NULL DEFAULT 'Y',
  UPDATE_DATE datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (TID)
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

--****************************************************************************************
--************************ Installed on 19 Nov********************************************

ALTER TABLE user_auth_table
ADD COLUMN OTP VARCHAR(45) NULL AFTER VERIFIED_USER,
ADD COLUMN VALID_EMAIL VARCHAR(10) NOT NULL DEFAULT 'NO' AFTER OTP,
ADD COLUMN OTP_GEN_DATE_TIME DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER VALID_EMAIL;
  

CREATE TABLE user_preferences (
  TID int NOT NULL AUTO_INCREMENT,
  USER_ID varchar(15) NOT NULL,
  TIME_ZONE varchar(200) DEFAULT '106IST',
  PREFERED_LANGUAGE varchar(45) DEFAULT 'English',
  PLAN_RENEWAL_REMINDER tinyint NOT NULL DEFAULT '0',
  WHATSAPP_NOTIFICATION tinyint NOT NULL DEFAULT '0',
  EMAIL_NOTIFICATION_FOR_AD tinyint NOT NULL DEFAULT '0',
  LAST_UPDATE_TIMESTAMP datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (TID),
  UNIQUE KEY `USER_ID_UNIQUE` (USER_ID)
)
  
-- LANGUAGE SCRIPT 
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


--********************************************************************************************
--*********************** Installed on 20th Nov 2020******************************************
CREATE TABLE service_request_table (
  UID INT NOT NULL AUTO_INCREMENT,
  TICKET_NO VARCHAR(15) NOT NULL,
  REQUESTED_BY VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
  REQUESTER_ID VARCHAR(15) NOT NULL,
  REQUESTED_FOR VARCHAR(200) NOT NULL,
  BENEFICIARY_ID VARCHAR(15) NULL,
  REQUESTED_VIA VARCHAR(20) NULL,
  ASSIGNED_TO VARCHAR(15) NULL,
  REQUESTED_ON DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  TO_BE_COMPLETED_BY DATETIME NULL,
  SERVICE_TYPE VARCHAR(45) NULL,
  SERVICE_CATEGORY VARCHAR(200) NULL,
  SERVICE_REQ_DESCRIPTION VARCHAR(3000) NOT NULL DEFAULT 'Service Request',
  SERVICE_URGENCY VARCHAR(100) NULL,
  SERVICE_STATUS VARCHAR(45) NULL,
  LAST_UPDATE DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  COMMENTS  VARCHAR(200) NULL,
  PRIMARY KEY (UID),
  UNIQUE INDEX TICKET_NO_UNIQUE (TICKET_NO ASC));
  
  

  
INSERT INTO dropdown_metadata (DROPDOWN_TYPE, KEY_ID, DROPDOWN_VALUE, ACTIVE, UPDATE_DATE)
VALUES('ServiceType', 'Pre-Paid Service', 'Pre-Paid Service', 'Y', CURRENT_TIMESTAMP),
('ServiceType', 'On Demand Basic Service', 'On Demand Basic Service', 'Y', CURRENT_TIMESTAMP),
('ServiceType', 'On Demand Emergency Service', 'On Demand Emergency Service', 'Y', CURRENT_TIMESTAMP),
('ServiceType', 'On Demand General Service', 'On Demand General Service', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Doctor Appointments', 'Doctor Appointments', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Medical Diagnostics', 'Medical Diagnostics', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Arranging Nursing Services', 'Arranging Nursing Services', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', '24X7 Oncall Support', '24X7 Oncall Support', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Arranging Physiotherapy', 'Arranging Physiotherapy', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Medicines Delivery', 'Medicines Delivery', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Ambulance Assistance', 'Ambulance Assistance', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Arranging Hospitalization', 'Arranging Hospitalization', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Hearing aids & Vision aids', 'Hearing aids & Vision aids', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Cooked Food Delivery', 'Cooked Food Delivery', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Cook Service at Home', 'Cook Service at Home', 'Y', CURRENT_TIMESTAMP),
('PaidServicaCategory', 'Transportation Arrangement', 'Transportation Arrangement', 'Y', CURRENT_TIMESTAMP),
('OnDemandServicaCategory', 'Hearing aids & Vision aids', 'Hearing aids & Vision aids', 'Y', CURRENT_TIMESTAMP),
('OnDemandServicaCategory', 'Cooked Food Delivery', 'Cooked Food Delivery', 'Y', CURRENT_TIMESTAMP),
('OnDemandServicaCategory', 'Cook Service at Home', 'Cook Service at Home', 'Y', CURRENT_TIMESTAMP),
('OnDemandServicaCategory', 'Transportation Arrangement', 'Transportation Arrangement', 'Y', CURRENT_TIMESTAMP),
('UrgencyType', 'Low', 'Low', 'Y', CURRENT_TIMESTAMP),
('UrgencyType', 'Medium', 'Medium', 'Y', CURRENT_TIMESTAMP),
('UrgencyType', 'High', 'High', 'Y', CURRENT_TIMESTAMP),
('UrgencyType', 'Urgent', 'Urgent', 'Y', CURRENT_TIMESTAMP);


CREATE TABLE service_ticket_history (
  HISTORY_ID INT NOT NULL AUTO_INCREMENT,
  TICKET_NUMBER VARCHAR(15) NULL,
  STATUS VARCHAR(45) NULL,
  COMMENTS VARCHAR(2000) NULL,
  UPDATE_TIMESTAMP DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UPDATED_BY_NAME VARCHAR(200) NULL,
  UPDATED_BY_ID VARCHAR(15) NULL,
  PRIMARY KEY (HISTORY_ID));


ALTER TABLE service_request_table
ADD COLUMN LAST_UPDATE_COMMENTS VARCHAR(2000) NULL AFTER COMMENTS,
ADD COLUMN LAST_UPDATE_USER VARCHAR(200) NULL AFTER LAST_UPDATE_COMMENTS;

ALTER TABLE service_request_table 
CHANGE COLUMN REQUESTED_BY REQUESTED_BY VARCHAR(200) NOT NULL DEFAULT 'CUSTOMER' ;
  
 
  
  CREATE TABLE agency_details (
  AID INT NOT NULL AUTO_INCREMENT,
  AGENT_ID VARCHAR(15) NOT NULL,
  AGENCY_NAME VARCHAR(200) NOT NULL,
  AGENCY_DESCRIPTION VARCHAR(1000) NULL,
  AGENCY_LIC_DETAIL VARCHAR(200) NULL,
  VERIFICATION_DOC VARCHAR(1000) NULL,
  DOC_PATH VARCHAR(500) NULL,
  PRIMARY KEY (AID),
  UNIQUE INDEX AGENT_ID_UNIQUE (AGENT_ID ASC) );
  

ALTER TABLE dropdown_metadata
ADD COLUMN APPLICABLE_FOR VARCHAR(10) NOT NULL DEFAULT 'All' AFTER UPDATE_DATE,
ADD COLUMN USAGE_NOTES VARCHAR(50) NULL AFTER APPLICABLE_FOR;
  
  
INSERT INTO dropdown_metadata (DROPDOWN_TYPE, KEY_ID, DROPDOWN_VALUE, ACTIVE, UPDATE_DATE)
VALUES('TicketStatus', 'Pre-Paid Service', 'Pre-Paid Service', 'Y', CURRENT_TIMESTAMP)

CREATE TABLE activity_history_table (
  ACTIVITY_ID INT NOT NULL AUTO_INCREMENT,
  ACTIVITY_FOR VARCHAR(15) NULL,
  ACTIVITY_BY VARCHAR(15) NULL,
  ACTIVITY_DATE_TIME DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  ACTIVITY_NOTE VARCHAR(300) NULL,
  PRIMARY KEY (ACTIVITY_ID));

  
   --########################## NEXT RELEASE #######################################

delete from dropdown_metadata where DROPDOWN_TYPE = 'ServiceType';
  
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


--TODO : Refer to Recent Table Update.
ALTER TABLE relation
ADD COLUMN PLAN_NAME VARCHAR(50) NULL AFTER UPDATE_DATE,
ADD COLUMN PLAN_PRICE_BREAKUP_ID INT NULL AFTER PLAN_NAME,
ADD COLUMN PLAN_EXPIRE_DATE DATETIME NULL AFTER PLAN_PRICE_BREAKUP_ID,
ADD COLUMN PLAN_INVOICE_ID VARCHAR(50) NULL AFTER PLAN_EXPIRE_DATE;


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