package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.polling.ShowPollingDetails;
import com.lawencon.community.service.PollingDetailsService;

@RestController
@RequestMapping("polling-details")
public class PollingDetailsController {

	@Autowired
	private PollingDetailsService detailsService;
	
	@GetMapping("{id}")
	public ResponseEntity<ShowPollingDetails> getAllByPolling(@PathVariable("id") String id) throws Exception {
		ShowPollingDetails response = detailsService.getAllByPolling(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
