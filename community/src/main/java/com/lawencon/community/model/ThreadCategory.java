package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "thread_category")
public class ThreadCategory extends BaseEntity {

	private static final long serialVersionUID = -5196455701225322056L;

	@Column(name = "category_name", columnDefinition = "TEXT")
	private String categoryName;

	@Column(name = "category_code", columnDefinition = "TEXT")
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