package com.josthi.web.security;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


//help : https://mkyong.com/spring-security/spring-security-password-hashing-example/
//https://www.codejava.net/frameworks/spring-boot/form-authentication-with-jdbc-and-mysql

@Configuration("MySecurityConfig")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	DataSource josthiDataSource;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("###################configure calles auth:"+auth);
		auth.jdbcAuthentication()
			.dataSource(josthiDataSource)
			.usersByUsernameQuery("Select USERID_EMAIL , WORDAPP , ENABLED from user_auth_table where USERID_EMAIL = ?")
			.authoritiesByUsernameQuery("Select USERID_EMAIL , ROLE from user_auth_table where USERID_EMAIL = ?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		logger.info("###################configure calles http:"+http);
		
		http.csrf().disable();
		http.authorizeRequests()
					.antMatchers("/admin1/*").hasRole("ADMIN")
					.antMatchers("/agent1/*").hasAnyRole("ADMIN","AGENT")
					.antMatchers("/user1/*").hasAnyRole("ADMIN","USER")
					.antMatchers("/").permitAll()
					.and().formLogin().loginPage("/login");
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	
	
	

}
