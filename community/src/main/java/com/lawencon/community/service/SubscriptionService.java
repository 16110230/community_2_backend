package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.SubscriptionCategoryDao;
import com.lawencon.community.dao.SubscriptionDao;
import com.lawencon.community.dao.UserSubscriptionDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Subscription;
import com.lawencon.community.model.SubscriptionCategory;
import com.lawencon.community.model.UserSubscription;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.subscriptions.InsertSubscriptionReq;
import com.lawencon.community.pojo.subscriptions.PojoSubscription;
import com.lawencon.community.pojo.subscriptions.ShowSubscriptionById;
import com.lawencon.community.pojo.subscriptions.UpdateSubscriptionReq;
import com.lawencon.model.SearchQuery;

@Service
public class SubscriptionService extends BaseService<Subscription> {
	@Autowired
	private SubscriptionDao subscriptionDao;

	@Autowired
	private FileDao fileDao;

	@Autowired
	private SubscriptionCategoryDao subsCategoryDao;

	@Autowired
	private UsersDao userDao;

	@Autowired
	private UserSubscriptionDao userSubsDao;
	
	public SearchQuery<PojoSubscription> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Subscription> subscriptions = subscriptionDao.findAll(query, startPage, maxPage);
		List<PojoSubscription> result = new ArrayList<PojoSubscription>();

		subscriptions.getData().forEach(val -> {
			PojoSubscription data = new PojoSubscription();
			data.setId(val.getId());
			data.setUser(val.getUser().getId());
			data.setFullName(val.getUser().getFullName());
			data.setSubscriptionCategory(val.getSubscriptionCategory().getId());
			data.setIsApproved(val.getIsApproved());
			data.setIsActive(val.getIsActive());
			data.setVersion(val.getVersion());

			result.add(data);
		});

		SearchQuery<PojoSubscription> response = new SearchQuery<PojoSubscription>();
		response.setData(result);
		response.setCount(subscriptions.getCount());

		return response;
	}

	public ShowSubscriptionById showById(String id) throws Exception {
		ShowSubscriptionById response = new ShowSubscriptionById();
		Subscription subscription = subscriptionDao.getById(id);
		PojoSubscription result = new PojoSubscription();

		result.setId(subscription.getId());
		result.setUser(subscription.getUser().getId());
		result.setFullName(subscription.getUser().getFullName());
		result.setFile(subscription.getFile().getId());
		result.setIsApproved(subscription.getIsApproved());
		result.setSubscriptionCategory(subscription.getSubscriptionCategory().getId());
		result.setIsActive(subscription.getIsActive());
		result.setVersion(subscription.getVersion());

		response.setData(result);

		return response;
	}

	public PojoInsertRes insert(InsertSubscriptionReq data) throws Exception {
		PojoInsertRes response = new PojoInsertRes();
		PojoInsertResData resData = new PojoInsertResData();
		Subscription insert = new Subscription();
		File fileInsert = new File();

		Users user = userDao.getById(getUserId());
		SubscriptionCategory category = subsCategoryDao.getById(data.getSubscriptionCategory());
		fileInsert.setFileName(data.getFileName());
		fileInsert.setFileExt(data.getFileExt());

		try {
			begin();

			File fileResult = fileDao.save(fileInsert);

			insert.setFile(fileResult);
			insert.setIsActive(true);
			insert.setIsApproved(null);
			insert.setSubscriptionCategory(category);
			insert.setUser(user);

			Subscription result = subscriptionDao.saveNew(insert);

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

	public PojoUpdateRes update(UpdateSubscriptionReq data) throws Exception {
		PojoUpdateRes response = new PojoUpdateRes();
		PojoUpdateResData resData = new PojoUpdateResData();
		Subscription update = subscriptionDao.getById(data.getId());
		SubscriptionCategory category = subsCategoryDao.getById(data.getSubscriptionCategory());
		UserSubscription userSubs = new UserSubscription();

		try {
			begin();
			UserSubscription checkSubs = userSubsDao.findByUserId(update.getUser().getId());
			
			update.setId(data.getId());
			update.setIsActive(true);
			update.setVersion(data.getVersion());
			update.setIsApproved(data.getIsApproved());
			Subscription result = subscriptionDao.save(update);

	
			userSubs.setExpiredDate(LocalDateTime.now().plusDays(category.getDuration()));
			if(result.getIsApproved()) {
				if (checkSubs != null) {
					userSubs.setId(checkSubs.getId());
					userSubs.setUser(checkSubs.getUser());
					userSubs.setVersion(checkSubs.getVersion());
					userSubs.setCreatedAt(checkSubs.getCreatedAt());
					userSubs.setCreatedBy(checkSubs.getCreatedBy());
					userSubs.setUpdatedBy(getUserId());
					userSubs.setIsActive(checkSubs.getIsActive());
				} else {
					Users user = userDao.getById(update.getUser().getId());
					userSubs.setUser(user);
					userSubs.setIsActive(true);
					userSubs.setCreatedBy(getUserId());
				}
				userSubsDao.save(userSubs);				
			}

			commit();

			resData.setVersion(result.getVersion());
			resData.setMessage("Successfully update the data!");
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
			boolean result = subscriptionDao.deleteById(id);
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
