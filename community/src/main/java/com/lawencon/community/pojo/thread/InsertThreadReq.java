package com.lawencon.community.pojo.thread;

public class InsertThreadReq {
	private String threadTitle;
	private String threadContent;
	private String file;
	private String threadCategory;

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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getThreadCategory() {
		return threadCategory;
	}

	public void setThreadCategory(String threadCategory) {
		this.threadCategory = threadCategory;
	}

}
