package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.ThreadCategoryType;
import com.lawencon.community.model.ThreadCategory;

@Repository
public class ThreadCategoryDao extends AbstractJpaDao<ThreadCategory>{

	public ThreadCategory getCategoryCode(String code) {
		ThreadCategory threadCategory = null;
		
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT tc.id, tc.category_name, tc.category_code, tc.created_at ")
				.append("FROM thread_category tc ")
				.append("WHERE category_code = :code ");
		
		try {
			Object result = createNativeQuery(sqlBuilder.toString())
					.setParameter("code", code)
					.getSingleResult();
			
			if (result != null) {
				threadCategory = new ThreadCategory();
				Object[] objArr = (Object[]) result;
				
				threadCategory.setId(objArr[0].toString());
				threadCategory.setCategoryName(objArr[1].toString());
				threadCategory.setCategoryCode(objArr[2].toString());
				threadCategory.setCreatedAt(((Timestamp) objArr[3]).toLocalDateTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return threadCategory;
	}
	
	public List<ThreadCategory> getCategoryUser(){
		List<ThreadCategory> response = new ArrayList<ThreadCategory>();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT tac.id, tac.category_name, tac.category_code ")
				.append("FROM thread_category tac ")
				.append("WHERE tac.category_code != :code ");
		try {
			List<?> result = createNativeQuery(sqlBuilder.toString())
					.setParameter("code", ThreadCategoryType.ART.name())
					.getResultList();

			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					ThreadCategory data = new ThreadCategory();

					data.setId(objArr[0].toString());
					data.setCategoryName(objArr[1].toString());
					data.setCategoryCode(objArr[2].toString());
					
					
					response.add(data);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
}
