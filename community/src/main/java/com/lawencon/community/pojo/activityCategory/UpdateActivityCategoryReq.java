package com.lawencon.community.pojo.activityCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UpdateActivityCategoryReq {

	@NotBlank(message = "Id can't be empty")
	private String id;

	@NotBlank(message = "Category Name can't be empty")
	@Length(min=4,max=35)
	private String categoryName;

	@NotBlank(message = "Category Code can't be empty")
	@Length(min=2,max=10)
	private String categoryCode;

	@NotNull(message = "is active can't be null")
	private Boolean isActive;

	@NotNull(message = "Version activity category can't be null")
	private Integer version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
