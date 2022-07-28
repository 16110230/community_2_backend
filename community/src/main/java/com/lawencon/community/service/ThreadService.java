package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.ThreadCategoryDao;
import com.lawencon.community.dao.ThreadDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Thread;
import com.lawencon.community.model.ThreadCategory;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.thread.InsertThreadReq;
import com.lawencon.community.pojo.thread.PojoThread;
import com.lawencon.community.pojo.thread.ShowThreadById;
import com.lawencon.community.pojo.thread.UpdateThreadReq;
import com.lawencon.model.SearchQuery;

@Service
public class ThreadService extends BaseCoreService<Thread> {

	@Autowired
	private ThreadDao threadDao;

	@Autowired
	private ThreadCategoryDao threadCategoryDao;

	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private UsersDao userDao;
	
	@Autowired
	private PollingService pollingService;

	public SearchQuery<PojoThread> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Thread> threads = threadDao.findAll(query, startPage, maxPage, "threadTitle", "threadContent");
		List<PojoThread> result = new ArrayList<PojoThread>();

		threads.getData().forEach(val -> {
			PojoThread thread = new PojoThread();
			ThreadCategory threadCategory = threadCategoryDao.getById(val.getThreadCategory().getId());
			File file = fileDao.getById(val.getFile().getId());

			thread.setId(val.getId());
			thread.setThreadTitle(val.getThreadTitle());
			thread.setThreadContent(val.getThreadContent());
			thread.setFile(file.getId());
			thread.setThreadcategory(threadCategory.getId());
			thread.setThreadCategoryName(threadCategory.getCategoryName());
			thread.setIsActive(val.getIsActive());
			thread.setVersion(val.getVersion());

			result.add(thread);
		});

		SearchQuery<PojoThread> response = new SearchQuery<PojoThread>();
		response.setData(result);
		response.setCount(threads.getCount());

		return response;
	}

	public ShowThreadById showById(String id) {
		Thread threads = threadDao.getById(id);
		PojoThread thread = new PojoThread();

		ThreadCategory threadCategory = threadCategoryDao.getById(threads.getThreadCategory().getId());
		File file = fileDao.getById(threads.getFile().getId());

		thread.setId(threads.getId());
		thread.setThreadTitle(threads.getThreadTitle());
		thread.setThreadContent(threads.getThreadContent());
		thread.setFile(file.getId());
		thread.setThreadcategory(threadCategory.getId());
		thread.setThreadCategoryName(threadCategory.getCategoryName());
		thread.setIsActive(threads.getIsActive());
		thread.setVersion(threads.getVersion());

		ShowThreadById response = new ShowThreadById();
		response.setData(thread);

		return response;
	}

	public PojoInsertRes insert(InsertThreadReq data) throws Exception {
		Thread insert = new Thread();
		ThreadCategory threadCategory = threadCategoryDao.getById(data.getThreadCategory());
		Users user = userDao.getById(baseService.getUserId());

		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();
		
		File file = new File();
		file.setFileName(data.getFileName());
		file.setFileExt(data.getFileExt());
		file.setIsActive(data.getIsActive());
		fileDao.save(file);

		insert.setThreadTitle(data.getThreadTitle());
		insert.setThreadContent(data.getThreadContent());
		insert.setThreadCategory(threadCategory);
		insert.setIsActive(data.getIsActive());
		insert.setFile(file);
		insert.setUser(user);

		try {
			begin();

			Thread result = save(insert);
			
			if(data.getPolling() != null) {
				pollingService.insert(data.getPolling(), result.getId());
			}
			
			resData.setId(result.getId());
			resData.setMessage("Successfully insert new data!");
			response.setData(resData);

			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}

	public PojoUpdateRes update(UpdateThreadReq data) throws Exception {
		Thread update = threadDao.getById(data.getId());
		ThreadCategory threadCategory = threadCategoryDao.getById(data.getThreadCategory());

		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();

		update.setThreadTitle(data.getThreadTitle());
		update.setThreadContent(data.getThreadContent());
		update.setThreadCategory(threadCategory);
		update.setIsActive(data.getIsActive());
		update.setVersion(data.getVersion());

		try {
			begin();

			Thread result = threadDao.save(update);
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
			boolean result = threadDao.deleteById(id);
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
