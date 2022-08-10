package com.lawencon.community.pojo.industry;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UpdateIndustryReq {
	
	@NotNull(message = "Id can't be null")
	private String id;
	
	@NotBlank(message = "Industry name can't be empty")
	@Length(min=4,max=35)
	private String industryName;
	
	@NotBlank(message = "Industry code can't be empty")
	@Length(min=2,max=10)
	private String industryCode;
	
	@NotNull(message = "Is active can't be null")
	private Boolean isActive;
	
	@NotNull(message = "Version can't be null")
	private Integer version;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
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
