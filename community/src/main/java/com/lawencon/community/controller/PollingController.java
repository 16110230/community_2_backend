package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.polling.PojoPolling;
import com.lawencon.community.pojo.polling.ShowPollingById;
import com.lawencon.community.service.PollingService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("pollings")
public class PollingController {

	@Autowired
	private PollingService pollingService;

	@GetMapping
	public ResponseEntity<?> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoPolling> result = pollingService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		ShowPollingById result = pollingService.showById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
