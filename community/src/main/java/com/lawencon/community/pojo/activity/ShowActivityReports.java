package com.lawencon.community.pojo.activity;

import java.util.List;

public class ShowActivityReports {

	private List<PojoActivityReport> data;
	private Integer countData;

	public List<PojoActivityReport> getData() {
		return data;
	}

	public void setData(List<PojoActivityReport> data) {
		this.data = data;
	}

	public Integer getCountData() {
		return countData;
	}

	public void setCountData(Integer countData) {
		this.countData = countData;
	}

}
