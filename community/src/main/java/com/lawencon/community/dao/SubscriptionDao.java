package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
	
	public boolean isPremium(String id) throws Exception {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT s.expired_date ")
				.append("FROM subscriptions s ")
				.append("INNER JOIN users u ON s.user_id = u.id ")
				.append("WHERE u.id = :id ")
				.append("ORDER BY s.expired_date DESC");
		boolean response = false;
		
		try {
			Object result = createNativeQuery(sqlBuilder.toString())
					.setParameter("id", id)
					.getSingleResult();
			
			if(result != null) {
				LocalDateTime res = ((Timestamp) result).toLocalDateTime();
				response = res.isAfter(LocalDateTime.now());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
