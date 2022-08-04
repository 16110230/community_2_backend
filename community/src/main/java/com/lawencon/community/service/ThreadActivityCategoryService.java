package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.dao.ThreadActivityCategoryDao;
import com.lawencon.community.model.ThreadActivityCategory;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.threadActivityCategory.InsertThreadActivityCategoryReq;
import com.lawencon.community.pojo.threadActivityCategory.PojoThreadActivityCategory;
import com.lawencon.community.pojo.threadActivityCategory.ShowThreadActivityCategoryById;
import com.lawencon.community.pojo.threadActivityCategory.UpdateThreadActivityCategoryReq;
import com.lawencon.model.SearchQuery;

@Service
public class ThreadActivityCategoryService extends BaseService<ThreadActivityCategory> {

	@Autowired
	private ThreadActivityCategoryDao threadActivityCategoryDao;

	public SearchQuery<PojoThreadActivityCategory> showAll(String query, Integer startPage, Integer maxPage)
			throws Exception {
		SearchQuery<ThreadActivityCategory> users = threadActivityCategoryDao.findAll(query, startPage, maxPage);
		List<PojoThreadActivityCategory> result = new ArrayList<PojoThreadActivityCategory>();

		users.getData().forEach(val -> {
			PojoThreadActivityCategory actCat = new PojoThreadActivityCategory();

			actCat.setId(val.getId());
			actCat.setThreadActivityCode(val.getThreadActivityCode());
			actCat.setThreadActivityName(val.getThreadActivityName());
			actCat.setIsActive(val.getIsActive());
			actCat.setVersion(val.getVersion());
			result.add(actCat);
		});

		SearchQuery<PojoThreadActivityCategory> response = new SearchQuery<PojoThreadActivityCategory>();
		response.setData(result);

		return response;
	}

	public ShowThreadActivityCategoryById showById(String id) {
		ThreadActivityCategory threadCats = threadActivityCategoryDao.getById(id);
		PojoThreadActivityCategory threadCat = new PojoThreadActivityCategory();

		threadCat.setId(threadCats.getId());
		threadCat.setThreadActivityCode(threadCats.getThreadActivityCode());
		threadCat.setThreadActivityName(threadCats.getThreadActivityName());
		threadCat.setIsActive(threadCats.getIsActive());
		threadCat.setVersion(threadCats.getVersion());

		ShowThreadActivityCategoryById response = new ShowThreadActivityCategoryById();
		response.setData(threadCat);

		return response;
	}

	public PojoInsertRes insert(InsertThreadActivityCategoryReq data) throws Exception {
		ThreadActivityCategory insert = new ThreadActivityCategory();
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();

		insert.setThreadActivityCode(data.getThreadActivityCode());
		insert.setThreadActivityName(data.getThreadActivityName());
		insert.setIsActive(data.getIsActive());

		try {
			begin();

			ThreadActivityCategory result = threadActivityCategoryDao.saveNew(insert);
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

	public PojoUpdateRes update(UpdateThreadActivityCategoryReq data) throws Exception {
		ThreadActivityCategory update = threadActivityCategoryDao.getById(data.getId());
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();

		update.setThreadActivityCode(data.getThreadActivityCode());
		update.setThreadActivityName(data.getThreadActivityName());
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());

		try {
			begin();

			ThreadActivityCategory result = threadActivityCategoryDao.saveNew(update);
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
			boolean result = threadActivityCategoryDao.deleteById(id);
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
