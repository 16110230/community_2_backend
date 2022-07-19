package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "thread_details")
public class ThreadDetails extends BaseEntity {

	private static final long serialVersionUID = -5196455701225322056L;
	
	@ManyToOne
	@JoinColumn(name = "thread_id")
	private Thread thread;
	
	@Column(name = "thread_desc", columnDefinition = "TEXT")
	private String threadDesc;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private Users user;

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public String getThreadDesc() {
		return threadDesc;
	}

	public void setThreadDesc(String threadDesc) {
		this.threadDesc = threadDesc;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
}