package com.lawencon.community.pojo.company;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateCompanyReq {
	
	@NotNull(message = "Id can't be null")
	private String id;
	
	@NotBlank(message = "Company name can't be empty")
	private String companyName;
	
	@NotBlank(message = "Company code can't be empty")
	private String companyCode;
	
	@NotBlank(message = "Company address can't be empty")
	private String address;
	
	@NotNull(message = "Version company can't be null")
	private Integer version;
	
	@NotNull(message = "Is active company can't be null")
	private Boolean isActive;
	
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return address;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
