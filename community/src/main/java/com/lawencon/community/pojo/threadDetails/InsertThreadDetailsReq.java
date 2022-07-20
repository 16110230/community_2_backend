package com.lawencon.community.pojo.threadDetails;

public class InsertThreadDetailsReq {
	private String thread;
	private String threadDesc;
	private String userId;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
