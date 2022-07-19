package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="industry", uniqueConstraints = {
	@UniqueConstraint(
			name = "industry_code_bk",
			columnNames = "industry_code"
	)
})
public class Industry extends BaseEntity{
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	private String industryName;
	private String industryCode;
	
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
	
	

}
