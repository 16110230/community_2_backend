package com.lawencon.community.pojo.threadCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class UpdateThreadCategoryReq {
	
	@NotBlank(message = "Id Can't Be Null")
	private String id;
	
	@NotBlank(message = "Category Name Can't Be Null")
	@Length(min=4,max=35)
	private String categoryName;
	
	@NotBlank(message = "Category Code Can't Be Null")
	@Length(min=2,max=10)
	private String categoryCode;
	
	@NotNull(message = "Active Can't Be Null")
	private Boolean isActive;
	
	@NotNull(message = "Version Can't Be Null")
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