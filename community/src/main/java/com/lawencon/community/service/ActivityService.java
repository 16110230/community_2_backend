package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ActivityCategoryDao;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityCategory;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoDeleteResData;
import com.lawencon.community.pojo.activity.PojoActivity;
import com.lawencon.community.pojo.activity.ShowActivityById;
import com.lawencon.model.SearchQuery;

@Service
public class ActivityService extends BaseCoreService{
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private ActivityCategoryDao activityCategoryDao;
	
	public SearchQuery<PojoActivity> showAll(String query, Integer startPage, Integer maxPage)
			throws Exception {
		SearchQuery<Activity> users = activityDao.findAll(query, startPage, maxPage);
		List<PojoActivity> result = new ArrayList<PojoActivity>();

		users.getData().forEach(val -> {
			PojoActivity act = new PojoActivity();
			ActivityCategory actCat = activityCategoryDao.getById(val.getActivityCategory().getId());
			

			act.setId(val.getId());
			act.setActivityTitle(val.getActivityTitle());
			act.setActivityContent(val.getActivityContent());
			act.setActivityCategory(actCat.getId());
			act.setActivityCategoryName(actCat.getCategoryName());
			act.setStartedAt(val.getStartedAt());
			act.setEndedAt(val.getEndedAt());
			act.setFee(val.getFee());
			act.setQuantity(val.getQuantity());
			act.setIsLimit(val.getIsLimit());
			act.setProvider(val.getProvider());
			act.setTrainer(val.getTrainer());
			act.setVersion(val.getVersion());
			act.setIsActive(val.getIsActive());
			
			result.add(act);
		});

		SearchQuery<PojoActivity> response = new SearchQuery<PojoActivity>();
		response.setData(result);

		return response;
	}
	
	public ShowActivityById showById(String id) {
		Activity acts = activityDao.getById(id);
		PojoActivity act = new PojoActivity();
		ActivityCategory actCat = activityCategoryDao.getById(acts.getActivityCategory().getId());
		
		act.setId(acts.getId());
		act.setActivityTitle(acts.getActivityTitle());
		act.setActivityContent(acts.getActivityContent());
		act.setActivityCategory(actCat.getId());
		act.setActivityCategoryName(actCat.getCategoryName());
		act.setStartedAt(acts.getStartedAt());
		act.setEndedAt(acts.getEndedAt());
		act.setFee(acts.getFee());
		act.setQuantity(acts.getQuantity());
		act.setIsLimit(acts.getIsLimit());
		act.setProvider(acts.getProvider());
		act.setTrainer(acts.getTrainer());
		act.setIsActive(acts.getIsActive());
		act.setVersion(acts.getVersion());

		ShowActivityById response = new ShowActivityById();
		response.setData(act);

		return response;
	}
	
	
	
	public PojoDeleteRes delete(String id) throws Exception {
		PojoDeleteResData resData = new PojoDeleteResData();
		PojoDeleteRes response = new PojoDeleteRes();
		
		try {
			begin();
			boolean result = activityCategoryDao.deleteById(id);
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
