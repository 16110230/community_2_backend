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
import com.lawencon.community.pojo.activityDetails.InsertActivityDetailsReq;
import com.lawencon.community.pojo.activityDetails.PojoActivityDetails;
import com.lawencon.community.pojo.activityDetails.ShowActivityDetailById;
import com.lawencon.community.pojo.activityDetails.UpdateActivityDetailsReq;
import com.lawencon.community.pojo.activityInvoice.InsertActivityInvoiceReq;
import com.lawencon.community.pojo.activityInvoice.PojoActivityInvoice;
import com.lawencon.community.pojo.activityInvoice.ShowActivityInvoice;
import com.lawencon.community.pojo.activityInvoice.ShowActivityInvoiceById;
import com.lawencon.community.pojo.activityInvoice.UpdateActivityInvoiceReq;
import com.lawencon.community.service.ActivityDetailService;
import com.lawencon.community.service.ActivityInvoiceService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("activity-invoices")
public class ActivityInvoiceController {

	@Autowired
	private ActivityInvoiceService activityInvoiceService;

	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam("query") String query, @RequestParam("startPage") Integer startPage,
			@RequestParam("maxPage") Integer maxPage) throws Exception {
		SearchQuery<PojoActivityInvoice> result = activityInvoiceService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<ShowActivityInvoiceById> getById(@PathVariable("id") String id) throws Exception {
		ShowActivityInvoiceById result = activityInvoiceService.showById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@RequestBody @Valid InsertActivityInvoiceReq data) throws Exception {
		PojoInsertRes result = activityInvoiceService.insert(data);
		return new ResponseEntity<PojoInsertRes>(result, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateActivityInvoiceReq data) throws Exception {
		PojoUpdateRes result = activityInvoiceService.update(data);
		return new ResponseEntity<PojoUpdateRes>(result, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable("id") String id) throws Exception {
		PojoDeleteRes data = activityInvoiceService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(data, HttpStatus.OK);
	}
}
