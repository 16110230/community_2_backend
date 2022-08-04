package com.lawencon.community.pojo.thread;

import java.util.List;

public class ShowThreads {
	
	private List<PojoThread> data;
	private Integer countData;
	
	public void setData(List<PojoThread> data) {
		this.data = data;
	}
	
	public List<PojoThread> getData() {
		return data;
	}

	public Integer getCountData() {
		return countData;
	}

	public void setCountData(Integer countData) {
		this.countData = countData;
	}
	
	

}
