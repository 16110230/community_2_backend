package com.lawencon.community.constant;

public enum ThreadActivityType {
	
	LIKE("like"), BOOKMARK("bookmark");
	
	private String code;
	
	private ThreadActivityType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code; 
	}
}	
