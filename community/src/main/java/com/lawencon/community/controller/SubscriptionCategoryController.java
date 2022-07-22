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
import com.lawencon.community.pojo.subscategory.InsertSubsCategoryReq;
import com.lawencon.community.pojo.subscategory.ShowSubsCategoryById;
import com.lawencon.community.pojo.subscategory.UpdateSubsCategoryReq;
import com.lawencon.community.service.SubsCategoryService;

@RestController
@RequestMapping("subscription-categories")
public class SubscriptionCategoryController {
	
	@Autowired
	private SubsCategoryService subsCategoryService;
	
//	@GetMapping
//	public ResponseEntity<ShowSubsCategories> getAll() throws Exception{
//		ShowSubsCategories data = subsCategoryService.showAll(null, null, null);
//		return new ResponseEntity<ShowSubsCategories>(data, HttpStatus.OK);
//	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ShowSubsCategoryById> getById(@PathVariable String id) throws Exception{
		ShowSubsCategoryById data = subsCategoryService.showById(id);
		return new ResponseEntity<ShowSubsCategoryById>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@PathVariable @Valid InsertSubsCategoryReq create) throws Exception{
		PojoInsertRes response = subsCategoryService.insert(create);
		return new ResponseEntity<PojoInsertRes>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateSubsCategoryReq update) throws Exception {
		PojoUpdateRes response = subsCategoryService.update(update);
		return new ResponseEntity<PojoUpdateRes>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable String id) throws Exception {
		PojoDeleteRes response = subsCategoryService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(response, HttpStatus.OK);
	}
}
