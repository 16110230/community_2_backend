package com.lawencon.community.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class WebIgnoringConfig {
	private List<RequestMatcher> matchers = new ArrayList<RequestMatcher>();
	
	@Bean("webIgnoring")
	public List<RequestMatcher> antMatchers(){
		return matchers;
	}
}
