package com.lawencon.community.pojo;

public class PojoInsertRes {
	private PojoInsertResData data;
	private String message;
	
	public void setData(PojoInsertResData data) {
		this.data = data;
	}
	
	public PojoInsertResData getData() {
		return data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
