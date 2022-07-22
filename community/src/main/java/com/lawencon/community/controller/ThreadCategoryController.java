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
import com.lawencon.community.pojo.threadCategory.InsertThreadCategoryReq;
import com.lawencon.community.pojo.threadCategory.ShowThreadCategories;
import com.lawencon.community.pojo.threadCategory.ShowThreadCategoryById;
import com.lawencon.community.pojo.threadCategory.UpdateThreadCategoryReq;
import com.lawencon.community.service.ThreadCategoryService;

@RestController
@RequestMapping("thread-categories")
public class ThreadCategoryController {

	@Autowired
	private ThreadCategoryService threadCategoryService;

//	@GetMapping
//	public ResponseEntity<ShowThreadCategories> getAll() throws Exception {
//		ShowThreadCategories data = threadCategoryService.showAll(null, null, null);
//		return new ResponseEntity<ShowThreadCategories>(data, HttpStatus.OK);
//	}

	@GetMapping("/{id}")
	public ResponseEntity<ShowThreadCategoryById> getById(@PathVariable String id) throws Exception {
		ShowThreadCategoryById data = threadCategoryService.showById(id);
		return new ResponseEntity<ShowThreadCategoryById>(data, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@PathVariable @Valid InsertThreadCategoryReq create) throws Exception {
		PojoInsertRes response = threadCategoryService.insert(create);
		return new ResponseEntity<PojoInsertRes>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateThreadCategoryReq update) throws Exception {
		PojoUpdateRes response = threadCategoryService.update(update);
		return new ResponseEntity<PojoUpdateRes>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable String id) throws Exception {
		PojoDeleteRes response = threadCategoryService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(response, HttpStatus.OK);
	}
}
