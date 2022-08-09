package com.lawencon.community.pojo;

public class PojoLoginResData {
	private String username;
	private String roleCode;
	private String token;
	private String refreshToken;
	private Boolean prem;
	
	public void setPrem(Boolean prem) {
		this.prem = prem;
	}
	public Boolean getPrem() {
		return prem;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
