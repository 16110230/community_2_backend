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
import com.lawencon.community.pojo.threadDetails.InsertThreadDetailsReq;
import com.lawencon.community.pojo.threadDetails.PojoThreadDetails;
import com.lawencon.community.pojo.threadDetails.ShowThreadDetailById;
import com.lawencon.community.pojo.threadDetails.ShowThreadDetails;
import com.lawencon.community.pojo.threadDetails.UpdateThreadDetailsReq;
import com.lawencon.community.service.ThreadDetailService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("thread-details")
public class ThreadDetailController {

	@Autowired
	private ThreadDetailService threadDetailService;
	
	@GetMapping
	public ResponseEntity<?> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoThreadDetails> result = threadDetailService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping({"thread/{id}"})
	public ResponseEntity<?> getAllByThread(@PathVariable("id") String id) throws Exception {
		ShowThreadDetails result = threadDetailService.showByThreadId(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ShowThreadDetailById> getById(@PathVariable("id") String id) throws Exception {
		ShowThreadDetailById result = threadDetailService.showById(id);
		return new ResponseEntity<ShowThreadDetailById>(result, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@RequestBody @Valid InsertThreadDetailsReq data) throws Exception {
		PojoInsertRes result = threadDetailService.insert(data);
		return new ResponseEntity<PojoInsertRes>(result, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateThreadDetailsReq data) throws Exception {
		PojoUpdateRes result = threadDetailService.update(data);
		return new ResponseEntity<PojoUpdateRes>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable("id") String id) throws Exception {
		PojoDeleteRes result = threadDetailService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(result, HttpStatus.OK);
	}
}
