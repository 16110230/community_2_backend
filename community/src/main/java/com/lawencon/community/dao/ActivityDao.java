package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Activity;
import com.lawencon.community.pojo.activity.PojoActivity;
import com.lawencon.community.pojo.activity.ShowActivities;

@Repository
public class ActivityDao extends AbstractJpaDao<Activity>{
	
	public Long countAllByToday(String type) {
		StringBuilder sqlBuilder = new StringBuilder().append("SELECT COUNT(id) activity_type_count ")
				.append("FROM activity ")
				.append("WHERE DATE(created_at) = DATE(NOW()) ")
				.append("AND activity_type_id = :type");

		Long response = 0l;

		Object result = createNativeQuery(sqlBuilder.toString())	
				.setParameter("type",type)
				.getSingleResult();		
		if (result != null) {
			response = Long.valueOf(result.toString());
		}

		return response;
	}
	
	public ShowActivities getAllByType(Integer startPage,Integer maxPage, String type){
		List<PojoActivity> res = new ArrayList<PojoActivity>();
		ShowActivities response = new ShowActivities();
		StringBuilder sqlBuilder = new StringBuilder()
			.append("SELECT a.id,a.activity_title,a.fee,a.is_limit,a.is_active,acc.category_name ")
			.append("FROM activity as a ")
			.append("INNER JOIN activity_type as act ON a.activity_type_id = act.id ")
			.append("INNER JOIN activity_category as acc ON a.activity_category_id = acc.id ")
			.append("WHERE a.activity_type_id = :type");
		
		try {
			Integer size = createNativeQuery(sqlBuilder.toString())
					.setParameter("type", type)
					.getResultList().size();
			response.setCountData(size);
			
			List<?> result = createNativeQuery(sqlBuilder.toString())
				.setParameter("type", type)
				.setFirstResult(startPage)
				.setMaxResults(maxPage)
				.getResultList();				
			
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoActivity data = new PojoActivity();
					
					data.setId(objArr[0].toString());
					data.setActivityTitle(objArr[1].toString());
					data.setActivityCategoryName(objArr[5].toString());
					
					if(objArr[2] != null) {
						data.setFee(Integer.valueOf(objArr[2].toString()));
					}else {
						data.setFee(0);
					}
					if(objArr[3] != null) {
						data.setIsLimit(true);
					}else {
						data.setIsLimit(false);
					}
					
					if(objArr[4] != null) {
						data.setIsActive(true);
					}else {
						data.setIsActive(false);
					}	
					
					res.add(data);
				});
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		response.setData(res);
		return response;
	}
	
	public ShowActivities getAllByUserId(Integer startPage,Integer maxPage, String userId) {
		List<PojoActivity> res = new ArrayList<PojoActivity>();
		ShowActivities response = new ShowActivities();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT a.id, a.activity_title, a.activity_content, a.activity_category_id, a.started_at, a.ended_at, a.fee, a.quantity, a.provider, a.trainer, a.created_by, a.activity_type_id, a.file_id, ")
				.append("ac.category_name, aty.type_name ")
				.append("FROM activity as a ")
				.append("INNER JOIN activity_category as ac ON ac.id = a.activity_category_id ")
				.append("INNER JOIN activity_type as aty ON aty.id = a.activity_type_id ")
				.append("WHERE a.created_by = :userId");
		
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
					PojoActivity data = new PojoActivity();
					
					data.setId(objArr[0].toString());
					data.setActivityTitle(objArr[1].toString());
					data.setActivityContent(objArr[2].toString());
					data.setActivityCategory(objArr[3].toString());
					data.setActivityCategoryName(objArr[14].toString());
					data.setStartedAt(((Timestamp) objArr[4]).toLocalDateTime());
					data.setEndedAt(((Timestamp) objArr[5]).toLocalDateTime());
					data.setFee(Integer.valueOf(objArr[6].toString()));
					data.setQuantity(Integer.valueOf(objArr[7].toString()));
					data.setProvider(objArr[8].toString());
					data.setTrainer(objArr[9].toString());
					data.setCreatedBy(objArr[10].toString());
					data.setActivityType(objArr[11].toString());
					data.setActivityTypeName(objArr[13].toString());
					
					if (objArr[12] != null) {
						data.setFile(objArr[12].toString());
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
  
  public ShowActivities getAllByFilter(String type, String category, Integer startPage,Integer maxPage){
		List<PojoActivity> res = new ArrayList<PojoActivity>();
		ShowActivities response = new ShowActivities();
		StringBuilder sqlBuilder = new StringBuilder()
			.append("SELECT a.id, a.activity_title, a.fee, a.is_limit, a.is_active, acc.category_name, a.started_at, ")
			.append("a.ended_at, act.type_name, a.file_id ")
			.append("FROM activity as a ")
			.append("INNER JOIN activity_type as act ON a.activity_type_id = act.id ")
			.append("INNER JOIN activity_category as acc ON a.activity_category_id = acc.id ")
			.append("WHERE a.activity_type_id = :type ")
			.append("OR a.activity_category_id = :category");
		
		try {
			Integer size = createNativeQuery(sqlBuilder.toString())
					.setParameter("type", type)
					.setParameter("category", category)
					.getResultList().size();
			response.setCountData(size);
			
			List<?> result = createNativeQuery(sqlBuilder.toString())
				.setParameter("type", type)
				.setParameter("category", category)
				.setFirstResult(startPage)
				.setMaxResults(maxPage)
				.getResultList();				
			
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoActivity data = new PojoActivity();
					
					data.setId(objArr[0].toString());
					data.setActivityTitle(objArr[1].toString());
					data.setActivityCategoryName(objArr[5].toString());
					data.setStartedAt(((Timestamp) objArr[6]).toLocalDateTime());
					data.setEndedAt(((Timestamp) objArr[7]).toLocalDateTime());
					data.setActivityTypeName(objArr[8].toString());
					data.setFile(objArr[9].toString());
					
					if(objArr[2] != null) {
						data.setFee(Integer.valueOf(objArr[2].toString()));
					}else {
						data.setFee(0);
					}
					if(objArr[3] != null) {
						data.setIsLimit(true);
					}else {
						data.setIsLimit(false);
					}
					
					if(objArr[4] != null) {
						data.setIsActive(true);
					}else {
						data.setIsActive(false);
					}
					
					res.add(data);
				});
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		response.setData(res);
		return response;
	}
}
