package com.lawencon.community.pojo.position;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdatePositionReq {
	@NotBlank(message = "Id Can't Ve Null")
	private String id;
	
	@NotBlank(message = "Position Name Can't Be Null")
	private String positionName;
	
	@NotBlank(message = "Position Code Can't Be Null")
	private String positionCode;
	
	@NotNull(message = "Active Can't Be Null")
	private Boolean isActive;
	
	@NotNull(message = "Version Can't Be Null")
	private Integer version;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
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
