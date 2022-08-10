package com.lawencon.community.pojo.activityCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class InsertActivityCategoryReq {
	
	@NotBlank(message = "Category Name can't be empty")
	@Length(min=4,max=35)
	private String categoryName;
	
	@NotBlank(message = "Category Code can't be empty")
	@Length(min=2,max=10)
	private String categoryCode;
	
	@NotNull(message = "is active can't be null")
	private Boolean isActive;

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

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsActive() {
		return isActive;
	}

}
