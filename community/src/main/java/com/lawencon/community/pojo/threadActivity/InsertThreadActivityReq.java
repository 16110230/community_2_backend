package com.lawencon.community.pojo.threadActivity;

import javax.validation.constraints.NotNull;

public class InsertThreadActivityReq {
	
	@NotNull(message = "Thread can't be null")
	private String thread;
	
	@NotNull(message = "Thread activity category can't be null")
	private String threadActivityCategory;
	
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
	
}
