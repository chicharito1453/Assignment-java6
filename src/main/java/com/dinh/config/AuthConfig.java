package com.dinh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dinh.service.impl.AccountServiceImpl;

@Configuration
@EnableWebSecurity
public class AuthConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AccountServiceImpl accountServiceImpl;
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(accountServiceImpl);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/admin/**").hasAnyRole("ADMIN","STAFF")
			.antMatchers("/checkout","/order","/orderDetail/**","/order/remove/**","/changeP","/profile").authenticated()
			.antMatchers("/rest/authorities**").hasRole("ADMIN")
			.anyRequest().permitAll();
		
		http.exceptionHandling()
			.accessDeniedPage("/access/denied");
		
		http.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/auth/login")
			.defaultSuccessUrl("/login/success",false)
			.failureUrl("/login/error")
			.usernameParameter("username")
			.passwordParameter("password");
		http.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(86400);
		
		http.logout()
			.logoutUrl("/logoff")
			.logoutSuccessUrl("/home");
	}
	
}
