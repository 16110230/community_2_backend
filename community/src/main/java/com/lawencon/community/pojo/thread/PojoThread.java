package com.lawencon.community.pojo.thread;

public class PojoThread {
	private String id;
	private String threadTitle;
	private String threadContent;
	private String file;
	private String threadcategory;
	private String threadCategoryName;
	private Boolean isActive;
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

	public String getThreadcategory() {
		return threadcategory;
	}

	public void setThreadcategory(String threadcategory) {
		this.threadcategory = threadcategory;
	}

	public String getThreadCategoryName() {
		return threadCategoryName;
	}

	public void setThreadCategoryName(String threadCategoryName) {
		this.threadCategoryName = threadCategoryName;
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
