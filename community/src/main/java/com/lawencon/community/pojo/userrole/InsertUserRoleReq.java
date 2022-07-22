package com.lawencon.community.pojo.userrole;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InsertUserRoleReq {
	
	@NotBlank(message = "Role Name Can't Be Null")
	private String roleName;
	
	@NotBlank(message = "Role Code Can't Be Null")
	private String roleCode;
	
	@NotNull(message = "Active Can't Be Null")
	private Boolean isActive;
	
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
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
