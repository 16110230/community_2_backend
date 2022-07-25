package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityDetailsDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityDetails;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoDeleteResData;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.activityDetails.InsertActivityDetailsReq;
import com.lawencon.community.pojo.activityDetails.PojoActivityDetails;
import com.lawencon.community.pojo.activityDetails.ShowActivityDetailById;
import com.lawencon.community.pojo.activityDetails.UpdateActivityDetailsReq;
import com.lawencon.model.SearchQuery;

@Service
public class ActivityDetailService extends BaseCoreService<ActivityDetails> {

	@Autowired
	private ActivityDetailsDao activityDetailsDao;

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private FileDao fileDao;

	public SearchQuery<PojoActivityDetails> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<ActivityDetails> users = activityDetailsDao.findAll(query, startPage, maxPage);
		List<PojoActivityDetails> result = new ArrayList<PojoActivityDetails>();

		users.getData().forEach(val -> {
			PojoActivityDetails actDet = new PojoActivityDetails();

			Activity act = activityDao.getById(val.getActivity().getId());
			Users usr = usersDao.getById(val.getUser().getId());
			File file = fileDao.getById(val.getFile().getId());

			actDet.setId(val.getId());
			actDet.setActivity(act.getId());
			actDet.setUser(usr.getId());
			actDet.setUserName(usr.getFullName());
			actDet.setFile(file.getId());
			actDet.setIsActive(val.getIsActive());
			actDet.setVersion(val.getVersion());
			result.add(actDet);
		});

		SearchQuery<PojoActivityDetails> response = new SearchQuery<PojoActivityDetails>();
		response.setData(result);

		return response;
	}
	
	public PojoInsertRes insert(InsertActivityDetailsReq data)throws Exception{
		
		ActivityDetails insert = new ActivityDetails();
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();
		
		Activity act = activityDao.getById(data.getActivity());
		Users usr = usersDao.getById(data.getUser());
		File file = fileDao.getById(data.getFile());
		
		insert.setActivity(act);
		insert.setFile(file);
		insert.setUser(usr);
		insert.setIsActive(true);
		
		try {
			begin();

			ActivityDetails result = save(insert);
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
	
	public PojoUpdateRes update(UpdateActivityDetailsReq data)throws Exception {
		ActivityDetails update = new ActivityDetails();
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();
		
		Activity act = activityDao.getById(data.getActivity());
		Users usr = usersDao.getById(data.getUser());
		File file = fileDao.getById(data.getFile());
		
		update.setActivity(act);
		update.setUser(usr);
		update.setFile(file);
		update.setIsActive(data.getIsActive());
		update.setVersion(data.getVersion());
		
		
		try {
			begin();

			ActivityDetails result = save(update);
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
	
	public ShowActivityDetailById showById(String id) {
		ActivityDetails actDets = activityDetailsDao.getById(id);
		PojoActivityDetails actDet = new PojoActivityDetails();
		
		Activity act = activityDao.getById(actDets.getActivity().getId());
		Users usr = usersDao.getById(actDets.getUser().getId());
		File file = fileDao.getById(actDets.getFile().getId());
		
		actDet.setId(actDets.getId());
		actDet.setActivity(act.getId());
		actDet.setUser(usr.getId());
		actDet.setUserName(usr.getFullName());
		actDet.setFile(file.getId());
		actDet.setIsActive(actDets.getIsActive());
		actDet.setVersion(actDets.getVersion());

		ShowActivityDetailById response = new ShowActivityDetailById();
		response.setData(actDet);

		return response;
	}
	
	public PojoDeleteRes delete(String id) throws Exception {
		PojoDeleteResData resData = new PojoDeleteResData();
		PojoDeleteRes response = new PojoDeleteRes();
		
		try {
			begin();
			boolean result = activityDetailsDao.deleteById(id);
			commit();
			
			if(result) {				
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
