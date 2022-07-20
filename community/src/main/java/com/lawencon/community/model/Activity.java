package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "activity")
public class Activity extends BaseEntity {

	private static final long serialVersionUID = -5196455701225322056L;

	@Column(name = "activity_title")
	private String activityTitle;

	@Column(name = "activity_content")
	private String activityContent;

	@OneToOne
	@JoinColumn(name = "activity_category_id")
	private ActivityCategory activityCategory;

	@Column(name = "started_at")
	private LocalDateTime startedAt;

	@Column(name = "ended_at")
	private LocalDateTime endedAt;
	private Integer fee;

	@Column(name = "is_limit")
	private Boolean isLimit;
	private Integer quantity;
	private String provider;
	private String trainer;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public ActivityCategory getActivityCategory() {
		return activityCategory;
	}

	public void setActivityCategory(ActivityCategory activityCategory) {
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
