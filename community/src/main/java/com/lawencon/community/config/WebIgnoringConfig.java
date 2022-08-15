package com.lawencon.community.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class WebIgnoringConfig {
	private List<RequestMatcher> matchers = new ArrayList<RequestMatcher>();
	
	@Bean("webIgnoring")
	public List<RequestMatcher> antMatchers(){
		matchers.add(new AntPathRequestMatcher("/register", HttpMethod.POST.name()));
		matchers.add(new AntPathRequestMatcher("/users", HttpMethod.POST.name()));
		matchers.add(new AntPathRequestMatcher("/companies", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/industries", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/positions", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/files/**", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/threads/non-login/**", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/threads/article", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/thread-details", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/users/verification", HttpMethod.POST.name()));
		matchers.add(new AntPathRequestMatcher("/activity-invoices/download/**", HttpMethod.GET.name()));
		matchers.add(new AntPathRequestMatcher("/activities/download/**", HttpMethod.GET.name()));
		return matchers;
	}
}
