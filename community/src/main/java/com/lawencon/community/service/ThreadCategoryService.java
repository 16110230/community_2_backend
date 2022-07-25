package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ThreadCategoryDao;
import com.lawencon.community.model.ThreadCategory;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoDeleteResData;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.threadCategory.InsertThreadCategoryReq;
import com.lawencon.community.pojo.threadCategory.PojoThreadCategory;
import com.lawencon.community.pojo.threadCategory.ShowThreadCategoryById;
import com.lawencon.community.pojo.threadCategory.UpdateThreadCategoryReq;
import com.lawencon.model.SearchQuery;

@Service
public class ThreadCategoryService extends BaseCoreService<ThreadCategory>{
	
	@Autowired
	private ThreadCategoryDao threadCategoryDao;
	
	public SearchQuery<PojoThreadCategory> showAll(String query, Integer startPage, Integer maxPage)
			throws Exception {
		SearchQuery<ThreadCategory> users = threadCategoryDao.findAll(query, startPage, maxPage);
		List<PojoThreadCategory> result = new ArrayList<PojoThreadCategory>();

		users.getData().forEach(val -> {
			PojoThreadCategory actCat = new PojoThreadCategory();

			actCat.setId(val.getId());
			actCat.setCategoryCode(val.getCategoryCode());
			actCat.setCategoryName(val.getCategoryName());
			actCat.setIsActive(val.getIsActive());
			actCat.setVersion(val.getVersion());
			result.add(actCat);
		});

		SearchQuery<PojoThreadCategory> response = new SearchQuery<PojoThreadCategory>();
		response.setData(result);

		return response;
	}
	
	public ShowThreadCategoryById showById(String id) {
		ThreadCategory threadCats = threadCategoryDao.getById(id);
		PojoThreadCategory threadCat = new PojoThreadCategory();

		threadCat.setId(threadCats.getId());
		threadCat.setCategoryCode(threadCats.getCategoryCode());
		threadCat.setCategoryName(threadCats.getCategoryName());
		threadCat.setIsActive(threadCats.getIsActive());
		threadCat.setVersion(threadCats.getVersion());

		ShowThreadCategoryById response = new ShowThreadCategoryById();
		response.setData(threadCat);

		return response;
	}

	public PojoInsertRes insert(InsertThreadCategoryReq data) throws Exception {
		ThreadCategory insert = new ThreadCategory();
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();

		insert.setCategoryCode(data.getCategoryCode());
		insert.setCategoryName(data.getCategoryName());
		insert.setIsActive(data.getIsActive());

		try {
			begin();

			ThreadCategory result = save(insert);
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

	public PojoUpdateRes update(UpdateThreadCategoryReq data) throws Exception {
		ThreadCategory update = new ThreadCategory();
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();

		update.setCategoryCode(data.getCategoryCode());
		update.setCategoryName(data.getCategoryName());
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());

		try {
			begin();

			ThreadCategory result = save(update);
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
			boolean result = threadCategoryDao.deleteById(id);
			commit();

			if (result) {
				resData.setMessage("Successfully delete the data!");
				response.setData(resData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new Exception(e);
		}

		return response;
	}
	

}
