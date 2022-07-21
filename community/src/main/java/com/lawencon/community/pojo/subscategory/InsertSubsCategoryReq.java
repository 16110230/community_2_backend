package com.lawencon.community.pojo.subscategory;

public class InsertSubsCategoryReq {
	private String description;
	private Long price;
	private Boolean isActive;
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
