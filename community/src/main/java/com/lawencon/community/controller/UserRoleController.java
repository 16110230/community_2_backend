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
import com.lawencon.community.pojo.userrole.InsertUserRoleReq;
import com.lawencon.community.pojo.userrole.ShowUserRoleById;
import com.lawencon.community.pojo.userrole.ShowUserRoles;
import com.lawencon.community.pojo.userrole.UpdateUserRoleReq;
import com.lawencon.community.service.UserRoleService;

@RestController
@RequestMapping("user-roles")
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;

//	@GetMapping
//	public ResponseEntity<ShowUserRoles> getAll() throws Exception {
//		ShowUserRoles data = userRoleService.showAll(null, null, null);
//		return new ResponseEntity<ShowUserRoles>(data, HttpStatus.OK);
//	}

	@GetMapping("/{id}")
	public ResponseEntity<ShowUserRoleById> getById(@PathVariable String id) throws Exception {
		ShowUserRoleById data = userRoleService.showById(id);
		return new ResponseEntity<ShowUserRoleById>(data, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@PathVariable @Valid InsertUserRoleReq create) throws Exception {
		PojoInsertRes response = userRoleService.insert(create);
		return new ResponseEntity<PojoInsertRes>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateUserRoleReq update) throws Exception {
		PojoUpdateRes response = userRoleService.update(update);
		return new ResponseEntity<PojoUpdateRes>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable String id) throws Exception {
		PojoDeleteRes response = userRoleService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(response, HttpStatus.OK);
	}
}
