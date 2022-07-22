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
import com.lawencon.community.pojo.position.InsertPositionReq;
import com.lawencon.community.pojo.position.ShowPositionById;
import com.lawencon.community.pojo.position.ShowPositions;
import com.lawencon.community.pojo.position.UpdatePositionReq;
import com.lawencon.community.service.PositionService;

@RestController
@RequestMapping("positions")
public class PositionController {
	
	@Autowired
	private PositionService positionService;
	
//	@GetMapping
//	public ResponseEntity<ShowPositions> getAll() throws Exception{
//		ShowPositions data = positionService.showAll(null, null, null);
//		return new ResponseEntity<ShowPositions>(data, HttpStatus.OK);
//	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ShowPositionById> getById(@PathVariable String id) throws Exception{
		ShowPositionById data = positionService.showById(id);
		return new ResponseEntity<ShowPositionById>(data, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@PathVariable @Valid InsertPositionReq create) throws Exception{
		PojoInsertRes response = positionService.insert(create);
		return new ResponseEntity<PojoInsertRes>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdatePositionReq update) throws Exception {
		PojoUpdateRes response = positionService.update(update);
		return new ResponseEntity<PojoUpdateRes>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable String id) throws Exception {
		PojoDeleteRes response = positionService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(response, HttpStatus.OK);
	}
}
