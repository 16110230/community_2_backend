package com.lawencon.community.pojo.activity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InsertActivityReq {
	
	@NotBlank(message = "Title Can't Be Null")
	private String activityTitle;
	
	@NotBlank(message = "Content Can't Be Null")
	private String activityContent;
	
	@NotBlank(message = "Category Can't Be Null")
	private String activityCategory;
	
	private LocalDateTime startedAt;
	private LocalDateTime endedAt;
	
	@NotNull(message = "Fee Can't Be Null")
	private Integer fee;
	
	@NotNull(message = "Qty Can't Be Null")
	private Integer quantity;
	
	@NotNull(message = "Limit Can't Be Null")
	private Boolean isLimit;
	
	@NotBlank(message = "Provider Can't Be Null")
	private String provider;
	
	@NotBlank(message = "Trainer Can't Be Null")
	private String trainer;
	
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
	public LocalDateTime getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(LocalDateTime startedAt) {
		this.startedAt = startedAt;
	}
	public LocalDateTime getEndedAt() {
		return endedAt;
	}
	public void setEndedAt(LocalDateTime endedAt) {
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
