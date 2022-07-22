package com.lawencon.community.pojo.subscriptions;

import javax.validation.constraints.NotNull;

public class UpdateSubscriptionReq {
	
	@NotNull(message = "Id can't be null")
	private String id;
	
	@NotNull(message = "Is approved can't be null")
	private Boolean isApproved;
	
	@NotNull(message = "Subscription category can't be null")
	private String subscriptionCategory;
	
	@NotNull(message = "Version can't be null")
	private Integer version;
	
	@NotNull(message = "Is active can't be null")
	private Boolean isActive;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
