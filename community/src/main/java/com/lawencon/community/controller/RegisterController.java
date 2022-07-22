package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoEmailReq;
import com.lawencon.community.pojo.PojoVerificationCode;
import com.lawencon.community.service.UsersService;

@RestController
@RequestMapping("register")
public class RegisterController {
	@Autowired
	private UsersService userService;
	
	@PostMapping
	public ResponseEntity<PojoVerificationCode> sendVerificationCode(@RequestBody PojoEmailReq email) throws Exception{
		PojoVerificationCode response = userService.sendVerificationCode(email);
		
		return new ResponseEntity<PojoVerificationCode>(response, HttpStatus.OK);
	}
}
