package com.galenyoder.spring.security.sample;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import edu.liberty.config.spring.security.CasLdapSecurityConfig;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends CasLdapSecurityConfig {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.antMatcher("/secure").authorizeRequests().anyRequest()
				.authenticated();
	}

	@Configuration
	@Order(500)
	public static class ApiWebSecurityConfigurationAdapter
			extends WebSecurityConfigurerAdapter {

		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/basic-secure").authorizeRequests().anyRequest()
					.authenticated().and().httpBasic();
		}
	}

}