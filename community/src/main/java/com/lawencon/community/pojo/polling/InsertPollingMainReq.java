package com.lawencon.community.pojo.polling;

import java.util.List;

public class InsertPollingMainReq {
	private InsertPollingHdrReq header;
	private List<InsertPollingDtlReq> details;
	
	public InsertPollingHdrReq getHeader() {
		return header;
	}
	public void setHeader(InsertPollingHdrReq header) {
		this.header = header;
	}
	public List<InsertPollingDtlReq> getDetails() {
		return details;
	}
	public void setDetails(List<InsertPollingDtlReq> details) {
		this.details = details;
	}
}
