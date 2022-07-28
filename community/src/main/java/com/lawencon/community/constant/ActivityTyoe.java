package com.lawencon.community.constant;

public enum ActivityTyoe {
	
	EVENT("event"), COURSE("course");
	
	private String code;
	
	private ActivityTyoe(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code; 
	}
}
