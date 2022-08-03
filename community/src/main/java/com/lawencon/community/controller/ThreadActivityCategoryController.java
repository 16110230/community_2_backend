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
import com.lawencon.community.pojo.threadActivityCategory.InsertThreadActivityCategoryReq;
import com.lawencon.community.pojo.threadActivityCategory.PojoThreadActivityCategory;
import com.lawencon.community.pojo.threadActivityCategory.ShowThreadActivityCategories;
import com.lawencon.community.pojo.threadActivityCategory.ShowThreadActivityCategoryById;
import com.lawencon.community.pojo.threadActivityCategory.UpdateThreadActivityCategoryReq;
import com.lawencon.community.service.ThreadActivityCategoryService;
import com.lawencon.community.service.ThreadActivityService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("thread-activity-categories")
public class ThreadActivityCategoryController {

	@Autowired
	private ThreadActivityCategoryService threadActivityCategoryService;

	@GetMapping
	public ResponseEntity<?> getAll(String query, Integer startPage,
			Integer maxPage) throws Exception {
		SearchQuery<PojoThreadActivityCategory> result = threadActivityCategoryService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShowThreadActivityCategoryById> getById(@PathVariable String id) throws Exception {
		ShowThreadActivityCategoryById data = threadActivityCategoryService.showById(id);
		return new ResponseEntity<ShowThreadActivityCategoryById>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@RequestBody @Valid InsertThreadActivityCategoryReq create)
			throws Exception {
		PojoInsertRes response = threadActivityCategoryService.insert(create);
		return new ResponseEntity<PojoInsertRes>(response, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateThreadActivityCategoryReq update)
			throws Exception {
		PojoUpdateRes response = threadActivityCategoryService.update(update);
		return new ResponseEntity<PojoUpdateRes>(response, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable String id) throws Exception {
		PojoDeleteRes response = threadActivityCategoryService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(response, HttpStatus.OK);
	}
}
