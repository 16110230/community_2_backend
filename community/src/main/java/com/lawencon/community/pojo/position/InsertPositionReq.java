package com.lawencon.community.pojo.position;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class InsertPositionReq {
	
	@NotBlank(message = "Position Name Can't Be Null")
	@Length(min=4,max=35)
	private String positionName;
	
	@NotBlank(message = "Position Code Can't Be Null")
	@Length(min=2,max=10)
	private String positionCode;
	
	@NotNull(message = "Active Can't Be Null")
	private Boolean isActive;
	
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
}
