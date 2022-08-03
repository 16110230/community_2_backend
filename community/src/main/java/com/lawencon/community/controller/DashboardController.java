package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.dashboard.ShowDashboard;
import com.lawencon.community.pojo.thread.ShowThreads;
import com.lawencon.community.service.DashboardService;


@RestController
@RequestMapping("dashboard")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping
	public ResponseEntity<?> getAll() throws Exception {
		ShowDashboard result = dashboardService.countData();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/threads")
	public ResponseEntity<?> getAllThreads() throws Exception {
		ShowThreads result = dashboardService.showThreadByNewest();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}	
}
