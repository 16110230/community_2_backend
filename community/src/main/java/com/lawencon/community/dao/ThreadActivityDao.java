package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.ThreadActivityType;
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
}
