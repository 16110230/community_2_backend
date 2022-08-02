package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="polling")
public class Polling extends BaseEntity  {
	
	private static final long serialVersionUID = -5746885109052389441L;
	
	@OneToOne
	@JoinColumn(name="thread_id")
	private Thread thread;
	
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}
}
