package com.lawencon.community.pojo.activity;

import java.util.List;

public class ShowActivities {
	
	private List<PojoActivity> data;
	private Integer countData;
	
	public void setData(List<PojoActivity> data) {
		this.data = data;
	}
	
	public List<PojoActivity> getData() {
		return data;
	}

	public Integer getCountData() {
		return countData;
	}

	public void setCountData(Integer countData) {
		this.countData = countData;
	}

	
}
