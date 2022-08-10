package com.lawencon.community.pojo;

public class PojoUpdateRes {
	private PojoUpdateResData data;
	private String message;
	
	public void setData(PojoUpdateResData data) {
		this.data = data;
	}
	
	public PojoUpdateResData getData() {
		return data;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}
