create table josthi_db.DURATION (
ID INT NOT NULL auto_increment,
DURATION_NAME VARCHAR(50) NOT null,
primary key (ID)
)COMMENT='Duration options to be displayed in under plan selection dropdown';

create table josthi_db.PLAN (
ID INT NOT NULL auto_increment,
PLAN_NAME VARCHAR(100) NOT NULL,
DESCRIPTION VARCHAR(255) default null,
ACTIVE CHAR(1) default 'Y',
primary key (ID),
key `IX_PLAN_NAME` (PLAN_NAME)
)COMMENT='List of plans to be offered';

create table josthi_db.PLAN_PRICE (
ID INT NOT NULL auto_increment,
PLAN_ID INT NOT null,
DURATION_ID INT NOT null,
PRICE_FOR_1_PERSON DECIMAL(10,2) default null,
PRICE_FOR_2_PERSON DECIMAL(10,2) default null,
PRICE_FOR_3_PERSON DECIMAL(10,2) default null,
CURRENCY VARCHAR(3) default null, -- INR/USD/GBP
ACTIVE CHAR(1) default 'Y', -- Needed only if you want to maintain offered price history. Earlier pricing entry will be set inactive and new entry will be made for any pricing update
PRICE_UPDATED_ON datetime default null, -- Needed only if you want to maintain pricing history
primary key (ID),
constraint `FK_PLAN_PRICE_ID` foreign key (PLAN_ID) references josthi_db.PLAN (ID),
constraint `FK_PLAN_PRICE_DURATION` foreign key (DURATION_ID) references josthi_db.DURATION (ID)
)COMMENT='Pricing master table for plans';

create table josthi_db.SERVICE_TYPE (
ID INT NOT NULL auto_increment,
TYPE_NAME VARCHAR(100) NOT NULL,
DESCRIPTION VARCHAR(255) default null,
ACTIVE CHAR(1) default 'Y',
primary key (ID)
)COMMENT='List of service types (Medical/general/Misc)';

create table josthi_db.SERVICE (
ID INT NOT NULL auto_increment,
SERVICE_NAME VARCHAR(100) NOT NULL,
DESCRIPTION VARCHAR(255) default null,
ACTIVE CHAR(1) default 'Y',
SERVICE_TYPE_ID INT NOT null,
INCLUDED_IN_PLAN VARCHAR(6) default 'NNNNNN',
ON_DEMAND_FLAG CHAR(1) default 'Y',
primary key (ID),
key `IX_SERVICE_NAME` (SERVICE_NAME),
constraint `FK_SEVICE_TYPE` foreign key (SERVICE_TYPE_ID) references josthi_db.SERVICE_TYPE (ID)
)COMMENT='Master table of all services to be offered';

create table josthi_db.SERVICE_PRICE (
ID INT NOT NULL auto_increment,
SERVICE_ID INT NOT null,
PRICE DECIMAL(10,2) default null,
CURRENCY VARCHAR(3) default null, -- INR/USD/GBP,
ACTIVE CHAR(1) default 'Y', -- Needed only if you want to maintain offered price history. Earlier pricing entry will be set inactive and new entry will be made for any pricing update
PRICE_UPDATED_ON datetime default null, -- Needed only if you want to maintain pricing history
primary key (ID),
constraint `FK_SERVICE_PLAN_ID` foreign key (SERVICE_ID) references josthi_db.SERVICE (ID)
)COMMENT='Pricing table for services';