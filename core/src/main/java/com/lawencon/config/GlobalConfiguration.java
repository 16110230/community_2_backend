package com.lawencon.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalConfiguration {
	/**
	 * Map Key consist of email and verification code 
	 * @return list of verification codes
	 */
	@Bean(name = "verificationCodes")
	public List<Map<String, String>> verificationCodes() {
		return new ArrayList<>();
	}

}
