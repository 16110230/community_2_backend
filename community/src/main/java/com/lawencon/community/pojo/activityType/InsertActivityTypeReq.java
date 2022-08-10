package com.lawencon.community.pojo.activityType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class InsertActivityTypeReq {
	
	@NotBlank(message = "Type name can't be empty")
	@Length(min=4,max=35)
	private String typeName;
	
	@NotBlank(message = "Type code can't be empty")
	@Length(min=2,max=10)
	private String typeCode;
	
	@NotNull(message = "Is active activity type can't be null")
	private Boolean isActive;
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}

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

}
