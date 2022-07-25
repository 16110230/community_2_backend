package com.lawencon.community.pojo.threadActivity;

import javax.validation.constraints.NotNull;

public class InsertThreadActivityReq {
	
	@NotNull(message = "Thread can't be null")
	private String thread;
	
	@NotNull(message = "Thread activity category can't be null")
	private String threadActivityCategory;
	
	@NotNull(message = "Is active can't be null")
	private Boolean isActive;

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getThreadActivityCategory() {
		return threadActivityCategory;
	}

	public void setThreadActivityCategory(String threadActivityCategory) {
		this.threadActivityCategory = threadActivityCategory;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
