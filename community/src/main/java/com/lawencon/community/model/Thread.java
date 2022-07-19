package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "thread")
public class Thread extends BaseEntity {

	private static final long serialVersionUID = -5196455701225322056L;

	@Column(name = "thread_title", columnDefinition = "TEXT")
	private String threadTitle;

	@Column(name = "thread_content", columnDefinition = "TEXT")
	private String threadContent;

	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;

	@OneToOne
	@JoinColumn(name = "thread_category_id")
	private ThreadCategory threadCategory;

	public String getThreadTitle() {
		return threadTitle;
	}

	public void setThreadTitle(String threadTitle) {
		this.threadTitle = threadTitle;
	}

	public String getThreadContent() {
		return threadContent;
	}

	public void setThreadContent(String threadContent) {
		this.threadContent = threadContent;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ThreadCategory getThreadCategory() {
		return threadCategory;
	}

	public void setThreadCategory(ThreadCategory threadCategory) {
		this.threadCategory = threadCategory;
	}

}