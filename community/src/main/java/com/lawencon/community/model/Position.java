package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="positions", uniqueConstraints = {
	@UniqueConstraint(
			name = "position_code_bk",
			columnNames = "position_code"
	)
})
public class Position extends BaseEntity{
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@Column(name="position_name")
	private String positionName;
	
	@Column(name="position_code")
	private String positionCode;
	
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}	
	
}
