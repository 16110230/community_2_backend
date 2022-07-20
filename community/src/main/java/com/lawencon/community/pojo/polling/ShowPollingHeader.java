package com.lawencon.community.pojo.polling;

import java.util.List;

public class ShowPollingHeader {
	private List<PojoPolling> data;
	
	public void setData(List<PojoPolling> data) {
		this.data = data;
	}
	
	public List<PojoPolling> getData() {
		return data;
	}
}