package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.dao.ThreadActivityCategoryDao;
import com.lawencon.community.dao.ThreadActivityDao;
import com.lawencon.community.dao.ThreadDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.Thread;
import com.lawencon.community.model.ThreadActivity;
import com.lawencon.community.model.ThreadActivityCategory;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
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
public class ThreadActivityService extends BaseService<ThreadActivity> {
	
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
			ThreadActivityCategory threadActivityCategory = threadActivityCategoryDao
					.getById(val.getThreadActivityCategory().getId());
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
		ThreadActivityCategory threadActivityCategory = threadActivityCategoryDao
				.getById(threadActivities.getThreadActivityCategory().getId());
		Users users = usersDao.getById(threadActivities.getUser().getId());

		threadActivity.setId(threadActivities.getId());
		threadActivity.setThread(thread.getId());
		threadActivity.setThreadActivityCategory(threadActivityCategory.getId());
		threadActivity.setThreadActivityCategoryName(threadActivityCategory.getThreadActivityName());
		threadActivity.setUserId(users.getId());
		threadActivity.setUserName(users.getFullName());
		threadActivity.setIsActive(threadActivities.getIsActive());
		threadActivity.setVersion(threadActivities.getVersion());

		ShowThreadActivityById response = new ShowThreadActivityById();
		response.setData(threadActivity);

		return response;
	}

	public PojoInsertRes insert(InsertThreadActivityReq data) throws Exception {
		ThreadActivity insert = new ThreadActivity();
		Thread thread = threadDao.getById(data.getThread());
		ThreadActivityCategory threadActivityCategory = threadActivityCategoryDao
				.findByCode(data.getThreadActivityCategory());
		System.err.println(threadActivityCategory.getId());
		Users user = usersDao.getById(getUserId());
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();

		insert.setThread(thread);
		insert.setThreadActivityCategory(threadActivityCategory);
		insert.setUser(user);
		insert.setIsActive(true);

		try {
			begin();

			ThreadActivity result = threadActivityDao.saveNew(insert);
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
		ThreadActivity update = threadActivityDao.getById(data.getId());
		Thread thread = threadDao.getById(data.getThread());
		ThreadActivityCategory threadActivityCategory = threadActivityCategoryDao
				.getById(data.getThreadActivityCategory());
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();

		update.setThread(thread);
		update.setThreadActivityCategory(threadActivityCategory);
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
		PojoDeleteRes response = new PojoDeleteRes();

		try {
			begin();
			boolean result = threadActivityDao.deleteById(id);
			commit();

			if (result) {
				response.setMessage("Successfully delete the data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}
	
	public PojoDeleteRes deleteByThreadId(InsertThreadActivityReq data) throws Exception {
		PojoDeleteRes response = new PojoDeleteRes();
		
		String id = threadActivityDao.getIdByThreadId(getUserId(),
				data.getThread(), data.getThreadActivityCategory());
		

		try {
			begin();
			boolean result = threadActivityDao.deleteById(id);
			commit();

			if (result) {
				response.setMessage("Successfully delete the data!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}
	
	
}
