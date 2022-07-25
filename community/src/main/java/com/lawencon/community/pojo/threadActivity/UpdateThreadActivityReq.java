package com.lawencon.community.pojo.threadActivity;

import javax.validation.constraints.NotNull;

public class UpdateThreadActivityReq {
	
	@NotNull(message = "Thread can't be  null")
	private String thread;
	
	@NotNull(message = "Thread Activity Category")
	private String threadActivityCategory;
	
	@NotNull(message = "User id can't be null")
	private String userId;
	
	@NotNull(message = "Is active can't be null")
	private Boolean isActive;
	
	@NotNull(message = "Version can't be null")
	private Integer version;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}