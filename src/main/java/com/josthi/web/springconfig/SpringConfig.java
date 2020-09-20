package com.josthi.web.springconfig;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.josthi.web.daoimpl.BaseDaoImpl;
import com.josthi.web.daoimpl.EmailDaoImpl;
import com.josthi.web.daoimpl.UserAuthDaoImpl;
import com.josthi.web.daoimpl.UserRegistrationDaoImpl;
import com.josthi.web.serviceimpl.UserRegistrationServiceImpl;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

@Configuration
public class SpringConfig {
	
	
/*===========================================================
 * ======================  Version  =========================
 * ===========================================================
 */
//private static final Logger LOG = Logger.getLogger(SpringConfig.class);
private static final Logger logger = LoggerFactory.getLogger(SpringConfig.class);
 @Bean(name="VERSION")
 public String version(@Value("${version}") String version) {
	 logger.info("=============================================");
	 logger.info(version);
	 logger.info("=============================================");
	 return version;  
 }
 
 
 /*===========================================================================
  * ====================  DATASOURCE CONFIG  =================================
  * ==========================================================================
  */
 
 @Bean("josthiDataSource")
 public BasicDataSource mockellDataSource(
		 	@Value("${josthi.db.driver}") String driver,
		 	@Value("${josthi.db.url}") String url,
		 	@Value("${josthi.db.username}") String userName,
		 	@Value("${josthi.db.password}") String password,
		 	@Value("${josthi.db.initialSize}") int initialSize,
		 	@Value("${josthi.db.maxActive}") int maxActive,
		 	@Value("${josthi.db.maxIdle}") int maxIdle,
		 	@Value("${josthi.db.commandtimeout}") long commandtimeout,
		 	@Value("${josthi.db.queryTimeout}") Integer queryTimeout,
		 	@Value("${josthi.db.testOnBorrow}") boolean testOnBorrow,
		 	@Value("${josthi.db.validationQueryTimeout}") int validationQueryTimeout) throws Exception {
	 
			 BasicDataSource datasource = new BasicDataSource();
			 datasource.setDriverClassName(driver);
			 datasource.setUrl(url);
			 datasource.setUsername(userName);
			 datasource.setPassword(password);
			 datasource.setInitialSize(initialSize);
			 datasource.setMaxTotal(maxActive);
			 datasource.setMaxIdle(maxIdle);
			 datasource.setMaxWaitMillis(commandtimeout);
			 datasource.setDefaultQueryTimeout(queryTimeout);
			 datasource.setTestOnBorrow(testOnBorrow);
			 datasource.setValidationQueryTimeout(validationQueryTimeout);	 
			 logger.info("Datasource Created Successfully!!");
	 
	 //return datasource;
	return datasource;
 }
 
 
 /*===========================================================================
  * ====================  DAO & DAOIMPL CONFIG  ==============================
  * ==========================================================================
  */
 
 @Bean("userAuthDao")
 public UserAuthDaoImpl userAuthDao(DataSource josthiDataSource) {
	 UserAuthDaoImpl userAuthDaoImpl = new UserAuthDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 userAuthDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return userAuthDaoImpl;
 }
 
 
 @Bean("baseDao")
 public BaseDaoImpl baseDao(DataSource josthiDataSource) {
	 
	 BaseDaoImpl baseDaoImpl = new BaseDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 System.out.println("Base Dao Initialised :"+jdbcTemplate);
	 baseDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return baseDaoImpl;
 }
 
 @Bean("emailDao")
 public EmailDaoImpl emailDaoImpl(DataSource josthiDataSource) {	 
	 EmailDaoImpl emailDaoImpl = new EmailDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 System.out.println("Base Dao Initialised :"+jdbcTemplate);
	 emailDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return emailDaoImpl;
 }	
 
 
 @Bean("userRegistrationDao")
 public UserRegistrationDaoImpl userRegistrationDaoImpl(DataSource josthiDataSource) {	 
	 UserRegistrationDaoImpl userRegistrationDaoImpl = new UserRegistrationDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 userRegistrationDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return userRegistrationDaoImpl;
 }	
 
 
 
 @Bean("userRegistrationService")
 public UserRegistrationServiceImpl userRegistrationServiceImpl(PlatformTransactionManager txnManager) {
	 UserRegistrationServiceImpl userRegistrationServiceImpl = new UserRegistrationServiceImpl();
	 userRegistrationServiceImpl.setPlatformTransactionManager(txnManager);
	 return userRegistrationServiceImpl;
 }
 
 
 /*===========================================================================
  * ================  TRANSACTION MANAGER CONFIG  ============================
  * ==========================================================================
  */
 
 @Bean("txnManager")
 public DataSourceTransactionManager txnManager(DataSource josthiDataSource) {
	 DataSourceTransactionManager txnManager =  new DataSourceTransactionManager();
	 txnManager.setDataSource(josthiDataSource);
	 return txnManager;
 }


}
