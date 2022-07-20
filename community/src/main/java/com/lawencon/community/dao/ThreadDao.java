package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Thread;

@Repository
public class ThreadDao extends AbstractJpaDao<Thread>{
	
	public long countAllNewThreadToday() {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(id) new_thread_today ")
				.append("FROM thread")
				.append("WHERE DATE(created_at) = DATE(NOW())");
		
		Long response = 0l;
		
		Object result = createNativeQuery(sqlBuilder.toString())
				.getSingleResult();
		
		if(result != null) {
			response = Long.valueOf(result.toString());
		}
		
		return response;
	}
}
