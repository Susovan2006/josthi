package com.josthi.web.springconfig;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
  * ======================  DATABASE CONFIG  =================================
  * ==========================================================================
  */
 
 @Bean("mockellDataSource")
 public BasicDataSource mockellDataSource(
		 	@Value("${mockell.db.driver}") String driver,
		 	@Value("${mockell.db.url}") String url,
		 	@Value("${mockell.db.username}") String userName,
		 	@Value("${mockell.db.password}") String password,
		 	@Value("${mockell.db.initialSize}") int initialSize,
		 	@Value("${mockell.db.maxActive}") int maxActive,
		 	@Value("${mockell.db.maxIdle}") int maxIdle,
		 	@Value("${mockell.db.commandtimeout}") long commandtimeout,
		 	@Value("${mockell.db.queryTimeout}") Integer queryTimeout,
		 	@Value("${mockell.db.testOnBorrow}") boolean testOnBorrow,
		 	@Value("${mockell.db.validationQueryTimeout}") int validationQueryTimeout) throws Exception {
	 
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
 
	

}
