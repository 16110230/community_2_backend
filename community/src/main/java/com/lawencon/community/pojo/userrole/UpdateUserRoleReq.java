package com.lawencon.community.pojo.userrole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateUserRoleReq {
	
	@NotBlank(message = "Id Can't Be Null")
	private String id;
	
	@NotBlank(message = "Role Name Can't Be Null")
	private String roleName;
	
	@NotBlank(message = "Role Code Can't Be Null")
	private String roleCode;
	
	@NotNull(message = "Version Can't Be Null")
	private Integer version;
	
	@NotNull(message = "Active Can't Be Null")
	private Boolean isActive;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
