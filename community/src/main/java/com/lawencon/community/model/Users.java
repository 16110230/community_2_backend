package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="users", uniqueConstraints = {
	@UniqueConstraint(
			name = "email_bk",
			columnNames = "email"
	), 
	@UniqueConstraint(
			name = "username_bk",
			columnNames = "username"
	)
})
public class Users extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@Column(name="full_name")
	private String fullName;
	private String username;
	
	@Column(name="user_password")
	private String userPassword;
	private String email;
	
	@Column(name="verification_code")
	private String verificationCode;
	
	@OneToOne
	@JoinColumn(name="role_id")
	private UserRole role;
	
	@OneToOne
	@JoinColumn(name="company_id")
	private Company company;
	
	@OneToOne
	@JoinColumn(name="industry_id")
	private Industry industry;
	
	@OneToOne
	@JoinColumn(name="position_id")
	private Position position;
	
	@OneToOne
	@JoinColumn(name="file_id")
	private File file;
	private Integer balance;
	
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
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
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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
	public UserRole getRole() {
		return role;
	}
	public void setRole(UserRole role) {
		this.role = role;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Industry getIndustry() {
		return industry;
	}
	public void setIndustry(Industry industry) {
		this.industry = industry;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
}
