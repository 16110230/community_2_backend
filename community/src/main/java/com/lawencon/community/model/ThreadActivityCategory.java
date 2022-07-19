package com.lawencon.community.model;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Table(name = "thread_activity_category", uniqueConstraints = {
		@UniqueConstraint(
				name = "thread_activity_bk",
				columnNames = "thread_activity_code"
		)
})
public class ThreadActivityCategory extends BaseEntity {

	private static final long serialVersionUID = -5196455701225322056L;

	@Column(name = "thread_activity_name", columnDefinition = "TEXT")
	private String threadActivityName;

	@Column(name = "thread_activity_code", columnDefinition = "TEXT")
	private String threadActivityCode;

	public String getThreadActivityName() {
		return threadActivityName;
	}

	public void setThreadActivityName(String threadActivityName) {
		this.threadActivityName = threadActivityName;
	}

	public String getThreadActivityCode() {
		return threadActivityCode;
	}

	public void setThreadActivityCode(String threadActivityCode) {
		this.threadActivityCode = threadActivityCode;
	}

}