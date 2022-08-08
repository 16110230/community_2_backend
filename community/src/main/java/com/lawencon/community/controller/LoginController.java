package com.lawencon.community.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.exception.InvalidLoginException;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoLoginReq;
import com.lawencon.community.pojo.PojoLoginRes;
import com.lawencon.community.pojo.PojoLoginResData;
import com.lawencon.community.service.UserSubscriptionService;
import com.lawencon.community.service.UsersService;
import com.lawencon.security.ApiSecurity;
import com.lawencon.util.JwtUtil;
import com.lawencon.util.JwtUtil.ClaimKey;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private UsersService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private ApiSecurity apiSecurity;
	
	@Autowired
	private UserSubscriptionService userSubsService;
	
	@PostMapping
	public ResponseEntity<PojoLoginRes> login(@RequestBody PojoLoginReq loginReq) throws Exception {
		try {
			apiSecurity.authenticationManagerBean().authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getUserPassword())).isAuthenticated();
		} catch (Exception e) {
			throw new InvalidLoginException("Invalid username/password");
		}
		
		Users user = userService.login(loginReq.getUsername());
		Map<String, Object> claim = new HashMap<String, Object>();
		claim.put(ClaimKey.ID.name(), user.getId());
		claim.put(ClaimKey.ROLE.name(), user.getRole().getRoleCode());
		String token = jwtUtil.generateToken(claim, Duration.ofHours(2));
		
		PojoLoginRes response = new PojoLoginRes();
		PojoLoginResData resData = new PojoLoginResData();
		
		resData.setUsername(user.getUsername());
		resData.setRoleCode(user.getRole().getRoleCode());
		resData.setToken(token);
		resData.setRefreshToken(userService.updateToken(user.getId())); 
		
		if(userSubsService.isPrem(user.getId())) {
			resData.setPrem(userSubsService.isPrem(user.getId()));
		}
		
		response.setData(resData);
		
		return new ResponseEntity<PojoLoginRes>(response, HttpStatus.OK);
	}
}
