package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.PollingDetailsDao;
import com.lawencon.community.dao.UserPollingDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.PollingDetails;
import com.lawencon.community.model.UserPolling;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.userpolling.InsertUserPolling;
import com.lawencon.community.pojo.userpolling.PojoUserPolling;
import com.lawencon.community.pojo.userpolling.ShowUserPollingById;
import com.lawencon.model.SearchQuery;

@Service
public class UserPollingService extends BaseService<UserPolling> {
	@Autowired
	private UserPollingDao userPollingDao;

	@Autowired
	private UsersDao userDao;

	@Autowired
	private PollingDetailsDao pollingDetailsDao;

	public SearchQuery<PojoUserPolling> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<UserPolling> userPollings = userPollingDao.findAll(query, startPage, maxPage);
		List<PojoUserPolling> result = new ArrayList<PojoUserPolling>();

		userPollings.getData().forEach(val -> {
			PojoUserPolling data = new PojoUserPolling();
			Users user = userDao.getById(val.getUser().getId());
			PollingDetails details = pollingDetailsDao.getById(val.getPollingDetails().getId());

			data.setId(val.getId());
			data.setUser(user.getId());
			data.setFullName(user.getFullName());
			data.setPollingDetails(details.getId());
			data.setIsActive(val.getIsActive());
			data.setVersion(val.getVersion());

			result.add(data);
		});

		SearchQuery<PojoUserPolling> response = new SearchQuery<PojoUserPolling>();
		response.setData(result);
		response.setCount(userPollings.getCount());

		return response;
	}

	public ShowUserPollingById showById(String id) throws Exception {
		ShowUserPollingById response = new ShowUserPollingById();
		PojoUserPolling result = new PojoUserPolling();
		UserPolling polling = userPollingDao.getById(id);
		Users user = userDao.getById(polling.getUser().getId());
		PollingDetails details = pollingDetailsDao.getById(polling.getPollingDetails().getId());

		result.setId(polling.getId());
		result.setUser(user.getId());
		result.setFullName(user.getFullName());
		result.setPollingDetails(details.getId());
		result.setIsActive(polling.getIsActive());
		result.setVersion(polling.getVersion());

		response.setData(result);

		return response;
	}

	public PojoInsertRes insert(InsertUserPolling data) throws Exception {
		PojoInsertRes response = new PojoInsertRes();
		PojoInsertResData resData = new PojoInsertResData();
		UserPolling insert = new UserPolling();
		Users user = userDao.getById(getUserId());
		PollingDetails detail = pollingDetailsDao.getById(data.getPollingDetails());

		insert.setUser(user);
		insert.setPollingDetails(detail);
		insert.setIsActive(true);

		try {
			begin();
			UserPolling result = userPollingDao.saveNew(insert);
			commit();

			resData.setId(result.getId());
			resData.setMessage("Successfully add new data!");
			response.setData(resData);
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
			boolean result = pollingDetailsDao.deleteById(id);
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
