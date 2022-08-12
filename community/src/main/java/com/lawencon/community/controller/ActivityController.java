package com.lawencon.community.controller;

import java.util.HashMap;
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
import com.lawencon.community.pojo.activity.InsertActivityReq;
import com.lawencon.community.pojo.activity.PojoActivity;
import com.lawencon.community.pojo.activity.ShowActivities;
import com.lawencon.community.pojo.activity.ShowActivityById;
import com.lawencon.community.pojo.activity.UpdateActivityReq;
import com.lawencon.community.service.ActivityService;
import com.lawencon.model.SearchQuery;
import com.lawencon.util.JasperUtil;

@RestController
@RequestMapping("activities")
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private JasperUtil jasUtil;
	
	@GetMapping
	public ResponseEntity<?> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoActivity> result = activityService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ShowActivityById> getById(@PathVariable("id") String id) throws Exception {
		ShowActivityById result = activityService.showById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@RequestBody @Valid InsertActivityReq data) throws Exception {
		PojoInsertRes result = activityService.insert(data);
		return new ResponseEntity<PojoInsertRes>(result, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateActivityReq data) throws Exception {
		PojoUpdateRes result = activityService.update(data);
		return new ResponseEntity<PojoUpdateRes>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable("id") String id) throws Exception {
		PojoDeleteRes data = activityService.deleteWithActivityInvoice(id);
		return new ResponseEntity<PojoDeleteRes>(data, HttpStatus.OK);
	}
	
	@GetMapping("/type")
	public ResponseEntity<?> getAllByCode( Integer startPage, Integer maxPage,String query,String code) throws Exception {
		ShowActivities result = activityService.showAllByCode(startPage, maxPage, code);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/filter")
	public ResponseEntity<?> getAllByFilter(String type, String category, Integer startPage, Integer maxPage) throws Exception {
		ShowActivities result = activityService.showAllByFilter(type, category, startPage, maxPage);
    
    return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("user")
	public ResponseEntity<?> getAllByUserId( Integer startPage, Integer maxPage) throws Exception {
		ShowActivities result = activityService.showAllByUserId(startPage, maxPage);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("download")
	public ResponseEntity<?> getAllByActivity(String id, String startDate, String endDate) throws Exception{
		
		List<?> listData = activityService.showActivityInvoiceReport(id, startDate, endDate).getData();
		ShowActivityById result = activityService.showById(id);
		
		Map<String, Object> map = new HashMap<>();
		map.put("activityType", result.getData().getActivityTypeName());
		map.put("activityName", result.getData().getActivityTitle());

		byte[] out = jasUtil.responseToByteArray(listData, map,"activity_income_A4");
		
		String fileName = "report.pdf";
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName+ "\"")
				.header("Access-Control-Allow-Origin", "*")
				.body(out);
	}
}
