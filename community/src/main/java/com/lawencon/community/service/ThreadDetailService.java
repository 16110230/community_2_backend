package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.dao.ThreadDao;
import com.lawencon.community.dao.ThreadDetailsDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.Thread;
import com.lawencon.community.model.ThreadDetails;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.threadDetails.InsertThreadDetailsReq;
import com.lawencon.community.pojo.threadDetails.PojoThreadDetails;
import com.lawencon.community.pojo.threadDetails.ShowThreadDetailById;
import com.lawencon.community.pojo.threadDetails.ShowThreadDetails;
import com.lawencon.community.pojo.threadDetails.UpdateThreadDetailsReq;
import com.lawencon.model.SearchQuery;

@Service
public class ThreadDetailService extends BaseService<ThreadDetails> {

	@Autowired
	private ThreadDetailsDao threadDetailsDao;

	@Autowired
	private ThreadDao threadDao;

	@Autowired
	private UsersDao usersDao;

	public SearchQuery<PojoThreadDetails> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<ThreadDetails> threadDetails = threadDetailsDao.findAll(query, startPage, maxPage);
		List<PojoThreadDetails> result = new ArrayList<PojoThreadDetails>();

		threadDetails.getData().forEach(val -> {
			PojoThreadDetails threadDetail = new PojoThreadDetails();
			Thread thread = threadDao.getById(val.getThread().getId());
			Users user = usersDao.getById(val.getUser().getId());

			threadDetail.setId(val.getId());
			threadDetail.setThread(thread.getId());
			threadDetail.setThreadDesc(val.getThreadDesc());
			threadDetail.setUserId(user.getId());
			threadDetail.setUserName(user.getUsername());
			
			threadDetail.setIsActive(val.getIsActive());
			threadDetail.setVersion(val.getVersion());

			result.add(threadDetail);
		});

		SearchQuery<PojoThreadDetails> response = new SearchQuery<PojoThreadDetails>();
		response.setData(result);
		response.setCount(threadDetails.getCount());

		return response;
	}

	public ShowThreadDetailById showById(String id) throws Exception {
		ThreadDetails threadDetails = threadDetailsDao.getById(id);
		PojoThreadDetails threadDetail = new PojoThreadDetails();

		Thread thread = threadDao.getById(threadDetails.getThread().getId());
		Users user = usersDao.getById(threadDetails.getUser().getId());

		threadDetail.setId(threadDetails.getId());
		threadDetail.setThread(thread.getId());
		threadDetail.setThreadDesc(threadDetails.getThreadDesc());
		threadDetail.setUserId(user.getId());
		threadDetail.setUserName(user.getUsername());
		threadDetail.setIsActive(threadDetails.getIsActive());
		threadDetail.setVersion(threadDetails.getVersion());

		ShowThreadDetailById response = new ShowThreadDetailById();
		response.setData(threadDetail);

		return response;
	}
		
	public ShowThreadDetails showByThreadId(String id) throws Exception {
		ShowThreadDetails response = new ShowThreadDetails();

		List<ThreadDetails> threadDetails = threadDetailsDao.findByThreadId(id);
		List<PojoThreadDetails> result = new ArrayList<PojoThreadDetails>();


		threadDetails.forEach(val -> {
			PojoThreadDetails threadDetail = new PojoThreadDetails();
			Thread thread = threadDao.getById(val.getThread().getId());
			Users user = usersDao.getById(val.getUser().getId());

			if(user.getFile() != null) {				
				threadDetail.setUserFile(user.getFile().getId());
			}
			threadDetail.setId(val.getId());
			threadDetail.setThread(thread.getId());
			threadDetail.setThreadDesc(val.getThreadDesc());
			threadDetail.setUserId(user.getId());
			threadDetail.setUserName(user.getUsername());
			threadDetail.setCreatedAt(val.getCreatedAt());
			threadDetail.setIsActive(val.getIsActive());
			threadDetail.setVersion(val.getVersion());

			result.add(threadDetail);
		});

		response.setData(result);

		return response;
	}


	public PojoInsertRes insert(InsertThreadDetailsReq data) throws Exception {
		ThreadDetails insert = new ThreadDetails();
		Thread thread = threadDao.getById(data.getThread());
		Users user = usersDao.getById(getUserId());
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();

		insert.setThread(thread);
		insert.setThreadDesc(data.getThreadDesc());
		insert.setUser(user);

		try {
			begin();

			ThreadDetails result = threadDetailsDao.saveNew(insert);
			resData.setId(result.getId());
			response.setMessage("Your comment has been post");
			response.setData(resData);

			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception();
		}

		return response;
	}

	public PojoUpdateRes update(UpdateThreadDetailsReq data) throws Exception {
		ThreadDetails update = new ThreadDetails();
		Thread thread = threadDao.getById(data.getThread());
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();

		update.setThread(thread);
		update.setThread(thread);
		update.setThreadDesc(data.getThreadDesc());
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());

		try {
			begin();

			ThreadDetails result = threadDetailsDao.saveNew(update);
			resData.setVersion(result.getVersion());
			response.setMessage("Successfully update the data!");
			response.setData(resData);

			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception();
		}

		return response;
	}

	public PojoDeleteRes delete(String id) throws Exception {
		PojoDeleteRes response = new PojoDeleteRes();

		try {
			begin();
			boolean result = threadDetailsDao.deleteById(id);
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
