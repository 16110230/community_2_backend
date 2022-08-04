package com.lawencon.community.pojo.polling;

import java.util.List;

public class ShowPollingMain {
	private PojoPolling header;
	private List<PojoPollingDetails> details;

	public PojoPolling getHeader() {
		return header;
	}

	public void setHeader(PojoPolling header) {
		this.header = header;
	}

	public List<PojoPollingDetails> getDetails() {
		return details;
	}

	public void setDetails(List<PojoPollingDetails> details) {
		this.details = details;
	}

}
