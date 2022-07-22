package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.thread.ShowThreads;
import com.lawencon.community.service.ThreadService;

@RestController
@RequestMapping("threads")
public class ThreadController {

	@Autowired
	private ThreadService threadService;
	
	@GetMapping
	public ResponseEntity<ShowThreads> showAllThread() throws Exception {
		
	}
}
