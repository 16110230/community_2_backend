package com.lawencon.community.constant;

public enum ThreadCategoryType {

	REG("Regular"), PREM("Premium"), ART("Article"), POL("Polling");
	
	private String code;
	
	private ThreadCategoryType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code; 
	}
}
