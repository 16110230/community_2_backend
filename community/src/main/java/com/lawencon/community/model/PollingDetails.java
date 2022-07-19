package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="polling_details")
public class PollingDetails extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	private Polling polling;
	private String pollingDetailsName;
	
	public Polling getPolling() {
		return polling;
	}
	public void setPolling(Polling polling) {
		this.polling = polling;
	}
	public String getPollingDetailsName() {
		return pollingDetailsName;
	}
	public void setPollingDetailsName(String pollingDetailsName) {
		this.pollingDetailsName = pollingDetailsName;
	}
}
