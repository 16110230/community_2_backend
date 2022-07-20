package com.lawencon.community.pojo.polling;

public class PojoPollingDetails {
	private String id;
	private String polling;
	private String pollingDetailsName;
	private Integer version;
	private Boolean isActive;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPolling() {
		return polling;
	}
	public void setPolling(String polling) {
		this.polling = polling;
	}
	public String getPollingDetailsName() {
		return pollingDetailsName;
	}
	public void setPollingDetailsName(String pollingDetailsName) {
		this.pollingDetailsName = pollingDetailsName;
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
