package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="subscriptions")
public class Subscription extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	private Users user;
	private File file;
	private LocalDateTime expiredDate;
	private Boolean isApproved;
	private SubscriptcionCategory subscriptionCategory;
	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
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
	public SubscriptcionCategory getSubscriptionCategory() {
		return subscriptionCategory;
	}
	public void setSubscriptionCategory(SubscriptcionCategory subscriptionCategory) {
		this.subscriptionCategory = subscriptionCategory;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
