package com.lawencon.community.pojo.threadDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InsertThreadDetailsReq {
	
	@NotNull(message = "Thread can't be null")
	private String thread;
	
	@NotBlank(message = "Thread Description can't be empty")
	private String threadDesc;
	
	@NotNull(message = "Is active can't be null")
	private Boolean isActive;

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getThreadDesc() {
		return threadDesc;
	}

	public void setThreadDesc(String threadDesc) {
		this.threadDesc = threadDesc;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
}
