package com.lawencon.community.exception;

public class InvalidLoginException extends RuntimeException {
	private static final long serialVersionUID = -536092147058751602L;

	public InvalidLoginException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public InvalidLoginException(String message) {
		super(message);
		
	}

	public InvalidLoginException(Throwable cause) {
		super(cause);
		
	}
}
