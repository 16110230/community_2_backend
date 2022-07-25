package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="company", uniqueConstraints = {
		@UniqueConstraint(
				name = "company_code_bk",
				columnNames = "company_code"
		)
	})
public class Company extends BaseEntity{
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="company_code")
	private String companyCode;
	
	@Column(name="address")
	private String address;
	
	@Column(name="email")
	private String email;
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
