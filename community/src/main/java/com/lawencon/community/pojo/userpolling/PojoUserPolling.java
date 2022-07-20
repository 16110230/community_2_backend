package com.lawencon.community.pojo.userpolling;

public class PojoUserPolling {
	private String id;
	private String user;
	private String fullName;
	private String pollingDetails;
	private Integer version;
	private Boolean isActive;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
