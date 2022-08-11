package com.lawencon.community.pojo.users;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class UpdateUserReq {
	private String id;
	
	@NotBlank(message = "Full Name Can't Be Null")
	@Length(min=4,max=35)
	private String fullName;
	
	@NotBlank(message = "Email Can't Be Null")
	@Length(min=15,max=30)
	private String email;
	
	@NotBlank(message = "Username Can't Be Null")
	@Length(min=6,max=10)
	private String username;
	
	private String userPassword;
	private String company;
	private String industry;
	private String position;
	private Integer version;
	private Boolean isActive;
	private String file;
	private String fileName;
	private String fileExt;
	private Integer balance;
	
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getBalance() {
		return balance;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
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
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	public Boolean getIsActive() {
		return isActive;
	}
}
