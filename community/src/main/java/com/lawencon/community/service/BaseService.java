package com.lawencon.community.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.base.BaseEntity;
import com.lawencon.community.exception.InvalidLoginException;

@Service
public class BaseService<T extends BaseEntity> extends BaseCoreService<T> {
	
	public String getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) {
			throw new InvalidLoginException("Invalid login!");
		}
		
		String id = auth.getPrincipal().toString();
		return id;
	}
}
