package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ActivityCategoryDao;
import com.lawencon.community.model.ActivityCategory;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.activityCategory.InsertActivityCategoryReq;
import com.lawencon.community.pojo.activityCategory.PojoActivityCategory;
import com.lawencon.community.pojo.activityCategory.ShowActivityCategoryById;
import com.lawencon.community.pojo.activityCategory.UpdateActivityCategoryReq;
import com.lawencon.model.SearchQuery;

@Service
public class ActivityCategoryService extends BaseCoreService<ActivityCategory> {

	@Autowired
	private ActivityCategoryDao activityCategoryDao;

	public SearchQuery<PojoActivityCategory> showAll(String query, Integer startPage, Integer maxPage)
			throws Exception {
		SearchQuery<ActivityCategory> users = activityCategoryDao.findAll(query, startPage, maxPage);
		List<PojoActivityCategory> result = new ArrayList<PojoActivityCategory>();

		users.getData().forEach(val -> {
			PojoActivityCategory actCat = new PojoActivityCategory();

			actCat.setId(val.getId());
			actCat.setCategoryCode(val.getCategoryCode());
			actCat.setCategoryName(val.getCategoryName());
			actCat.setIsActive(val.getIsActive());
			actCat.setVersion(val.getVersion());
			result.add(actCat);
		});

		SearchQuery<PojoActivityCategory> response = new SearchQuery<PojoActivityCategory>();
		response.setData(result);
		response.setCount(users.getCount());

		return response;
	}

	public ShowActivityCategoryById showById(String id) {
		ActivityCategory actCats = activityCategoryDao.getById(id);
		PojoActivityCategory actCat = new PojoActivityCategory();

		actCat.setId(actCats.getId());
		actCat.setCategoryCode(actCats.getCategoryCode());
		actCat.setCategoryName(actCats.getCategoryName());
		actCat.setIsActive(actCats.getIsActive());
		actCat.setVersion(actCats.getVersion());

		ShowActivityCategoryById response = new ShowActivityCategoryById();
		response.setData(actCat);

		return response;
	}

	public PojoInsertRes insert(InsertActivityCategoryReq data) throws Exception {
		ActivityCategory insert = new ActivityCategory();
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();

		insert.setCategoryCode(data.getCategoryCode());
		insert.setCategoryName(data.getCategoryName());
		insert.setIsActive(data.getIsActive());

		try {
			begin();

			ActivityCategory result = activityCategoryDao.saveNew(insert);
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

	public PojoUpdateRes update(UpdateActivityCategoryReq data) throws Exception {
		
		ActivityCategory update = activityCategoryDao.getById(data.getId());
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();

		update.setCategoryCode(data.getCategoryCode());
		update.setCategoryName(data.getCategoryName());
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());

		try {
			begin();

			ActivityCategory result = activityCategoryDao.saveNew(update);
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
			boolean result = activityCategoryDao.deleteById(id);
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
