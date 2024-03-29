package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.ThreadActivityCategory;

@Repository
public class ThreadActivityCategoryDao extends AbstractJpaDao<ThreadActivityCategory>{
	
	public ThreadActivityCategory findByCode(String code) {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT tac.id, tac.thread_activity_name ")
				.append("FROM thread_activity_category tac ")
				.append("WHERE tac.thread_activity_code = :code ");
		ThreadActivityCategory response = null;
		try {			
			Object result = createNativeQuery(sqlBuilder.toString())
					.setParameter("code", code)
					.getSingleResult();
			
			if(result != null) {
				response = new ThreadActivityCategory();
				Object[] objArr = (Object[]) result;
				response.setId(objArr[0].toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
