package com.lawencon.community.pojo.activityInvoice;

import java.util.List;

public class ShowActivityInvoice {

	private List<PojoActivityInvoice> data;
	private Integer countData;

	public void setData(List<PojoActivityInvoice> data) {
		this.data = data;
	}

	public List<PojoActivityInvoice> getData() {
		return data;
	}

	public Integer getCountData() {
		return countData;
	}

	public void setCountData(Integer countData) {
		this.countData = countData;
	}

}
