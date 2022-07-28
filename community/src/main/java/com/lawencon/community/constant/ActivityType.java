package com.lawencon.community.constant;

public enum ActivityType {
	
	EVENT("event"), COURSE("course");
	
	private String code;
	
	private ActivityType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code; 
	}
}
