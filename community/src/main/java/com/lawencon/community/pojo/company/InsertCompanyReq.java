package com.lawencon.community.pojo.company;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class InsertCompanyReq {
	
	@NotBlank(message = "Company name can't be empty")
	@Length(min=4,max=35)
	private String companyName;
	
	@NotBlank(message = "Company code can't be empty")
	@Length(min=2,max=10)
	private String companyCode;
	
	@NotBlank(message = "Email can't be empty")
	@Length(min=15,max=30)
	private String email;
	
	@NotBlank(message = "Company address can't be empty")
	@Column(columnDefinition = "TEXT")
	private String address;
	
	@NotNull(message = "Is active company can't be null")
	private Boolean isActive;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
