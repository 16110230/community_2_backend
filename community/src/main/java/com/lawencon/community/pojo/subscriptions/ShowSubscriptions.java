package com.lawencon.community.pojo.subscriptions;

import java.util.List;

public class ShowSubscriptions {
	private List<PojoSubscription> data;
	private Integer countData;
	
	public void setData(List<PojoSubscription> data) {
		this.data = data;
	}
	
	public List<PojoSubscription> getData() {
		return data;
	}

	public Integer getCountData() {
		return countData;
	}

	public void setCountData(Integer countData) {
		this.countData = countData;
	}
	
	
}
