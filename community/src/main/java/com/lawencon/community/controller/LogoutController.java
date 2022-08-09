package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.service.UsersService;

@RestController
@RequestMapping("logout")
public class LogoutController {
	@Autowired
	private UsersService userService;
	
	@PatchMapping
	public ResponseEntity<PojoUpdateRes> logout() throws Exception {
		PojoUpdateRes response = userService.logout();
		return new ResponseEntity<PojoUpdateRes>(response, HttpStatus.OK);
	}
}
