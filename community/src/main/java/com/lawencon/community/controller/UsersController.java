package com.lawencon.community.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.users.InsertUserReq;
import com.lawencon.community.pojo.users.PojoUsers;
import com.lawencon.community.pojo.users.ShowUserById;
import com.lawencon.community.pojo.users.UpdatePasswordReq;
import com.lawencon.community.pojo.users.UpdateUserReq;
import com.lawencon.community.service.UsersService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("users")
public class UsersController {
	@Autowired
	private UsersService userService;
	
	@GetMapping
	public ResponseEntity<?> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoUsers> result = userService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShowUserById> getById(@PathVariable String id) throws Exception {
		ShowUserById data = userService.showById(id);
		return new ResponseEntity<ShowUserById>(data, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@RequestBody @Valid InsertUserReq create) throws Exception {
		PojoInsertRes response = userService.insert(create);
		return new ResponseEntity<PojoInsertRes>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateUserReq update) throws Exception {
		PojoUpdateRes response = userService.update(update);
		return new ResponseEntity<PojoUpdateRes>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable String id) throws Exception {
		PojoDeleteRes response = userService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(response, HttpStatus.OK);
	}
	
	@PutMapping("/change-password")
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdatePasswordReq update) throws Exception {
		PojoUpdateRes response = userService.changePassword(update);
		return new ResponseEntity<PojoUpdateRes>(response, HttpStatus.OK);
	}
	
	@GetMapping("/profile")
	public ResponseEntity<ShowUserById> getUserProfile() throws Exception {
		ShowUserById data = userService.showById();
		return new ResponseEntity<ShowUserById>(data, HttpStatus.OK);
	}
}
