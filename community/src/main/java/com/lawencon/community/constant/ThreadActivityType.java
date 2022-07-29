package com.lawencon.community.constant;

public enum ThreadActivityType {
	
	LIKE("Like"), BOOKMARK("Bookmark");
	
	private String code;
	
	private ThreadActivityType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code; 
	}
}	
