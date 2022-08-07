package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.ThreadActivityType;
import com.lawencon.community.constant.ThreadCategoryType;
import com.lawencon.community.model.Thread;
import com.lawencon.community.model.ThreadActivity;

@Repository
public class ThreadActivityDao extends AbstractJpaDao<ThreadActivity>{
	
	public Long countLikeByThreadId(Long id) {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(ta.thread_id) like_count ")
				.append("FROM thread_activity ta")
				.append("JOIN thread_activity_category tac ON tac.id = ta.thread_activity_category_id")
				.append("WHERE ta.thread.id = :threadId")
				.append("AND tac.thread_activity_code = :tacCode")
				;
		
		Long response = 0l;
		
		
		Object result = createNativeQuery(sqlBuilder.toString())
				.setParameter("threadId", id)
				.setParameter("tacCode", ThreadActivityType.LIKE.name())
				.getSingleResult();
		
		if(result != null) {
			response = Long.valueOf(result.toString());
		}
		
		
		return response;
	}
	
	public Long countBookmarkByThreadId(Long id) {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(ta.thread_id) book_count ")
				.append("FROM thread_activity ta")
				.append("JOIN thread_activity_category tac ON tac.id = ta.thread_activity_category_id")
				.append("WHERE ta.thread.id = :threadId")
				.append("AND tac.thread_activity_code = :tacCode")
				;
		
		Long response = 0l;
		
		Object result = createNativeQuery(sqlBuilder.toString())
				.setParameter("threadId", id)
				.setParameter("tacCode", ThreadActivityType.BOOKMARK.name())
				.getSingleResult();
		
		if(result != null) {
			response = Long.valueOf(result.toString());
		}
		
		
		return response;
	}
	
	public boolean deleteByThreadId(String id) {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("DELETE FROM thread_activity ta ")
				.append("WHERE ta.thread_id = :threadId");
		
		int result = createNativeQuery(sqlBuilder.toString())
				.setParameter("threadId", id)
				.executeUpdate();
		
		return result > 0;
	}

	public String getIdByThreadId(String userId, String thread, String threadActivityCategory) {
		String response = null;
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT ta.id FROM thread_activity ta ")
				.append("JOIN thread_activity_category tac ON tac.id = ta.thread_activity_category_id ")
				.append("WHERE ta.thread_id = :threadId ")
				.append("AND ta.user_id = :userId ")
				.append("AND tac.thread_activity_code = :code");
		Object result = createNativeQuery(sqlBuilder.toString())
				.setParameter("threadId", thread)
				.setParameter("userId", userId)
				.setParameter("code", threadActivityCategory)
				.getSingleResult();
		if(result != null) {
			response = result.toString();
		}
		
		return response;
	}
	
	public List<ThreadActivity> getBookmarksByUserAndThreadActivityTypeId(String userId){
		List<ThreadActivity> response = new ArrayList<ThreadActivity>();
		StringBuilder sqlBuilder = new StringBuilder()
			.append("SELECT ta.id, ta.thread_id, ta.is_Active ")
			.append("FROM thread_activity as ta")
			.append("INNER JOIN thread_activity_category as tac ON ta.thread_activity_category_id = tac.id ")
			.append("WHERE tac.thread_activity_code = :threadActivityTypeCode ")
			.append("AND ta.user_id = :userId ");
		
		try {
			List<?> result = createNativeQuery(sqlBuilder.toString())
				.setParameter("userId", userId)
				.setParameter("threadActivityTypeCode", ThreadActivityType.BOOKMARK.getCode())
				.getResultList();
			
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					ThreadActivity data = new ThreadActivity();
					Thread thread = new Thread();
					
					data.setId(objArr[0].toString());
					thread.setId(objArr[1].toString());
					data.setThread(thread);
					data.setIsActive(Boolean.valueOf(objArr[2].toString()));
				
				});
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return response;
			
	}
	
	public List<ThreadActivity> getLikesByUserAndThreadActivityTypeId(String userId){
		List<ThreadActivity> response = new ArrayList<ThreadActivity>();
		StringBuilder sqlBuilder = new StringBuilder()
			.append("SELECT ta.id, ta.thread_id, ta.is_Active ")
			.append("FROM thread_activity as ta")
			.append("INNER JOIN thread_activity_category as tac ON ta.thread_activity_category_id = tac.id ")
			.append("WHERE tac.thread_activity_code = :threadActivityTypeCode ")
			.append("AND ta.user_id = :userId ");
		
		try {
			List<?> result = createNativeQuery(sqlBuilder.toString())
				.setParameter("userId", userId)
				.setParameter("threadActivityTypeCode", ThreadActivityType.LIKE.getCode())
				.getResultList();
			
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					ThreadActivity data = new ThreadActivity();
					Thread thread = new Thread();
					
					data.setId(objArr[0].toString());
					thread.setId(objArr[1].toString());
					data.setThread(thread);
					data.setIsActive(Boolean.valueOf(objArr[2].toString()));
				
				});
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return response;
			
	}
}
