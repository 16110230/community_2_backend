package com.lawencon.community.pojo;

public class PojoErrorRes<T> {
	private T message;
	
	public void setMessage(T message) {
		this.message = message;
	}
	
	public T getMessage() {
		return message;
	}
}
