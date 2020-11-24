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
('ServiceType', 'On Demand Service', 'On Demand Service', 'Y', CURRENT_TIMESTAMP),
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

--########################## NEXT RELEASE #######################################
ALTER TABLE `josthi_db`.`service_request_table` 
ADD COLUMN `LAST_UPDATE_COMMENTS` VARCHAR(2000) NULL AFTER `COMMENTS`,
ADD COLUMN `LAST_UPDATE_USER` VARCHAR(200) NULL AFTER `LAST_UPDATE_COMMENTS`;

ALTER TABLE `josthi_db`.`service_request_table` 
CHANGE COLUMN `REQUESTED_BY` `REQUESTED_BY` VARCHAR(200) NOT NULL DEFAULT 'CUSTOMER' ;
  
  
  
  
