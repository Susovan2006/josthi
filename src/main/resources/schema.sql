---grant all on twitter.* to 'root'@'127.0.0.1' identified by 'mypwd';


create table josthi_db.PLANS (
PLAN_ID INT NOT NULL auto_increment,
PLAN_NAME VARCHAR(100) NOT NULL,
PLAN_DESCRIPTION VARCHAR(255) default null,
ACTIVE VARCHAR(1) default 'Y',
YEARLY_PLAN_PRICE DECIMAL(10,2) default null,
MONTHLY_PLAN_PRICE DECIMAL(10,2) default null,
primary key (PLAN_ID),
key `IX_PLAN_NAME` (PLAN_NAME),
key `IX_PLAN_ACTIVE` (ACTIVE)
) COMMENT='Plan details. No price will set for custom plans.';

create table josthi_db.SEVICES (
SERVICE_ID INT NOT NULL auto_increment,
SERVICE_NAME VARCHAR(100) NOT NULL,
SERVICE_DESCRIPTION VARCHAR(255) default null,
ACTIVE VARCHAR(1) default 'Y',
YEARLY_SERVICE_PRICE DECIMAL(10,2) default null,
MONTHLY_SERVICE_PRICE DECIMAL(10,2) default null,
primary key (SERVICE_ID),
key `IX_SERVICE_NAME` (SERVICE_NAME),
key `IX_SERVICE_ACTIVE` (ACTIVE)
) COMMENT='Services to be offered. Price for individual services can also be set.';

create table josthi_db.SERVICES_FOR_PLAN (
PLAN_SERVICE_ID INT NOT NULL auto_increment,
PLAN_ID INT NOT null,
SERVICE_ID INT NOT null,
ACTIVE VARCHAR(1) default 'Y',
primary key (PLAN_SERVICE_ID),
unique key (PLAN_ID, SERVICE_ID),
key `IX_PS_PLAN_ID` (PLAN_ID),
key `IX_PS_SERVICE_ID` (SERVICE_ID),
constraint `FK_PS_PLAN_ID` foreign key (PLAN_ID) references josthi_db.PLANS (PLAN_ID),
constraint `FK_PS_SEVICE_ID` foreign key (SERVICE_ID) references josthi_db.SEVICES (SERVICE_ID)
) COMMENT='List of services to be offered under a plan.';

create table josthi_db.USER_PLAN_PACKAGE (
USER_PACKAGE_ID INT NOT NULL auto_increment,
USER_ID VARCHAR(20) not null,
PLAN_TYPE VARCHAR(20) not null, -- PLAN/SERVICE
PLAN_ID INT default null,
SERVICE_ID INT default null,
ACTIVE VARCHAR(1) default 'Y',
AMOUNT_PAID DECIMAL(10,2) default null,
PACKAGE_START_DATE datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
PACKAGE_END_DATE datetime NOT null,
primary key (USER_PACKAGE_ID),
key `IX_PKG_USER_ID` (USER_ID),
constraint `FK_PKG_USER_ID` foreign key (USER_ID) references josthi_db.USER_DETAIL (UID),
constraint `FK_PKG_PLAN_ID` foreign key (PLAN_ID) references josthi_db.PLANS (PLAN_ID),
constraint `FK_PKG_SEVICE_ID` foreign key (SERVICE_ID) references josthi_db.SEVICES (SERVICE_ID)
) COMMENT='Maintain user package details';