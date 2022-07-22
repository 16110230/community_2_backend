package com.lawencon.community.pojo.thread;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InsertThreadReq {
	
	@NotBlank(message = "Thread Title can't be empty")
	private String threadTitle;
	
	@NotBlank(message = "Thread Content can't be empty")
	private String threadContent;
	
	private String file;
		
	@NotNull(message = "Thread category can't be null")
	private String threadCategory;
	
	private String fileName;
	private String fileExt;
	private Boolean isActive;

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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
}
