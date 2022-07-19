package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="subscriptions")
public class Subscription extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	@OneToOne
	@JoinColumn(name="file_id")
	private File file;
	
	@Column(name="expired_date")
	private LocalDateTime expiredDate;
	
	@Column(name="is_approved")
	private Boolean isApproved;
	
	@OneToOne
	@JoinColumn(name="subs_category_id")
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
