package com.lawencon.community.constant;

public enum ThreadCategoryType {

	FREE("free"), SUBS("subscribe"), ART("article"), POL("polling");
	
	private String code;
	
	private ThreadCategoryType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code; 
	}
}
