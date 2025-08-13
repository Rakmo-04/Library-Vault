package com.LibVault.spring_boot_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import com.okta.spring.boot.oauth.Okta;

@Configuration
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		//Disable cross site request forgery
		http.csrf().disable();
		
		//protection endpoints at /api/<type>/secure
		http.authorizeRequests(configure->
		configure.
		antMatchers("/api/books/secure/**",
				"/api/reviews/secure/**",
				"/api/messages/secure/**",
				"/api/admin/secure/**")
		.authenticated())
		.oauth2ResourceServer()
		.jwt();
		//cors filter
		http.cors();
		
		//add content negotiation strategy
		http.setSharedObject(ContentNegotiationStrategy.class, 
				new HeaderContentNegotiationStrategy());
		
		Okta.configureResourceServer401ResponseBody(http);
		
		
		
		return http.build();
	}
	
}
