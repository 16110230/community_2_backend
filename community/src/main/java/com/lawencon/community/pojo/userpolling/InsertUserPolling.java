package com.lawencon.community.pojo.userpolling;

import javax.validation.constraints.NotNull;

public class InsertUserPolling {
	
	@NotNull(message = "Polling details can't be null")
	private String pollingDetails;
	
	public String getPollingDetails() {
		return pollingDetails;
	}
	public void setPollingDetails(String pollingDetails) {
		this.pollingDetails = pollingDetails;
	}
}
