package com.lawencon.community.pojo.subscriptions;

import javax.validation.constraints.NotNull;

public class InsertSubscriptionReq {
	private String file;

	@NotNull(message = "Subscription category can't be null")
	private String subscriptionCategory;

	@NotNull(message = "Is active can't be null")
	private Boolean isActive;
	private String fileName;
	private String fileExt;

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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getSubscriptionCategory() {
		return subscriptionCategory;
	}

	public void setSubscriptionCategory(String subscriptionCategory) {
		this.subscriptionCategory = subscriptionCategory;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
