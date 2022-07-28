package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.usersubscription.PojoUserSubscription;
import com.lawencon.community.pojo.usersubscription.ShowUserSubscriptionById;
import com.lawencon.community.service.UserSubscriptionService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("user-subscriptions")
public class UserSubscriptionController {

	@Autowired
	private UserSubscriptionService userSubscriptionService;
	
	@GetMapping
	public ResponseEntity<?> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoUserSubscription> result = userSubscriptionService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		ShowUserSubscriptionById result = userSubscriptionService.showById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
