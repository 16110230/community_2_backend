package com.lawencon.community.constant;

public enum ActivityTypeCategory {
	
	EVENT("event"), COURSE("course");
	
	private String code;
	
	private ActivityTypeCategory(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code; 
	}
}
