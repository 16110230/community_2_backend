package com.lawencon.community.pojo.users;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class InsertUserReq {
	@NotBlank(message = "Full Name Can't Be Null")
	@Length(min=4,max=35)
	private String fullName;
	
	@NotBlank(message = "User Name Can't Be Null")
	@Length(min=6,max=10)
	private String username;
	
	@NotBlank(message = "Email Can't Be Null")
	@Length(min=15,max=30)
	private String email;
	
	private String verificationCode;
	
	@NotBlank(message = "Company Can't Be Null")
	private String company;
	
	@NotBlank(message = "Industry Can't Be Null")
	private String industry;
	
	@NotBlank(message = "Position Can't Be Null")
	private String position;
	
	@NotBlank(message = "Password Can't Be Null")
	@Length(min=6,max=10)
	private String userPassword;
	
	private String role;
	
	
	public void setRole(String role) {
		this.role = role;
	}
	public String getRole() {
		return role;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
