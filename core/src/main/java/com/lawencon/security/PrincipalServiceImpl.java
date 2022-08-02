package com.lawencon.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PrincipalServiceImpl implements PrincipalService {

	@Override
	public String getPrincipal() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null)
			throw new RuntimeException("Invalid Login");
		return auth.getPrincipal().toString();
	}

}
