package com.lawencon.community.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="user_subscription")
public class UserSubscription extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	private Users user;
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
