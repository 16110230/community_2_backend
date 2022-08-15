package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.SubscriptionCategoryDao;
import com.lawencon.community.dao.SubscriptionDao;
import com.lawencon.community.dao.UsersDao;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Subscription;
import com.lawencon.community.model.SubscriptionCategory;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoInsertResData;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.PojoUpdateResData;
import com.lawencon.community.pojo.subscriptions.InsertSubscriptionReq;
import com.lawencon.community.pojo.subscriptions.PojoSubscription;
import com.lawencon.community.pojo.subscriptions.ShowSubscriptionById;
import com.lawencon.community.pojo.subscriptions.ShowSubscriptions;
import com.lawencon.community.pojo.subscriptions.UpdateSubscriptionReq;
import com.lawencon.community.util.GenerateCode;
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
	private GenerateCode generateCode;
	
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
		PojoSubscription result = new PojoSubscription();
		Subscription subscription = subscriptionDao.getById(id);
//		if() {
//		}
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
			insert.setSubscriptionCode(generateCode.generate());

			Subscription result = subscriptionDao.saveNew(insert);

			commit();

			resData.setId(result.getId());
			response.setMessage("Successfully add new data!");
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
		Users users = userDao.getById(getUserId());
		Integer balanceAdmin = users.getBalance();

		try {
			begin();
			
			update.setIsApproved(data.getIsApproved());
			update.setExpiredDate(LocalDateTime.now().plusDays(category.getDuration()));
			users.setBalance(balanceAdmin + category.getPrice().intValue());
			Subscription result = subscriptionDao.saveNew(update);
			userDao.saveNew(users);

			commit();

			resData.setVersion(result.getVersion());
			response.setMessage("Successfully update the data!");
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
	
	public SearchQuery<PojoSubscription> showAllTypePending(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Subscription> subscriptions = subscriptionDao.findAll(query, startPage, maxPage);
		List<PojoSubscription> result = new ArrayList<PojoSubscription>();

		subscriptions.getData().forEach(val -> {
			if (val.getIsApproved() == null) {
				PojoSubscription data = new PojoSubscription();
				Users users = userDao.getById(val.getCreatedBy());
				SubscriptionCategory subsCategory = subsCategoryDao.getById(val.getSubscriptionCategory().getId());
				File file = fileDao.getById(val.getFile().getId());
				
				data.setId(val.getId());
				data.setUser(users.getId());
				data.setFullName(users.getFullName());
				data.setSubscriptionCategory(subsCategory.getId());
				data.setIsApproved(val.getIsApproved());
				data.setIsActive(val.getIsActive());
				data.setVersion(val.getVersion());
				data.setOrderDate(val.getCreatedAt());
				data.setAmount(subsCategory.getPrice());
				data.setFile(file.getId());
				data.setSubscriptionCode(val.getSubscriptionCode());
				
				result.add(data);	
			}
			
		});

		SearchQuery<PojoSubscription> response = new SearchQuery<PojoSubscription>();
		response.setData(result);
		response.setCount(subscriptions.getCount());

		return response;
	}
	
	public SearchQuery<PojoSubscription> showAllByValidate(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<Subscription> subscriptions = subscriptionDao.findAll(query, startPage, maxPage);
		List<PojoSubscription> result = new ArrayList<PojoSubscription>();

		subscriptions.getData().forEach(val -> {
			if (val.getIsApproved() != null) {
				PojoSubscription data = new PojoSubscription();
				Users users = userDao.getById(val.getCreatedBy());
				SubscriptionCategory subsCategory = subsCategoryDao.getById(val.getSubscriptionCategory().getId());
				File file = fileDao.getById(val.getFile().getId());
				
				data.setId(val.getId());
				data.setUser(users.getId());
				data.setFullName(users.getFullName());
				data.setSubscriptionCategory(subsCategory.getId());
				data.setSubscriptionCode(val.getSubscriptionCode());
				data.setIsApproved(val.getIsApproved());
				data.setIsActive(val.getIsActive());
				data.setVersion(val.getVersion());
				data.setOrderDate(val.getCreatedAt());
				data.setAmount(subsCategory.getPrice());
				data.setFile(file.getId());
				
				result.add(data);	
			}
			
		});

		SearchQuery<PojoSubscription> response = new SearchQuery<PojoSubscription>();
		response.setData(result);
		response.setCount(subscriptions.getCount());

		return response;
	}
	
	public boolean isPrem() throws Exception {
		boolean response = subscriptionDao.isPremium(getUserId());
		
		return response;
	}
	
	public ShowSubscriptions showAllByUserId(Integer startPage, Integer maxPage) throws Exception {
		Users user = userDao.getById(getUserId());
		ShowSubscriptions response = subscriptionDao.getAllByUserId(startPage, maxPage, user.getId());
		
		return response;
	}
	
	public ShowSubscriptions showAllUnApproved(Integer startPage, Integer maxPage) throws Exception {
		ShowSubscriptions response = subscriptionDao.getAllUnApproved(startPage, maxPage);
		
		return response;
	}
	
	public ShowSubscriptions showAllApproved(Integer startPage, Integer maxPage) throws Exception {
		ShowSubscriptions response = subscriptionDao.getAllApproved(startPage, maxPage);
		
		return response;
	}
}
