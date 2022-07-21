package com.lawencon.community.pojo.subscriptions;

import java.time.LocalDateTime;

public class InsertSubscriptionReq {
	private String user;
	private String file;
	private LocalDateTime expiredDate;
	private Boolean isApproved;
	private String subscriptionCategory;
	private Boolean isActive;
	private String fileName;
	private String fileExt;
	
	public String getUser() {
		return user;
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
	public void setUser(String user) {
		this.user = user;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public LocalDateTime getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(LocalDateTime expiredDate) {
		this.expiredDate = expiredDate;
	}
	public Boolean getIsApproved() {
		return isApproved;
	}
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
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
