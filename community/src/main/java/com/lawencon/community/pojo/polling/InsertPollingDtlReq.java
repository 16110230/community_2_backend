package com.lawencon.community.pojo.polling;

public class InsertPollingDtlReq {
	private String pollingDetailsName;
	private Boolean isActive;
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setPollingDetailsName(String pollingDetailsName) {
		this.pollingDetailsName = pollingDetailsName;
	}
	public String getPollingDetailsName() {
		return pollingDetailsName;
	}
}
