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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.threadActivity.InsertThreadActivityReq;
import com.lawencon.community.pojo.threadActivity.PojoThreadActivity;
import com.lawencon.community.pojo.threadActivity.ShowThreadActivityById;
import com.lawencon.community.pojo.threadActivity.UpdateThreadActivityReq;
import com.lawencon.community.service.ThreadActivityService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("thread-activities")
public class ThreadActivity {

	@Autowired
	private ThreadActivityService threadActivityService;
	
	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam("query") String query, @RequestParam("startPage") Integer startPage, @RequestParam("maxPage") Integer maxPage) throws Exception {
		SearchQuery<PojoThreadActivity> result = threadActivityService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		ShowThreadActivityById result = threadActivityService.showById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@RequestBody @Valid InsertThreadActivityReq data) throws Exception {
		PojoInsertRes result = threadActivityService.insert(data);
		return new ResponseEntity<PojoInsertRes>(result, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateThreadActivityReq data) throws Exception {
		PojoUpdateRes result = threadActivityService.update(data);
		return new ResponseEntity<PojoUpdateRes>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes>  delete(@PathVariable("id") String id) throws Exception {
		PojoDeleteRes result = threadActivityService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(result, HttpStatus.OK);
	}
}
