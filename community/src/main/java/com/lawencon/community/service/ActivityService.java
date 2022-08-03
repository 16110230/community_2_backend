package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.dao.ActivityCategoryDao;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityCategory;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.model.File;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.activity.InsertActivityReq;
import com.lawencon.community.pojo.activity.PojoActivity;
import com.lawencon.community.pojo.activity.ShowActivities;
import com.lawencon.community.pojo.activity.ShowActivityById;
import com.lawencon.community.pojo.activity.UpdateActivityReq;
import com.lawencon.model.SearchQuery;

@Service
public class ActivityService extends BaseService<Activity>{
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private ActivityCategoryDao activityCategoryDao;
	
	@Autowired
	private ActivityTypeDao activityTypeDao;
	
	@Autowired
	private FileDao fileDao;
	
	public SearchQuery<PojoActivity> showAll(String query, Integer startPage, Integer maxPage)
			throws Exception {
		SearchQuery<Activity> users = activityDao.findAll(query, startPage, maxPage);
		List<PojoActivity> result = new ArrayList<PojoActivity>();

		users.getData().forEach(val -> {
			PojoActivity act = new PojoActivity();
			ActivityCategory actCat = activityCategoryDao.getById(val.getActivityCategory().getId());
			
			if(val.getFile() != null) {				
				File file = fileDao.getById(val.getFile().getId());
				act.setFile(file.getId());
			}

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
			act.setActivityType(val.getActivityType().getId());
			act.setActivityTypeName(val.getActivityType().getTypeName());
			
			result.add(act);
		});

		SearchQuery<PojoActivity> response = new SearchQuery<PojoActivity>();
		response.setData(result);

		return response;
	}
	
	public PojoInsertRes insert(InsertActivityReq data)throws Exception{
		Activity insert  = new Activity();
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();
		
		ActivityCategory actCat = activityCategoryDao.getById(data.getActivityCategory());
		ActivityType actType = activityTypeDao.getById(data.getActivityType());
		
		insert.setActivityTitle(data.getActivityTitle());
		insert.setActivityContent(data.getActivityContent());
		insert.setActivityCategory(actCat);
		insert.setStartedAt(data.getStartedAt());
		insert.setEndedAt(data.getEndedAt());
		insert.setFee(data.getFee());
		insert.setQuantity(data.getQuantity());
		insert.setIsLimit(data.getIsLimit());
		insert.setProvider(data.getProvider());
		insert.setTrainer(data.getTrainer());
		insert.setIsActive(true);
		insert.setActivityType(actType);
		
		
		try {
			begin();
			
			if(data.getFileName() != null) {				
				File insertFile = new File();
				insertFile.setFileName(data.getFileName());
				insertFile.setFileExt(data.getFileExt());
				insertFile.setCreatedBy(getUserId());
				insertFile.setIsActive(true);
				File resultFile = fileDao.saveNew(insertFile);
				insert.setFile(resultFile);
			}
			
			Activity result = activityDao.saveNew(insert);
      
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
	
	public PojoUpdateRes update(UpdateActivityReq data)throws Exception{
		Activity update = activityDao.getById(data.getId());
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();

		ActivityCategory  actCat = activityCategoryDao.getById(data.getActivityCategory());
		
		update.setActivityTitle(data.getActivityTitle());
		update.setActivityContent(data.getActivityContent());
		update.setActivityCategory(actCat);
		update.setStartedAt(data.getStartedAt());
		update.setEndedAt(data.getEndedAt());
		update.setFee(data.getFee());
		update.setQuantity(data.getQuantity());
		update.setIsLimit(data.getIsLimit());
		update.setProvider(data.getProvider());
		update.setTrainer(data.getTrainer());
		update.setVersion(data.getVersion());
		update.setIsActive(data.getIsActive());
		
		try {
			begin();

			Activity result = activityDao.saveNew(update);
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
		PojoDeleteRes response = new PojoDeleteRes();

		try {
			begin();
			boolean result = activityDao.deleteById(id);
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
	
	public ShowActivities showAllByCode(String query, Integer startPage, Integer maxPage, String code)
			throws Exception {
		code = code.toLowerCase();		
		
		String activityTypeId = activityTypeDao.getByCode(code);		
		List<PojoActivity> activities = activityDao.getAllByType(activityTypeId);
		ShowActivities response = new ShowActivities();
		response.setData(activities);
		
		return response;
		
	}
}
