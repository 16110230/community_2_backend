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
import com.lawencon.community.pojo.activityDetails.InsertActivityDetailsReq;
import com.lawencon.community.pojo.activityDetails.PojoActivityDetails;
import com.lawencon.community.pojo.activityDetails.ShowActivityDetailById;
import com.lawencon.community.pojo.activityDetails.UpdateActivityDetailsReq;
import com.lawencon.community.service.ActivityDetailService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("activity-details")
public class ActivityDetailsController {

	@Autowired
	private ActivityDetailService activityDetailService;

	@GetMapping
	public ResponseEntity<?> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoActivityDetails> result = activityDetailService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<ShowActivityDetailById> getById(@PathVariable("id") String id) throws Exception {
		ShowActivityDetailById result = activityDetailService.showById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@RequestBody @Valid InsertActivityDetailsReq data) throws Exception {
		PojoInsertRes result = activityDetailService.insert(data);
		return new ResponseEntity<PojoInsertRes>(result, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateActivityDetailsReq data) throws Exception {
		PojoUpdateRes result = activityDetailService.update(data);
		return new ResponseEntity<PojoUpdateRes>(result, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable("id") String id) throws Exception {
		PojoDeleteRes data = activityDetailService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(data, HttpStatus.OK);
	}
}
