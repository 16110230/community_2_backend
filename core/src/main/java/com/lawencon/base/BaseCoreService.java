package com.lawencon.base;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author lawencon05
 */
public class BaseCoreService<T extends BaseEntity> {

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
}
