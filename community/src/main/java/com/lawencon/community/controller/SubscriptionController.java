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
import com.lawencon.community.pojo.subscriptions.InsertSubscriptionReq;
import com.lawencon.community.pojo.subscriptions.PojoCheckSubs;
import com.lawencon.community.pojo.subscriptions.PojoSubscription;
import com.lawencon.community.pojo.subscriptions.ShowCheckSubs;
import com.lawencon.community.pojo.subscriptions.ShowSubscriptionById;
import com.lawencon.community.pojo.subscriptions.ShowSubscriptions;
import com.lawencon.community.pojo.subscriptions.UpdateSubscriptionReq;
import com.lawencon.community.service.SubscriptionService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("subscriptions")
public class SubscriptionController {

	@Autowired
	private SubscriptionService subscriptionService;
	
	@GetMapping
	public ResponseEntity<?> getAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoSubscription> result = subscriptionService.showAllTypePending(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		ShowSubscriptionById result = subscriptionService.showById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@RequestBody @Valid InsertSubscriptionReq data) throws Exception {
		PojoInsertRes result = subscriptionService.insert(data);
		return new ResponseEntity<PojoInsertRes>(result, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> update(@RequestBody @Valid UpdateSubscriptionReq data) throws Exception {
		PojoUpdateRes result = subscriptionService.update(data);
		return new ResponseEntity<PojoUpdateRes>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes>  delete(@PathVariable("id") String id) throws Exception {
		PojoDeleteRes result = subscriptionService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(result, HttpStatus.OK);
	}
	
	@GetMapping("validate")
	public ResponseEntity<?> getAllByValidate(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoSubscription> result = subscriptionService.showAllByValidate(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("user")
	public ResponseEntity<?> getAllByUserId( Integer startPage, Integer maxPage) throws Exception {
		ShowSubscriptions result = subscriptionService.showAllByUserId(startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("unapproved")
	public ResponseEntity<?> getAllUnApproved( Integer startPage, Integer maxPage) throws Exception {
		ShowSubscriptions result = subscriptionService.showAllUnApproved(startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("approved")
	public ResponseEntity<?> getAllApproved( Integer startPage, Integer maxPage) throws Exception {
		ShowSubscriptions result = subscriptionService.showAllApproved(startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@GetMapping("user-subs")
	public ResponseEntity<?> getUserSub() throws Exception {
		boolean result = subscriptionService.isPrem();
		PojoCheckSubs isPrem = new PojoCheckSubs();
		isPrem.setIsPremium(result);
		ShowCheckSubs data = new ShowCheckSubs();
		data.setData(isPrem);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
