package com.lawencon.community.constant;

public enum Role {
	SYSTEM("System"), ADMIN("Admin"), MEMBER("Member");
	
	private String code;
	
	Role(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
