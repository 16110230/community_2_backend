package com.lawencon.community.pojo.subscategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InsertSubsCategoryReq {
	
	@NotBlank(message = "Description Can't Be Null")
	private String description;
	
	@NotNull(message = "Price Can't Be Null")
	private Long price;
	
	@NotNull(message = "Active Can't Be Null")
	private Boolean isActive;
	
	@NotNull(message = "Duration Can't Be Null")
	private Integer duration;
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Integer getDuration() {
		return duration;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
