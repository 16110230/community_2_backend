package com.lawencon.community.pojo.activity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class InsertActivityReq {
	
	@NotBlank(message = "Title Can't Be Null")
	private String activityTitle;
	
	@NotBlank(message = "Content Can't Be Null")
	private String activityContent;
	
//	@NotBlank(message = "Category Can't Be Null")
	private String activityCategory;
	
	@NotBlank(message = "Type Can't Be Null")
	private String activityType;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private String startedAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	private String endedAt;
	
	@NotNull(message = "Fee Can't Be Null")
	private Integer fee;
	
	@NotNull(message = "Qty Can't Be Null")
	private Integer quantity;
	
	@NotNull(message = "Limit Can't Be Null")
	private Boolean isLimit;
	
	private String provider;
	
	private String trainer;
	
	private String file;
	
	private String fileName;
	
	private String fileExt;
	
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
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
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public String getActivityType() {
		return activityType;
	}
	public String getActivityTitle() {
		return activityTitle;
	}
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
	public String getActivityContent() {
		return activityContent;
	}
	public void setActivityContent(String activityContent) {
		this.activityContent = activityContent;
	}
	public String getActivityCategory() {
		return activityCategory;
	}
	public void setActivityCategory(String activityCategory) {
		this.activityCategory = activityCategory;
	}

	public String getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(String startedAt) {
		this.startedAt = startedAt;
	}
	public String getEndedAt() {
		return endedAt;
	}
	public void setEndedAt(String endedAt) {
		this.endedAt = endedAt;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Boolean getIsLimit() {
		return isLimit;
	}
	public void setIsLimit(Boolean isLimit) {
		this.isLimit = isLimit;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getTrainer() {
		return trainer;
	}
	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}
	
	
}
