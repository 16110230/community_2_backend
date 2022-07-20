package com.lawencon.community.pojo.threadActivity;

public class InsertThreadActivityReq {
	private String thread;
	private String threadActivityCategory;
	private String userId;

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

}
