package com.lawencon.community.pojo.activity;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UpdateActivityReq {
	
	@NotBlank(message = "Id Can't Be Null")
	private String id;
	
	@NotBlank(message = "Title Can't Be Null")
	private String activityTitle;
	
	@NotBlank(message = "Content Can't Be Null")
	private String activityContent;
	
	@NotBlank(message = "Category Can't Be Null")
	private String activityCategory;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+07:00'")
	private Date startedAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+07:00'")
	private Date endedAt;
	
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
	
	@NotNull(message = "Is Active Can't Be Null")
	private Boolean isActive;
	
	@NotNull(message = "Version Can't Be Null")
	private Integer version;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	public Date getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
	public Date getEndedAt() {
		return endedAt;
	}
	public void setEndedAt(Date endedAt) {
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
