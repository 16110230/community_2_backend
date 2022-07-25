package com.lawencon.community.pojo.activityDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateActivityDetailsReq {

	@NotBlank(message = "Id Can't Be Null")
	private String id;

	@NotBlank(message = "Activity Can't Be Null")
	private String activity;

	@NotBlank(message = "File Id Can't Be Null")
	private String file;

	@NotBlank(message = "File Name Id Can't Be Null")
	private String fileName;

	@NotBlank(message = "File Ext Can't Be Null")
	private String fileExt;

	@NotNull(message = "Is Active Can't Be Null")
	private Boolean isActive;

	@NotNull(message = "Version Id Can't Be Null")
	private Integer version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
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