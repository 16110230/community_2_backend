package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Subscription;

@Repository
public class SubscriptionDao extends AbstractJpaDao<Subscription>{
	
	public Long countAllInvoicePending(){
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(id) pending_count ")
				.append("FROM subscriptions ")
				.append("WHERE is_approved = null");
		
		Long response = 0l;
		
		Object result = createNativeQuery(sqlBuilder.toString())
				.getSingleResult();
		
		if(result != null) {
			response = Long.valueOf(result.toString());
		}
		
		
		return response;
	}
}
