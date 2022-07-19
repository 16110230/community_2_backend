package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "thread_activity")
public class ThreadActivity extends BaseEntity {

	private static final long serialVersionUID = -5196455701225322056L;
	
	@OneToOne
	@JoinColumn(name = "thread_id")
	private Thread thread;
	
	@OneToOne
	@JoinColumn(name = "thread_activity_category_id")
	private ThreadActivityCategory threadActivityCategory;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private Users user;

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public ThreadActivityCategory getThreadActivityCategory() {
		return threadActivityCategory;
	}

	public void setThreadActivityCategory(ThreadActivityCategory threadActivityCategory) {
		this.threadActivityCategory = threadActivityCategory;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}