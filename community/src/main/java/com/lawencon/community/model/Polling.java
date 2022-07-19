package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="polling")
public class Polling extends BaseEntity  {
	
	private static final long serialVersionUID = -5196455701225322056L;
	private Thread thread;
	
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
}
