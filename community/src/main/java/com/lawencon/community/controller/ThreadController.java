package com.lawencon.community.controller;

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
import com.lawencon.community.pojo.thread.InsertThreadReq;
import com.lawencon.community.pojo.thread.PojoThread;
import com.lawencon.community.pojo.thread.ShowThreadById;
import com.lawencon.community.pojo.thread.ShowThreads;
import com.lawencon.community.pojo.thread.UpdateThreadReq;
import com.lawencon.community.service.ThreadService;
import com.lawencon.model.SearchQuery;

@RestController
@RequestMapping("threads")
public class ThreadController {

	@Autowired
	private ThreadService threadService;
	
	@GetMapping
	public ResponseEntity<?> getAll(@RequestParam(required = false) String query,
			@RequestParam(required = false) Integer startPage,
			@RequestParam(required = false) Integer maxPage
			) throws Exception {
		SearchQuery<PojoThread> result = threadService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("non-login/")
	public ResponseEntity<?> getAllNoLogin(@RequestParam(required = false) String query,
			@RequestParam(required = false) Integer startPage,
			@RequestParam(required = false) Integer maxPage
			) throws Exception {
		SearchQuery<PojoThread> result = threadService.showAll(query, startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping({"article"})
	public ResponseEntity<?> getAllArticle() throws Exception {
		ShowThreads result = threadService.getArticles();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping({"user"})
	public ResponseEntity<?> getThreadByUser() throws Exception {
		ShowThreads result = threadService.showThreadForUser();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		ShowThreadById result = threadService.showById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insert(@RequestBody InsertThreadReq data) throws Exception {
		PojoInsertRes result = threadService.insert(data);
		return new ResponseEntity<PojoInsertRes>(result, HttpStatus.CREATED);
	}
	
	@PostMapping("article")
	public ResponseEntity<PojoInsertRes> insertArticle(@RequestBody InsertThreadReq data) throws Exception {
		PojoInsertRes result = threadService.insertArticle(data);
		return new ResponseEntity<PojoInsertRes>(result, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> update(@RequestBody UpdateThreadReq data) throws Exception {
		PojoUpdateRes result = threadService.update(data);
		return new ResponseEntity<PojoUpdateRes>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable("id") String id) throws Exception {
		PojoDeleteRes result = threadService.delete(id);
		return new ResponseEntity<PojoDeleteRes>(result, HttpStatus.OK);
	}
	
	@GetMapping({"articles"})
	public ResponseEntity<?> getAllArticles(String query, Integer startPage, Integer maxPage) throws Exception {
		ShowThreads result = threadService.showAllArticlesPagination(startPage,maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("profile")
	public ResponseEntity<?> getAllById(@RequestParam(required = false) Integer startPage,
			@RequestParam(required = false) Integer maxPage
			) throws Exception {
		SearchQuery<PojoThread> result = threadService.showAllByUserId(startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("bookmark")
	public ResponseEntity<?> getByBookmark(Integer startPage, Integer maxPage) throws Exception {
		ShowThreads result = threadService.showByBookmarkAndUser(startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("like")
	public ResponseEntity<?> getByLike(Integer startPage, Integer maxPage) throws Exception {
		ShowThreads result = threadService.showByLikeAndUser(startPage, maxPage);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
