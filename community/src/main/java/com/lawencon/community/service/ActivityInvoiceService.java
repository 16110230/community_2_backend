package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.ActivityDao;
import com.lawencon.community.dao.ActivityInvoiceDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.model.ActivityInvoice;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.activityInvoice.InsertActivityInvoiceReq;
import com.lawencon.community.pojo.activityInvoice.PojoActivityInvoice;
import com.lawencon.community.pojo.activityInvoice.ShowActivityInvoiceById;
import com.lawencon.community.pojo.activityInvoice.UpdateActivityInvoiceReq;
import com.lawencon.model.SearchQuery;

@Service
public class ActivityInvoiceService extends BaseCoreService<ActivityInvoice> {

	@Autowired
	private ActivityInvoiceDao activityInvoiceDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private ActivityDao activityDao;

	@Autowired
	private FileDao fileDao;

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
		Users usr = usersDao.getById(data.getUser());
		File file = fileDao.getById(data.getFile());

		insert.setActivity(act);
		insert.setUser(usr);
		insert.setFile(file);
		insert.setIsApproved(data.getIsApproved());
		insert.setIsActive(true);

		try {
			begin();

			ActivityInvoice result = save(insert);
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
		ActivityInvoice update = new ActivityInvoice();
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

			ActivityInvoice result = save(update);
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

}
