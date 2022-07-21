package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ThreadActivityCategoryDao;
import com.lawencon.community.dao.ThreadActivityDao;
import com.lawencon.community.dao.ThreadDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.Thread;
import com.lawencon.community.model.ThreadActivity;
import com.lawencon.community.model.ThreadActivityCategory;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoDeleteResData;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.threadActivity.InsertThreadActivityReq;
import com.lawencon.community.pojo.threadActivity.PojoThreadActivity;
import com.lawencon.community.pojo.threadActivity.ShowThreadActivityById;
import com.lawencon.community.pojo.threadActivity.UpdateThreadActivityReq;
import com.lawencon.model.SearchQuery;

@Service
public class ThreadActivityService extends BaseCoreService<ThreadActivity> {

	@Autowired
	private ThreadActivityDao threadActivityDao;
	
	@Autowired
	private ThreadDao threadDao;
	
	@Autowired
	private ThreadActivityCategoryDao threadActivityCategoryDao;
	
	@Autowired
	private UsersDao usersDao;
	
	public SearchQuery<PojoThreadActivity> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<ThreadActivity> threadActivities = threadActivityDao.findAll(query, startPage, maxPage);
		List<PojoThreadActivity> result = new ArrayList<PojoThreadActivity>();
		
		threadActivities.getData().forEach(val -> {
			PojoThreadActivity threadActivity = new PojoThreadActivity();
			Thread thread = threadDao.getById(val.getThread().getId());
			ThreadActivityCategory threadActivityCategory = threadActivityCategoryDao.getById(val.getThreadActivityCategory().getId());
			Users users = usersDao.getById(val.getUser().getId());
			
			threadActivity.setId(val.getId());
			threadActivity.setThread(thread.getId());
			threadActivity.setThreadActivityCategory(threadActivityCategory.getId());
			threadActivity.setThreadActivityCategoryName(threadActivityCategory.getThreadActivityName());
			threadActivity.setUserId(users.getId());
			threadActivity.setUserName(users.getUsername());
			threadActivity.setIsActive(val.getIsActive());
			threadActivity.setVersion(val.getVersion());
			
			result.add(threadActivity);
		});
		
		SearchQuery<PojoThreadActivity> response = new SearchQuery<PojoThreadActivity>();
		response.setData(result);
		response.setCount(threadActivities.getCount());
		
		return response;
	}
	
	public ShowThreadActivityById showById(String id) {
		ThreadActivity threadActivities = threadActivityDao.getById(id);
		PojoThreadActivity threadActivity = new PojoThreadActivity();
		
		Thread thread = threadDao.getById(threadActivities.getThread().getId());
		ThreadActivityCategory threadActivityCategory = threadActivityCategoryDao.getById(threadActivities.getThreadActivityCategory().getId());
		Users users = usersDao.getById(threadActivities.getUser().getId());
		
		threadActivity.setId(threadActivities.getId());
		threadActivity.setThread(thread.getId());
		threadActivity.setThreadActivityCategory(threadActivityCategory.getId());
		threadActivity.setUserId(users.getId());
		threadActivity.setIsActive(threadActivities.getIsActive());
		threadActivity.setVersion(threadActivities.getVersion());
		
		ShowThreadActivityById response = new ShowThreadActivityById();
		response.setData(threadActivity);
		
		return response;
	}
	
	public PojoInsertRes insert(InsertThreadActivityReq data) throws Exception {
		ThreadActivity insert = new ThreadActivity();
		Thread thread = threadDao.getById(data.getThread());
		ThreadActivityCategory threadActivityCategory = threadActivityCategoryDao.getById(data.getThreadActivityCategory());
		Users user = usersDao.getById(data.getUserId());
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();
		
		insert.setThread(thread);
		insert.setThreadActivityCategory(threadActivityCategory);
		insert.setUser(user);
		
		try {
			begin();
			
			ThreadActivity result = save(insert);
			resData.setId(result.getId());
			resData.setMessage("Successfully insert new data!");
			response.setData(resData);
			
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception();
		}
		
		return response;
	}
	
	public PojoUpdateRes update(UpdateThreadActivityReq data) throws Exception {
		ThreadActivity update = new ThreadActivity();
		Thread thread = threadDao.getById(data.getThread());
		ThreadActivityCategory threadActivityCategory = threadActivityCategoryDao.getById(data.getThreadActivityCategory());
		Users user = usersDao.getById(data.getUserId());
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();
		
		update.setThread(thread);
		update.setThreadActivityCategory(threadActivityCategory);
		update.setUser(user);
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());
		
		try {
			begin();
			
			ThreadActivity result = threadActivityDao.save(update);
			resData.setVersion(result.getVersion());
			resData.setMessage("Successfully update the data!");
			response.setData(resData);
			
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		
		return response;
	}
	
	public PojoDeleteRes delete(String id) throws Exception {
		PojoDeleteResData resData = new PojoDeleteResData();
		PojoDeleteRes response = new PojoDeleteRes();
		
		try {
			begin();
			boolean result = threadActivityDao.deleteById(id);
			commit();
			
			if(result) {				
				resData.setMessage("Successfully delete the data!");
				response.setData(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}
		
		return response;
	}
}