package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
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
import com.lawencon.community.pojo.PojoDeleteResData;
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
public class SubscriptionService extends BaseCoreService<Subscription> {
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
		result.setExpiredDate(subscription.getExpiredDate());
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
		
		Users user = userDao.getById(data.getUser());
		SubscriptionCategory category = subsCategoryDao.getById(data.getSubscriptionCategory());
		fileInsert.setFileName(data.getFileName());
		fileInsert.setFileExt(data.getFileExt());
		
		try {
			begin();
			
			File fileResult = fileDao.save(fileInsert);
			
			insert.setExpiredDate(data.getExpiredDate());
			insert.setFile(fileResult);
			insert.setIsActive(false);
			insert.setIsApproved(data.getIsApproved());
			insert.setSubscriptionCategory(category);
			insert.setUser(user);
			
			Subscription result = save(insert);
			
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
		UserSubscription checkSubs = userSubsDao.findByUserId(update.getUser().getId());
		UserSubscription userSubs = new UserSubscription();
		
		try {
			begin();
			
			update.setId(data.getId());
			update.setIsActive(true);
			update.setVersion(data.getVersion());
			update.setIsApproved(true);
			Subscription result = subscriptionDao.save(update);

			userSubs.setExpiredDate(LocalDateTime.now().plusDays(category.getDuration()));
			if(checkSubs != null) {
				userSubs.setId(checkSubs.getId());
				userSubs.setVersion(checkSubs.getVersion());
			} else {
				Users user = userDao.getById(update.getUser().getId());
				userSubs.setUser(user);
				userSubs.setIsActive(true);
			}
			userSubsDao.save(userSubs);
			
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
		PojoDeleteResData resData = new PojoDeleteResData();
		
		try {
			begin();
			boolean result = subscriptionDao.deleteById(id);
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
