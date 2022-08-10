package com.lawencon.community.pojo.activityType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UpdateActivityTypeReq {

	@NotBlank(message = "Id Can't Be Empty")
	private String id;

	@NotBlank(message = "Type name can't be empty")
	@Length(min=4,max=35)
	private String typeName;

	@NotBlank(message = "Type code can't be empty")
	@Length(min=2,max=10)
	private String typeCode;

	@NotNull(message = "Is active activity type can't be null")
	private Boolean isActive;

	@NotNull(message = "Version activity type can't be null")
	private Integer version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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