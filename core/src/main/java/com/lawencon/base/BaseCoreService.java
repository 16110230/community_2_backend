package com.lawencon.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lawencon05
 */
public class BaseCoreService<T extends BaseEntity> {

	
	@Autowired
	@Qualifier("verificationCodes")
	private List<Map<String, String>> verificationCodes;


	protected void begin() {
		ConnHandler.begin();
	}

	protected void commit() {
		ConnHandler.commit();
	}

	protected void rollback() {
		ConnHandler.rollback();
	}

	protected void clear() {
		ConnHandler.clear();
	}

	@SuppressWarnings("unchecked")
	protected <A> A getAuthPrincipal() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth == null)
			throw new Exception("Invalid Login");

		return (A) auth.getPrincipal().toString();
	}
	
	protected void addVerificationCode(String email, String verificationCode) {
		Map<String, String> map = new HashMap<>();
		map.put(email, verificationCode);
		verificationCodes.add(map);
	}

	protected void validateVerificationCode(String email, String verificationCode) throws Exception {
		Long countResult = verificationCodes.stream().filter(data -> {
			return data.get(email).equals(verificationCode);
		}).count();

		if (countResult <= 0) {
			throw new Exception("Invalid Verification Code");
		} else {
			verificationCodes = verificationCodes.stream().filter(data -> {
				return !data.get(email).equals(verificationCode);
			}).collect(Collectors.toList());
		}
	}
}
