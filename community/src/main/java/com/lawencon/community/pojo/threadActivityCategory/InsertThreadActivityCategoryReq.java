package com.lawencon.community.pojo.threadActivityCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InsertThreadActivityCategoryReq {
	
	@NotBlank(message = "Thread Activity Category Name Can't Be Null")
	private String threadActivityName;
	
	@NotBlank(message = "Thread Activity Category Code Can't Be Null")
	private String threadActivityCode;
	
	@NotNull(message = "Active Can't Be Null")
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
