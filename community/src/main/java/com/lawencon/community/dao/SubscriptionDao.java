package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Subscription;
import com.lawencon.community.pojo.subscriptions.PojoSubscription;
import com.lawencon.community.pojo.subscriptions.ShowSubscriptions;

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
	
	public ShowSubscriptions getAllByUserId(Integer startPage,Integer maxPage, String userId) {
		List<PojoSubscription> res = new ArrayList<PojoSubscription>();
		ShowSubscriptions response = new ShowSubscriptions();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT s.id, s.subscription_code, sc.description, sc.price , s.created_at, s.expired_date, ")
				.append("s.is_active, s.is_approved ")
				.append("FROM subscriptions as s ")
				.append("INNER JOIN subscriptions_category as sc ON s.subs_category_id = sc.id ")
				.append("WHERE s.created_by = :userId");
		
		try {
			Integer size = createNativeQuery(sqlBuilder.toString())
					.setParameter("userId", userId)
					.getResultList().size();
			response.setCountData(size);
			
			List<?> result =createNativeQuery(sqlBuilder.toString())
					.setParameter("userId", userId)
					.setFirstResult(startPage)
					.setMaxResults(maxPage)
					.getResultList();
			
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoSubscription data = new PojoSubscription();
					
					data.setId(objArr[0].toString());
					data.setSubscriptionCode(objArr[1].toString());
					data.setSubscriptionCategory(objArr[2].toString());
					data.setAmount(Long.valueOf(objArr[3].toString()));
					data.setOrderDate(((Timestamp) objArr[4]).toLocalDateTime());
					if(objArr[5] != null ) {
						data.setExpiredData(((Timestamp) objArr[5]).toLocalDateTime());	
					}
					if(objArr[6] != null) {
						data.setIsActive(Boolean.valueOf(objArr[6].toString()));
					}
					if(objArr[7] != null) {
						data.setIsApproved(Boolean.valueOf(objArr[7].toString()));
					}
					
					
					res.add(data);
				});
			}
      
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setData(res);
		return response;
	}
	
	public ShowSubscriptions getAllUnApproved(Integer startPage,Integer maxPage) {
		List<PojoSubscription> res = new ArrayList<PojoSubscription>();
		ShowSubscriptions response = new ShowSubscriptions();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT s.id, s.subscription_code, sc.description, sc.price , s.created_at, s.expired_date, ")
				.append("s.is_active, s.is_approved, u.full_name, s.file_id ")
				.append("FROM subscriptions as s ")
				.append("INNER JOIN subscriptions_category as sc ON s.subs_category_id = sc.id ")
				.append("INNER JOIN users as u ON s.user_id = u.id ")
				.append("WHERE s.is_approved IS NULL");
		
		try {
			Integer size = createNativeQuery(sqlBuilder.toString())
					.getResultList().size();
			response.setCountData(size);
			
			List<?> result =createNativeQuery(sqlBuilder.toString())
					.setFirstResult(startPage)
					.setMaxResults(maxPage)
					.getResultList();
			
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoSubscription data = new PojoSubscription();
					
					data.setId(objArr[0].toString());
					data.setSubscriptionCode(objArr[1].toString());
					data.setSubscriptionCategory(objArr[2].toString());
					data.setAmount(Long.valueOf(objArr[3].toString()));
					data.setOrderDate(((Timestamp) objArr[4]).toLocalDateTime());
					if(objArr[5] != null ) {
						data.setExpiredData(((Timestamp) objArr[5]).toLocalDateTime());	
					}
					if(objArr[6] != null) {
						data.setIsActive(Boolean.valueOf(objArr[6].toString()));
					}
					if(objArr[7] != null) {
						data.setIsApproved(Boolean.valueOf(objArr[7].toString()));
					}
					data.setFullName(objArr[8].toString());
					if(objArr[9] != null) {
						data.setFile(objArr[9].toString());
					}
					
					
					res.add(data);
				});
			}
      
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setData(res);
		return response;
	}
	
	public ShowSubscriptions getAllApproved(Integer startPage,Integer maxPage) {
		List<PojoSubscription> res = new ArrayList<PojoSubscription>();
		ShowSubscriptions response = new ShowSubscriptions();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT s.id, s.subscription_code, sc.description, sc.price , s.created_at, s.expired_date, ")
				.append("s.is_active, s.is_approved, u.full_name ")
				.append("FROM subscriptions as s ")
				.append("INNER JOIN subscriptions_category as sc ON s.subs_category_id = sc.id ")
				.append("INNER JOIN users as u ON s.user_id = u.id ")
				.append("WHERE s.is_approved IS NOT NULL");
		
		try {
			Integer size = createNativeQuery(sqlBuilder.toString())
					.getResultList().size();
			response.setCountData(size);
			
			List<?> result =createNativeQuery(sqlBuilder.toString())
					.setFirstResult(startPage)
					.setMaxResults(maxPage)
					.getResultList();
			
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoSubscription data = new PojoSubscription();
					
					data.setId(objArr[0].toString());
					data.setSubscriptionCode(objArr[1].toString());
					data.setSubscriptionCategory(objArr[2].toString());
					data.setAmount(Long.valueOf(objArr[3].toString()));
					data.setOrderDate(((Timestamp) objArr[4]).toLocalDateTime());
					if(objArr[5] != null ) {
						data.setExpiredData(((Timestamp) objArr[5]).toLocalDateTime());	
					}
					if(objArr[6] != null) {
						data.setIsActive(Boolean.valueOf(objArr[6].toString()));
					}
					if(objArr[7] != null) {
						data.setIsApproved(Boolean.valueOf(objArr[7].toString()));
					}
					data.setFullName(objArr[8].toString());
					
					
					res.add(data);
				});
			}
      
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setData(res);
		return response;
	}
}
