package com.lawencon.community.pojo.subscategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateSubsCategoryReq {

	@NotBlank(message = "Id Can't Be Null")
	private String id;

	@NotBlank(message = "Description Can't Be Null")
	private String description;

	@NotNull(message = "Price Can't Be Null")
	private Long price;

	@NotNull(message = "Version Can't Be Null")
	private Integer version;

	@NotNull(message = "Duration Can't Be Null")
	private Integer duration;

	@NotNull(message = "Active Can't Be Null")
	private Boolean isActive;

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
