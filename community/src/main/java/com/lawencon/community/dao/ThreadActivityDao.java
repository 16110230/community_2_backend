package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.ThreadActivityType;
import com.lawencon.community.model.ThreadActivity;
import com.lawencon.community.model.Users;

@Repository
public class ThreadActivityDao extends AbstractJpaDao<ThreadActivity>{
	
	public List<ThreadActivity> findByIdLike(){
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT  ta.user_id, ta.thread_id, ta.id")
				.append("FROM thread_activity ta")
				.append("JOIN thread_activity_category tac ON tac.id = ta.thread_activity_category_id")
				.append("WHERE  tac.thread_activity_code= :code");
		
		List<ThreadActivity> response =  new ArrayList<ThreadActivity>();
		
		try {			
			List<?> result = createNativeQuery(sqlBuilder.toString())
					.setParameter("date", ThreadActivityType.LIKE.name())
					.getResultList();
			
			result.forEach(obj -> {
				Object[] objArr = (Object[]) obj;
				ThreadActivity ta = new ThreadActivity();
				Users usr = new Users();
				
				usr.setId(objArr[0].toString());
				
				ta.setUser(usr);
				response.add(ta);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;	
	}
	
}
