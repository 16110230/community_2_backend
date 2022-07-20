package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name="activity_type", uniqueConstraints = {
	@UniqueConstraint(
			name = "activity_type_bk",
			columnNames = "type_code"
	)
})
public class ActivityType extends BaseEntity {
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@Column(name="type_name")
	private String typeName;
	
	@Column(name="type_code")
	private String typeCode;
	
	public String getTypeName() {
		return typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	public String getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
}
