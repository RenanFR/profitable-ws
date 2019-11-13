package com.profitable.ws.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("profitable");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.headers()
		.frameOptions()
		.disable()
			.and()
		.csrf()
		.disable()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
			.and()
		.authorizeRequests()
		.anyRequest()
		.fullyAuthenticated()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		.antMatchers("/h2-console/**").permitAll();
	}
	
}
