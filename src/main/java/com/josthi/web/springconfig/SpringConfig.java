package com.josthi.web.springconfig;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.josthi.web.controller.CacheConfigDataController;
import com.josthi.web.daoimpl.BaseDaoImpl;
import com.josthi.web.daoimpl.CacheConfigDaoImpl;
import com.josthi.web.daoimpl.EmailDaoImpl;
import com.josthi.web.daoimpl.FileWikiDaoImpl;
import com.josthi.web.daoimpl.HistoryDaoImpl;
import com.josthi.web.daoimpl.PlanDetailsDaoImpl;
import com.josthi.web.daoimpl.SchedulerDaoImpl;
import com.josthi.web.daoimpl.ServiceRequestDaoImpl;
import com.josthi.web.daoimpl.UserAuthDaoImpl;
import com.josthi.web.daoimpl.UserDetailsDaoImpl;
import com.josthi.web.daoimpl.UserRegistrationDaoImpl;
import com.josthi.web.mail.EmailSenderService;
import com.josthi.web.security.SecurityConfig;
import com.josthi.web.serviceimpl.BeneficiaryServiceImpl;
import com.josthi.web.serviceimpl.CacheConfigServiceImpl;
import com.josthi.web.serviceimpl.PlanAndBenefitServiceImpl;
import com.josthi.web.serviceimpl.ServiceRequestServiceImpl;
import com.josthi.web.serviceimpl.UserRegistrationServiceImpl;
import com.josthi.web.utils.HostNamePropertyPlaceHolderConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.naming.NamingException;
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
  * ====================  LOADING PROP FILE  =================================
  * ==========================================================================
  */
 
 @Bean(name="serverName")
 public String serverName() throws NamingException, UnknownHostException{
	 final String serverName = InetAddress.getLocalHost().getHostName().toUpperCase();
	 logger.info("=============================================");
	 logger.info("Host Name:"+serverName);
	 logger.info("=============================================");
	 return serverName;
 }
 
 //@Bean("propertyConfigurer")
 //public HostNamePropertyPlaceHolderConfig propertyConfigurer (String serverName) throws IOException, Exception {
//	 final HostNamePropertyPlaceHolderConfig host = new HostNamePropertyPlaceHolderConfig(serverName);
//	 return host;
 //}
 
 
 
 
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
 
	/*
	 * @Bean public SecurityConfig securityConfig(DataSource josthiDataSource) {
	 * SecurityConfig securityConfig = new SecurityConfig();
	 * securityConfig.setDataSource(josthiDataSource); return securityConfig; }
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
	 logger.info("Base Dao Initialised");
	 baseDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return baseDaoImpl;
 }
 
 @Bean("emailDao")
 public EmailDaoImpl emailDaoImpl(DataSource josthiDataSource) {	 
	 EmailDaoImpl emailDaoImpl = new EmailDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 logger.info("emailDao Initialised!!");
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
 
 @Bean("schedulerDao")
 public SchedulerDaoImpl schedulerDaoImpl(DataSource josthiDataSource) {
	 SchedulerDaoImpl schedulerDaoImpl = new SchedulerDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 schedulerDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return schedulerDaoImpl;
 }

 
 @Bean("cacheConfigDao")
 public CacheConfigDaoImpl cacheConfigDaoImpl(DataSource josthiDataSource) {
	 CacheConfigDaoImpl cacheConfigDaoImpl = new CacheConfigDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 cacheConfigDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return cacheConfigDaoImpl;
 }
 
 
 @Bean("fileWikiDao")
 public FileWikiDaoImpl fileWikiDaoImpl(DataSource josthiDataSource) {
	 FileWikiDaoImpl fileWikiDaoImpl = new FileWikiDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 fileWikiDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return fileWikiDaoImpl;
 }
 
 @Bean("serviceRequestDao")
 public ServiceRequestDaoImpl serviceRequestDaoImpl(DataSource josthiDataSource) {
	 ServiceRequestDaoImpl serviceRequestDaoImpl = new ServiceRequestDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 serviceRequestDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return serviceRequestDaoImpl;
 }
 
 
 @Bean("userDetailsDao")
 public UserDetailsDaoImpl userDetailsDaoImpl(DataSource josthiDataSource) {
	 UserDetailsDaoImpl userDetailsDaoImpl = new UserDetailsDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 userDetailsDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return userDetailsDaoImpl;
 }
 
 @Bean("historyDao")
 public HistoryDaoImpl historyDaoImpl(DataSource josthiDataSource) {
	 HistoryDaoImpl historyDaoImpl = new HistoryDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 historyDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return historyDaoImpl;
 }
 
 
 @Bean("planDetailsDao")
 public PlanDetailsDaoImpl planDetailsDaoImpl(DataSource josthiDataSource) {
	 PlanDetailsDaoImpl planDetailsDaoImpl = new PlanDetailsDaoImpl();
	 JdbcTemplate jdbcTemplate = new JdbcTemplate(josthiDataSource);
	 planDetailsDaoImpl.setJdbcTemplate(jdbcTemplate);
	 return planDetailsDaoImpl;
 }
 
 /* ==========================================================================
  * ==================== S E R V I C E   T R I G G E R   =====================
  * ========================================================================== 
  */

 
	/*
	 * @Bean("cacheConfigService") public CacheConfigServiceImpl
	 * cacheConfigServiceImpl() { CacheConfigServiceImpl cacheConfigServiceImpl =
	 * new CacheConfigServiceImpl(); cacheConfigServiceImpl.setCacheConfigDao(new
	 * CacheConfigDaoImpl()); System.out.println("$$$$$$$$$$$$$$$$$$$"); return
	 * cacheConfigServiceImpl;
	 * 
	 * }
	 * 
	 * @Bean("cacheConfigDataController") public CacheConfigDataController
	 * cacheConfigDataController() { CacheConfigDataController
	 * cacheConfigDataController = new CacheConfigDataController();
	 * cacheConfigDataController.setCacheConfigService(new
	 * CacheConfigServiceImpl()); cacheConfigDataController.getConfigData(); return
	 * cacheConfigDataController; }
	 */
 
 
 
 /*===========================================================================
  * ================  TRANSACTION MANAGER CONFIG  ============================
  * ==========================================================================
  */
 
 @Bean("beneficiaryService")
 public BeneficiaryServiceImpl beneficiaryServiceImpl(PlatformTransactionManager txnManager) {
	 BeneficiaryServiceImpl beneficiaryServiceImpl = new BeneficiaryServiceImpl();
	 beneficiaryServiceImpl.setPlatformTransactionManager(txnManager);
	 return beneficiaryServiceImpl;
 }
 
 
 @Bean("userRegistrationService")
 public UserRegistrationServiceImpl userRegistrationServiceImpl(PlatformTransactionManager txnManager) {
	 UserRegistrationServiceImpl userRegistrationServiceImpl = new UserRegistrationServiceImpl();
	 userRegistrationServiceImpl.setPlatformTransactionManager(txnManager);
	 return userRegistrationServiceImpl;
 }
 
 
 @Bean("serviceRequestService")
 public ServiceRequestServiceImpl serviceRequestServiceImpl(PlatformTransactionManager txnManager) {
	 ServiceRequestServiceImpl serviceRequestServiceImpl = new ServiceRequestServiceImpl();
	 serviceRequestServiceImpl.setPlatformTransactionManager(txnManager);
	 return serviceRequestServiceImpl;
 }
 

 @Bean("planAndBenefitService")
 public PlanAndBenefitServiceImpl planAndBenefitServiceImpl(PlatformTransactionManager txnManager) {
	 PlanAndBenefitServiceImpl planAndBenefitServiceImpl = new PlanAndBenefitServiceImpl();
	 planAndBenefitServiceImpl.setPlatformTransactionManager(txnManager);
	 return planAndBenefitServiceImpl;
 }
 
 @Bean("txnManager")
 public DataSourceTransactionManager txnManager(DataSource josthiDataSource) {
	 DataSourceTransactionManager txnManager =  new DataSourceTransactionManager();
	 txnManager.setDataSource(josthiDataSource);
	 return txnManager;
 }
 
 /*===========================================================================
  * ===========================  EMAIL  CONFIG  ==============================
  * ==========================================================================
  */

 
	/* working Settings
	 * @Bean("javaMailSender") public JavaMailSenderImpl javaMailSenderImpl(
	 * 
	 * @Value("${spring.mail.default-encoding}") String defaultEncoding,
	 * 
	 * @Value("${spring.mail.host}") String host,
	 * 
	 * @Value("${spring.mail.username}") String username,
	 * 
	 * @Value("${spring.mail.password}") String password,
	 * 
	 * @Value("${spring.mail.port}") int port,
	 * 
	 * @Value("${spring.mail.protocol}") String protocol,
	 * 
	 * @Value("${spring.mail.test-connection}") String testConnection,
	 * 
	 * @Value("${spring.mail.properties.mail.smtp.auth}") String smptAuth,
	 * 
	 * @Value("${spring.mail.properties.mail.smtp.starttls.enable}") String
	 * starttlsEnabled,
	 * 
	 * @Value("${spring.mail.properties.mail.smtp.connectiontimeout}") String
	 * connectiontimeout,
	 * 
	 * @Value("${spring.mail.properties.mail.smtp.timeout}") String timeout,
	 * 
	 * @Value("${spring.mail.properties.mail.smtp.writetimeout}") String
	 * writetimeout ){
	 * 
	 * JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
	 * javaMailSender.setDefaultEncoding(defaultEncoding);
	 * javaMailSender.setHost(host); javaMailSender.setUsername(username);
	 * javaMailSender.setPassword(password); javaMailSender.setPort(port);
	 * javaMailSender.setProtocol(protocol);
	 * 
	 * Properties prop = javaMailSender.getJavaMailProperties();
	 * prop.put("spring.mail.test-connection", testConnection);
	 * prop.put("mail.smtp.port",port+""); prop.put("mail.smtp.auth", smptAuth);
	 * prop.put("mail.smtp.starttls.enable", starttlsEnabled);
	 * prop.put("mail.smtp.connectiontimeout", connectiontimeout);
	 * prop.put("mail.smtp.timeout", timeout); prop.put("mail.smtp.writetimeout",
	 * writetimeout);
	 * 
	 * return javaMailSender; }
	 */
 
	/*
	 * @Bean("templateEngine")
	 * 
	 * @Description("Thymeleaf template engine with Spring integration") public
	 * SpringTemplateEngine templateEngine() { SpringTemplateEngine templateEngine =
	 * new SpringTemplateEngine();
	 * templateEngine.setTemplateResolver(templateResolver());
	 * 
	 * return templateEngine; }
	 */
 
	/*
	 * @Bean
	 * 
	 * @Description("Thymeleaf template resolver serving HTML 5") public
	 * ClassLoaderTemplateResolver templateResolver() {
	 * 
	 * ClassLoaderTemplateResolver templateResolver = new
	 * ClassLoaderTemplateResolver();
	 * 
	 * //templateResolver.setPrefix("/"); templateResolver.setCacheable(false);
	 * templateResolver.setSuffix(".html");
	 * templateResolver.setTemplateMode("HTML5");
	 * templateResolver.setCharacterEncoding("UTF-8"); return templateResolver; }
	 */
 
 
	/*
	 * @Bean
	 * 
	 * @Description("Thymeleaf view resolver") public ViewResolver viewResolver() {
	 * 
	 * ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	 * viewResolver.setTemplateEngine(templateEngine());
	 * viewResolver.setCharacterEncoding("UTF-8"); return viewResolver; }
	 */
 
 
	/*
	 * @Bean("emailSenderService") public EmailSenderService
	 * emailSenderService(JavaMailSenderImpl javaMailSender) {
	 * 
	 * EmailSenderService emailSenderService = new EmailSenderService();
	 * emailSenderService.setEmailSender(javaMailSender);
	 * emailSenderService.setTemplateEngine(templateEngine()); return
	 * emailSenderService; }
	 */

 								

}
