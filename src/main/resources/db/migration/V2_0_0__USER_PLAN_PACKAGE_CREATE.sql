create table josthi_db.PLANS (
PLAN_ID INT NOT NULL auto_increment,
PLAN_NAME VARCHAR(100) NOT NULL,
PLAN_DESCRIPTION VARCHAR(255) default null,
ACTIVE VARCHAR(1) default 'Y',
YEARLY_PLAN_PRICE DECIMAL(10,2) default null,
MONTHLY_PLAN_PRICE DECIMAL(10,2) default null,
primary key (PLAN_ID)
) COMMENT='Plan details. No price will set for custom plans.';
