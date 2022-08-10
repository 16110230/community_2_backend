package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityInvoiceDao;
import com.lawencon.community.dao.ActivityTypeDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityInvoice;
import com.lawencon.community.model.ActivityType;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.activityInvoice.InsertActivityInvoiceReq;
import com.lawencon.community.pojo.activityInvoice.PojoActivityInvoice;
import com.lawencon.community.pojo.activityInvoice.ShowActivityInvoice;
import com.lawencon.community.pojo.activityInvoice.ShowActivityInvoiceById;
import com.lawencon.community.pojo.activityInvoice.UpdateActivityInvoiceReq;
import com.lawencon.community.util.GenerateCode;
import com.lawencon.model.SearchQuery;

@Service
public class ActivityInvoiceService extends BaseService<ActivityInvoice> {

	@Autowired
	private ActivityInvoiceDao activityInvoiceDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private ActivityTypeDao activityTypeDao;
	
	@Autowired
	private GenerateCode generateCode;

	public SearchQuery<PojoActivityInvoice> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<ActivityInvoice> users = activityInvoiceDao.findAll(query, startPage, maxPage);
		List<PojoActivityInvoice> result = new ArrayList<PojoActivityInvoice>();

		users.getData().forEach(val -> {
			PojoActivityInvoice actInv = new PojoActivityInvoice();

			Activity act = activityDao.getById(val.getActivity().getId());
			Users usr = usersDao.getById(val.getUser().getId());
			File file = fileDao.getById(val.getFile().getId());

			actInv.setId(val.getId());
			actInv.setUser(usr.getId());
			actInv.setUserName(usr.getFullName());
			actInv.setActivity(act.getId());
			actInv.setActivityName(act.getActivityTitle());
			actInv.setFile(file.getId());
			actInv.setIsApproved(val.getIsApproved());
			actInv.setIsActive(val.getIsActive());
			actInv.setVersion(val.getVersion());
			actInv.setOrderDate(val.getActivity().getCreatedAt());
			actInv.setAmount(val.getActivity().getFee());
			
			result.add(actInv);
		});

		SearchQuery<PojoActivityInvoice> response = new SearchQuery<PojoActivityInvoice>();
		response.setData(result);

		return response;
	}

	public PojoInsertRes insert(InsertActivityInvoiceReq data) throws Exception {
		ActivityInvoice insert = new ActivityInvoice();
		PojoInsertResData resData = new PojoInsertResData();
		PojoInsertRes response = new PojoInsertRes();

		Activity act = activityDao.getById(data.getActivity());
		Users usr = usersDao.getById(getUserId());
		File fileIns = new File();
		
		fileIns.setFileName(data.getFileName());
		fileIns.setFileExt(data.getFileExt());

		insert.setActivity(act);
		insert.setInvoiceCode(generateCode.generate());
		insert.setUser(usr);
		insert.setIsApproved(data.getIsApproved());
		insert.setIsActive(true);

		try {
			begin();
			File res = fileDao.save(fileIns);
			insert.setFile(res);
			ActivityInvoice result = activityInvoiceDao.saveNew(insert);
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

	public PojoUpdateRes update(UpdateActivityInvoiceReq data) throws Exception {
		ActivityInvoice update = activityInvoiceDao.getById(data.getId());
		Activity activity = activityDao.getById(update.getActivity().getId());
		PojoUpdateResData resData = new PojoUpdateResData();
		PojoUpdateRes response = new PojoUpdateRes();

		update.setId(data.getId());
		update.setIsApproved(data.getIsApproved());
		update.setIsActive(data.getIsActive());
		update.setVersion(data.getVersion());
		Users userAdmin = usersDao.getById(getUserId());
		Users userCreatedby = usersDao.getById(activity.getCreatedBy());
		Integer balanceAdmin = userAdmin.getBalance();
		Integer balanceOwner = userCreatedby.getBalance();
		Integer fee = update.getActivity().getFee();
		Integer tenPercent = fee * 10/100;
		userAdmin.setBalance(balanceAdmin + tenPercent);
		userCreatedby.setBalance(balanceOwner + (fee - tenPercent));
		
		try {
			begin();
			
			ActivityInvoice result = activityInvoiceDao.saveNew(update);
			usersDao.saveNew(userAdmin);
			usersDao.saveNew(userCreatedby);
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

	public ShowActivityInvoiceById showById(String id) {
		ActivityInvoice actInvs = activityInvoiceDao.getById(id);
		PojoActivityInvoice actInv = new PojoActivityInvoice();

		Activity act = activityDao.getById(actInvs.getActivity().getId());
		Users usr = usersDao.getById(actInvs.getUser().getId());
		File file = fileDao.getById(actInvs.getFile().getId());

		actInv.setId(actInvs.getId());
		actInv.setActivity(act.getId());
		actInv.setUser(usr.getId());
		actInv.setUserName(usr.getFullName());
		actInv.setFile(file.getId());
		actInv.setIsActive(actInvs.getIsActive());
		actInv.setVersion(actInvs.getVersion());

		ShowActivityInvoiceById response = new ShowActivityInvoiceById();
		response.setData(actInv);

		return response;
	}

	public PojoDeleteRes delete(String id) throws Exception {
		PojoDeleteRes response = new PojoDeleteRes();

		try {
			begin();
			boolean result = activityInvoiceDao.deleteById(id);
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
	
	public SearchQuery<PojoActivityInvoice> showAllByTypePending(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<ActivityInvoice> activities = activityInvoiceDao.findAll(query, startPage, maxPage);
		List<PojoActivityInvoice> result = new ArrayList<>();
		
		activities.getData().forEach(val -> {
			if (val.getIsApproved() == null) {
				PojoActivityInvoice activity = new PojoActivityInvoice();
				Activity act = activityDao.getById(val.getActivity().getId());
				ActivityType activityType = activityTypeDao.getById(val.getActivity().getActivityType().getId());
				Users users = usersDao.getById(val.getCreatedBy());
				
				if (val.getFile() != null) {
					File file = fileDao.getById(val.getFile().getId());
					activity.setFile(file.getId());				
				}
				
				activity.setId(val.getId());
				activity.setUser(users.getId());
				activity.setInvoiceCode(val.getInvoiceCode());
				activity.setUserName(users.getFullName());
				activity.setActivity(act.getId());
				activity.setActivityName(activityType.getTypeName());
				activity.setIsApproved(val.getIsApproved());
				activity.setIsActive(val.getIsActive());
				activity.setVersion(val.getVersion());
				activity.setOrderDate(val.getActivity().getCreatedAt());
				activity.setAmount(val.getActivity().getFee());
				activity.setActivityType(activityType.getTypeCode());
				
				result.add(activity);
			}
		});
		
		SearchQuery<PojoActivityInvoice> response = new SearchQuery<PojoActivityInvoice>();
		response.setData(result);
		response.setCount(activities.getCount());

		return response;
	}
	
	public ShowActivityInvoice showAllByType(Integer startPage, Integer maxPage, String code) throws Exception {
		String activityId = activityTypeDao.getByCode(code);
		ShowActivityInvoice response = activityInvoiceDao.getAllByType(startPage, maxPage, activityId);
		
		return response;
	}

	public ShowActivityInvoice showAllByUserId(Integer startPage, Integer maxPage) throws Exception {
		Users user = usersDao.getById(getUserId());
		ShowActivityInvoice response = activityInvoiceDao.getAllByUserId(startPage, maxPage, user.getId());
		
		return response;
	}
}
