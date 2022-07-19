package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="polling_details")
public class PollingDetails extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@ManyToOne
	@JoinColumn(name="polling_id")
	private Polling polling;
	
	@Column(name="polling_details_name")
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
