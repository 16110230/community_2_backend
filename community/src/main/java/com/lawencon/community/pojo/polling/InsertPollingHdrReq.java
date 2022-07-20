package com.lawencon.community.pojo.polling;

public class InsertPollingHdrReq {
	private String thread;
	private Boolean isActive;
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	
	public String getThread() {
		return thread;
	}
}
