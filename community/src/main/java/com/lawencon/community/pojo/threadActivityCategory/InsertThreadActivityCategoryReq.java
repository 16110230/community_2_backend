package com.lawencon.community.pojo.threadActivityCategory;

public class InsertThreadActivityCategoryReq {
	private String threadActivityName;
	private String threadActivityCode;
	private Boolean isActive;
	
	public Boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getThreadActivityName() {
		return threadActivityName;
	}

	public void setThreadActivityName(String threadActivityName) {
		this.threadActivityName = threadActivityName;
	}

	public String getThreadActivityCode() {
		return threadActivityCode;
	}

	public void setThreadActivityCode(String threadActivityCode) {
		this.threadActivityCode = threadActivityCode;
	}

}
