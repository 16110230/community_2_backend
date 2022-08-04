package com.lawencon.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lawencon.util.VerificationCodeUtil.VerificationCodes;

@Configuration
public class GlobalConfiguration {
	/**
	 * Map Key consist of email and verification code 
	 * @return list of verification codes
	 */
	@Bean(name = "verificationCodes")
	public Map<String, VerificationCodes> verificationCodes() {
		return new HashMap<>();
	}
}
