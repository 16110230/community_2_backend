package com.lawencon.community.pojo.users;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class UpdatePasswordReq {
	
	@NotBlank(message = "Old password Can't Be Null")	
	private String oldPassword;
	
	@NotBlank(message = "New Password Can't Be Null")
	@Length(min=6,max=10)
	private String newPassword;
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
