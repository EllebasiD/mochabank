package com.externalbank.otherbank.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
* This class sets the security configuration.
* 
* @author Teachers of GLG203 Unit
*/
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		String[] staticResourcesImg = { "/img/**"};
		String[] staticResourcesCSS = { "/css/**"};
		
        http
        	.csrf().disable()
	        .formLogin()
	        	.loginProcessingUrl("/login")
	        	.loginPage("/login")
	        	.defaultSuccessUrl("/")
	        	.and()
	        .logout()
	        	.logoutSuccessUrl("/")
	        	.and()
	        .authorizeRequests()
	        	.antMatchers(staticResourcesImg).permitAll()
	        	.antMatchers(staticResourcesCSS).permitAll()
	        	.antMatchers("/","/favicon.ico","/login","/about","/open-account-request","/new-account", "/check-balance", "/check-transactions", "/order-transfer", "/receive-transfer", "/program-transfer").permitAll()
	        	.anyRequest().authenticated();
	}
	
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

