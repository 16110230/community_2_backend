package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="user_subscription")
public class UserSubscription extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	@Column(name="expired_date")
	private LocalDateTime expiredDate;
	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public LocalDateTime getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(LocalDateTime expiredDate) {
		this.expiredDate = expiredDate;
	}
}
