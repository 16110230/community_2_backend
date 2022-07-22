package com.lawencon.community.pojo.userpolling;

import javax.validation.constraints.NotNull;

public class InsertUserPolling {
	
	@NotNull(message = "User can't be null")
	private String user;
	
	@NotNull(message = "Polling details can't be null")
	private String pollingDetails;
	
	@NotNull(message = "Is active can't be null")
	private Boolean isActive;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPollingDetails() {
		return pollingDetails;
	}
	public void setPollingDetails(String pollingDetails) {
		this.pollingDetails = pollingDetails;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
