package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dao.UserSubscriptionDao;
import com.lawencon.community.model.UserSubscription;
import com.lawencon.community.pojo.usersubscription.PojoUserSubscription;
import com.lawencon.community.pojo.usersubscription.ShowUserSubscriptionById;
import com.lawencon.model.SearchQuery;

@Service
public class UserSubscriptionService extends BaseCoreService<UserSubscription> {
	@Autowired
	private UserSubscriptionDao userSubsDao;
	
	public SearchQuery<PojoUserSubscription> showAll(String query, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<UserSubscription> userSubs = userSubsDao.findAll(query, startPage, maxPage);
		List<PojoUserSubscription> result = new ArrayList<PojoUserSubscription>();
		
		userSubs.getData().forEach(val -> {
			PojoUserSubscription data = new PojoUserSubscription();
			data.setId(val.getId());
			data.setExpiredDate(val.getExpiredDate());
			data.setUser(val.getUser().getId());
			data.setFullName(val.getUser().getFullName());
			data.setIsActive(val.getIsActive());
			data.setVersion(val.getVersion());
			
			result.add(data);
		});
		
		SearchQuery<PojoUserSubscription> response = new SearchQuery<PojoUserSubscription>();
		response.setData(result);
		response.setCount(userSubs.getCount());
		
		return response;
	}
	
	public ShowUserSubscriptionById showById(String id) throws Exception {
		ShowUserSubscriptionById response = new ShowUserSubscriptionById();
		UserSubscription userSub = userSubsDao.getById(id);
		PojoUserSubscription result = new PojoUserSubscription();
		
		result.setId(userSub.getId());
		result.setUser(userSub.getUser().getId());
		result.setFullName(userSub.getUser().getFullName());
		result.setExpiredDate(userSub.getExpiredDate());
		result.setIsActive(userSub.getIsActive());
		result.setVersion(userSub.getVersion());
		
		response.setData(result);
		
		return response;
	}
}
