package com.lawencon.community.pojo.activityType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateActivityTypeReq {
	
	@NotBlank(message = "Type name can't be empty")
	private String typeName;
	
	@NotBlank(message = "Type code can't be empty")
	private String typeCode;
	
	@NotNull(message = "Is active activity type can't be null")
	private Boolean isActive;
	
	@NotNull(message = "Version activity type can't be null")
	private Integer version;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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