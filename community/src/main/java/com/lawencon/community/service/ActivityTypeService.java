package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoDeleteResData;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.activityType.InsertActivityTypeReq;
import com.lawencon.community.pojo.activityType.PojoActivityType;
import com.lawencon.community.pojo.activityType.ShowActivityTypeById;
import com.lawencon.community.pojo.activityType.UpdateActivityTypeReq;
import com.lawencon.model.SearchQuery;

@Service
public class ActivityTypeService extends BaseCoreService<ActivityType> {

	@Autowired
	private ActivityTypeDao activityTypeDao;

	public SearchQuery<PojoActivityType> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<ActivityType> users = activityTypeDao.findAll(query, startPage, maxPage);
		List<PojoActivityType> result = new ArrayList<PojoActivityType>();

		users.getData().forEach(val -> {
			PojoActivityType act = new PojoActivityType();

			act.setId(val.getId());
			act.setTypeCode(val.getTypeCode());
			act.setTypeName(val.getTypeName());
			act.setVersion(val.getVersion());
			act.setIsActive(val.getIsActive());

			result.add(act);
		});

		SearchQuery<PojoActivityType> response = new SearchQuery<PojoActivityType>();
		response.setData(result);

		return response;
	}

	public PojoInsertRes insert(InsertActivityTypeReq data) throws Exception {
		ActivityType insert = new ActivityType();
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();

		insert.setTypeCode(data.getTypeCode());
		insert.setTypeName(data.getTypeName());
		insert.setIsActive(data.getIsActive());

		try {
			begin();

			ActivityType result = save(insert);
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

	public PojoUpdateRes update(UpdateActivityTypeReq data) throws Exception {
		ActivityType update = new ActivityType();
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();

		update.setTypeCode(data.getTypeCode());
		update.setTypeName(data.getTypeName());
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());

		try {
			begin();

			ActivityType result = save(update);
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

	public ShowActivityTypeById showById(String id) {
		ActivityType acts = activityTypeDao.getById(id);
		PojoActivityType act = new PojoActivityType();

		act.setId(acts.getId());
		act.setTypeCode(acts.getTypeCode());
		act.setTypeName(acts.getTypeName());
		act.setIsActive(acts.getIsActive());
		act.setVersion(acts.getVersion());

		ShowActivityTypeById response = new ShowActivityTypeById();
		response.setData(act);

		return response;
	}

	public PojoDeleteRes delete(String id) throws Exception {
		PojoDeleteResData resData = new PojoDeleteResData();
		PojoDeleteRes response = new PojoDeleteRes();

		try {
			begin();
			boolean result = activityTypeDao.deleteById(id);
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
