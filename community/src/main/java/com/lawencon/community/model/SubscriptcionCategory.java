package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="subscriptions_category")
public class SubscriptcionCategory extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	private String description;
	private Integer price;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
