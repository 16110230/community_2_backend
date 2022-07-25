package com.lawencon.community.pojo.activityDetails;

import javax.validation.constraints.NotBlank;

public class InsertActivityDetailsReq {
	
	@NotBlank(message = "Activity Can't Null")
	private String activity;
	
	@NotBlank(message = "User Can't Null")
	private String user;
	private String file;
	private String fileName;
	private String fileExt;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}
