package com.lawencon.community.pojo.usersubscription;

import java.time.LocalDateTime;

public class InsertUserSubscriptionReq {
	private String user;
	private LocalDateTime expiredDate;
	private Boolean isActive;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public LocalDateTime getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(LocalDateTime expiredDate) {
		this.expiredDate = expiredDate;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
