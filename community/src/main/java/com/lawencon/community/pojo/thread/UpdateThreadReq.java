package com.lawencon.community.pojo.thread;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateThreadReq {
	
	@NotNull(message = "Thread id can't be null")
	private String id;
	
	@NotBlank(message = "Thread Title can't be empty")
	private String threadTitle;
	
	@NotBlank(message = "Thread Title can't be empty")
	private String threadContent;
	private String file;
	
	@NotNull(message = "Thread category can't be null")
	private String threadCategory;
	
	@NotNull(message = "Is active can't be null")
	private Boolean isActive;
	
	@NotNull(message = "Version can't be null")
	private Integer version;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getThreadTitle() {
		return threadTitle;
	}

	public void setThreadTitle(String threadTitle) {
		this.threadTitle = threadTitle;
	}

	public String getThreadContent() {
		return threadContent;
	}

	public void setThreadContent(String threadContent) {
		this.threadContent = threadContent;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getThreadCategory() {
		return threadCategory;
	}

	public void setThreadCategory(String threadCategory) {
		this.threadCategory = threadCategory;
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