package com.lawencon.community.pojo.activity;

import java.util.List;

public class ShowActivityIncomeReport {
	private List<PojoActivityIncomeReport> data;
	private Integer countData;

	public List<PojoActivityIncomeReport> getData() {
		return data;
	}

	public void setData(List<PojoActivityIncomeReport> data) {
		this.data = data;
	}

	public Integer getCountData() {
		return countData;
	}

	public void setCountData(Integer countData) {
		this.countData = countData;
	}

}
