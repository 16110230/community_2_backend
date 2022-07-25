package com.lawencon.community.pojo.threadDetails;

public class UpdateThreadDetailsReq {
	
	private String thread;
	private String threadDesc;
	private Boolean isActive;
	private Integer version;

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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}