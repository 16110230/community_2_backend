package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="file")
public class File extends BaseEntity{

	private static final long serialVersionUID = -5196455701225322056L;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="file_ext")
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
	
	
}
