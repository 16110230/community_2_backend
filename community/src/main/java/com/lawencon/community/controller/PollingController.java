package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.service.PollingService;

@RestController
@RequestMapping("pollings")
public class PollingController {

	@Autowired
	private PollingService pollingService;

}
