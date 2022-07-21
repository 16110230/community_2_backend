package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="subscriptions_category")
public class SubscriptionCategory extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@Column(name="description")
	private String description;
	
	@Column(name="price")
	private Long price;
	
	@Column(name="duration")
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
