package com.lawencon.community.pojo.activity;

import java.time.LocalDateTime;

public class PojoActivityReport {
	public String no;
	public String fullName;
	public String email;
	public String fee;
	public LocalDateTime purchasedDate;
	public String paymentStatus;

	public void setNo(String no) {
		this.no = no;
	}

	public String getNo() {
		return no;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public LocalDateTime getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(LocalDateTime purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
