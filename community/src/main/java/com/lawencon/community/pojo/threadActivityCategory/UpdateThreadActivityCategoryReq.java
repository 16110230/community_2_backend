package com.lawencon.community.pojo.threadActivityCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateThreadActivityCategoryReq {
	
	@NotBlank(message = "Id Can't Be Null")
	private String id;
	
	@NotBlank(message = "Thread Activity Category Name Can't Be Null")
	private String threadActivityName;
	
	@NotBlank(message = "Thread Activity Category Code Can't Be Null")
	private String threadActivityCode;
	
	@NotNull(message = "Active Can't Be Null")
	private Boolean isActive;
	
	@NotNull(message = "Version Can't Be Null")
	private Integer version;

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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