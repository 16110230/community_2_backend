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
import com.lawencon.community.pojo.activityCategory.InsertActivityCategoryReq;
import com.lawencon.community.pojo.activityCategory.PojoActivityCategory;
import com.lawencon.community.pojo.activityCategory.ShowActivityCategoryById;
import com.lawencon.community.pojo.activityCategory.UpdateActivityCategoryReq;
import com.lawencon.community.service.ActivityCategoryService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("activity-categories")
public class ActivityCategoryController {

	@Autowired
	private ActivityCategoryService activityCategoryService;

	@GetMapping
	public ResponseEntity<?> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoActivityCategory> result = activityCategoryService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<ShowActivityCategoryById> getById(@PathVariable("id") String id) throws Exception {
		ShowActivityCategoryById result = activityCategoryService.showById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@RequestBody @Valid InsertActivityCategoryReq data) throws Exception {
		PojoInsertRes result = activityCategoryService.insert(data);
		return new ResponseEntity<PojoInsertRes>(result, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<PojoUpdateRes> update(@RequestBody UpdateActivityCategoryReq data) throws Exception {
		PojoUpdateRes result = activityCategoryService.update(data);
		return new ResponseEntity<PojoUpdateRes>(result, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable("id") String id) throws Exception {
		PojoDeleteRes data = activityCategoryService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(data, HttpStatus.OK);
	}

}
