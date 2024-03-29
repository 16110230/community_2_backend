package com.lawencon.community.pojo.threadActivityCategory;

public class PojoThreadActivityCategory {
	private String id;
	private String threadActivityName;
	private String threadActivityCode;
	private Boolean isActive;
	private Integer version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getThreadActivityName() {
		return threadActivityName;
	}

	public void setThreadActivityName(String threadActivityName) {
		this.threadActivityName = threadActivityName;
	}

	public String getThreadActivityCode() {
		return threadActivityCode;
	}

	public void setThreadActivityCode(String threadActivityCode) {
		this.threadActivityCode = threadActivityCode;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
