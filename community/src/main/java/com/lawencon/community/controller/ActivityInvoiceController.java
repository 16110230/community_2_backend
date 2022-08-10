package com.lawencon.community.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.lawencon.community.pojo.activity.ShowActivityById;
import com.lawencon.community.pojo.activityInvoice.InsertActivityInvoiceReq;
import com.lawencon.community.pojo.activityInvoice.PojoActivityInvoice;
import com.lawencon.community.pojo.activityInvoice.ShowActivityInvoice;
import com.lawencon.community.pojo.activityInvoice.ShowActivityInvoiceById;
import com.lawencon.community.pojo.activityInvoice.UpdateActivityInvoiceReq;
import com.lawencon.community.service.ActivityInvoiceService;
import com.lawencon.community.service.ActivityService;
import com.lawencon.community.util.JasperUtil;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("activity-invoices")
public class ActivityInvoiceController {

	@Autowired
	private ActivityInvoiceService activityInvoiceService;
	
	@Autowired
	private ActivityService activityService;

	@GetMapping
	public ResponseEntity<?> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoActivityInvoice> result = activityInvoiceService.showAllByTypePending(query, startPage, maxPage);
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
	
	@GetMapping("type")
	public ResponseEntity<?> getAllByType(Integer startPage, Integer maxPage, String code) throws Exception {
		ShowActivityInvoice result = activityInvoiceService.showAllByType(startPage, maxPage, code);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("user")
	public ResponseEntity<?> getAllByUserId(Integer startPage, Integer maxPage) throws Exception {
		ShowActivityInvoice result = activityInvoiceService.showAllByUserId(startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("download")
	public ResponseEntity<?> getAllByActivity(String id) throws Exception{
		List<?> listData = (List<?>) activityInvoiceService.showById(id);
		ShowActivityById result = activityService.showById(id);
		
		Map<String, Object> map = new HashMap<>();
		map.put("activityType", result.getData().getActivityTypeName());
		
		Map<String, Object> map2 = new HashMap<>();
		map.put("activityName", result.getData().getActivityTitle());

		byte[] out = JasperUtil.responseToByteArray(listData, map, map2, "sample");
		
		String fileName = "report.pdf";
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.body(out);
	
	}
}
