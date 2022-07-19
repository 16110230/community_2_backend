package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="user_polling")
public class UserPolling extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private Users user;
	
	@OneToOne
	@JoinColumn(name="polling_details_id")
	private PollingDetails pollingDetails;
	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public PollingDetails getPollingDetails() {
		return pollingDetails;
	}
	public void setPollingDetails(PollingDetails pollingDetails) {
		this.pollingDetails = pollingDetails;
	}
}
