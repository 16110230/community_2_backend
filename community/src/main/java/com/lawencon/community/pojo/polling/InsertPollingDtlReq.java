package com.lawencon.community.pojo.polling;

public class InsertPollingDtlReq {
	private String polling;
	private String pollingDetailsName;
	
	public void setPolling(String polling) {
		this.polling = polling;
	}
	public String getPolling() {
		return polling;
	}
	public void setPollingDetailsName(String pollingDetailsName) {
		this.pollingDetailsName = pollingDetailsName;
	}
	public String getPollingDetailsName() {
		return pollingDetailsName;
	}
}
