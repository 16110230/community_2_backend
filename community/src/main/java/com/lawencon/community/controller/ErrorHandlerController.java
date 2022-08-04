package com.lawencon.community.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lawencon.community.exception.InvalidLoginException;
import com.lawencon.community.pojo.PojoErrorRes;

@ControllerAdvice
public class ErrorHandlerController {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleDtoValidation(MethodArgumentNotValidException e){
		List<String> messages = new ArrayList<>();
		for(FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			messages.add(fieldError.getDefaultMessage());
		}
		PojoErrorRes<List<String>> response = new PojoErrorRes<>();
		response.setMessage(messages);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidLoginException.class)
	public ResponseEntity<?> handleFailLogin(InvalidLoginException e){
		PojoErrorRes<String> response = new PojoErrorRes<>();
		response.setMessage(NestedExceptionUtils.getMostSpecificCause(e).getMessage());
		
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
}
