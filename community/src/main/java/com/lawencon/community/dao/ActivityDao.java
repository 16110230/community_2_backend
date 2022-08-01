package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Activity;

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
}
