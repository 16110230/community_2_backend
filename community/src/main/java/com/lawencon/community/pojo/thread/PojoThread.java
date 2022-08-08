package com.lawencon.community.pojo.thread;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawencon.community.pojo.polling.ShowPollingMain;

public class PojoThread {
	private String id;
	private String threadTitle;
	private String threadContent;
	private String file;
	private String threadcategory;
	private String threadCategoryName;
	private Boolean isActive;
	private Integer version;
	private String user;
	private String userName;
	private Integer countLike;
	private Integer countBookmark;
	private Integer countComment;
	private Boolean isLike;
	private Boolean isBookmark;
	private ShowPollingMain polling;
	private String userFile;
	
	public void setUserFile(String userFile) {
		this.userFile = userFile;
	}
	
	public String getUserFile() {
		return userFile;
	}
	
	public ShowPollingMain getPolling() {
		return polling;
	}
	
	public void setPolling(ShowPollingMain polling) {
		this.polling = polling;
	}
	
	public Integer getCountBookmark() {
		return countBookmark;
	}
	
	public void setCountBookmark(Integer countBookmark) {
		this.countBookmark = countBookmark;
	}
	
	public Integer getCountComment() {
		return countComment;
	}

	public void setCountComment(Integer countComment) {
		this.countComment = countComment;
	}

	public Boolean getIsLike() {
		return isLike;
	}

	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}

	public Boolean getIsBookmark() {
		return isBookmark;
	}

	public void setIsBookmark(Boolean isBookmark) {
		this.isBookmark = isBookmark;
	}

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createdAt;

	public Integer getCountLike() {
		return countLike;
	}

	public void setCountLike(Integer countLike) {
		this.countLike = countLike;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getThreadcategory() {
		return threadcategory;
	}

	public void setThreadcategory(String threadcategory) {
		this.threadcategory = threadcategory;
	}

	public String getThreadCategoryName() {
		return threadCategoryName;
	}

	public void setThreadCategoryName(String threadCategoryName) {
		this.threadCategoryName = threadCategoryName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
