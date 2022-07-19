package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="activity_category", uniqueConstraints = {
		@UniqueConstraint(
				name = "activity_category_bk",
				columnNames = "category"
		)
	})
public class ActivityCategory extends BaseEntity{
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@Column(name="category_name")
	private String categoryName;
	
	@Column(name="category_code")
	private String categoryCode;
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	
}
