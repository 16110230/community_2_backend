package com.lawencon.community.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.UserSubscription;
import com.lawencon.community.model.Users;

@Repository
public class UserSubscriptionDao extends AbstractJpaDao<UserSubscription>{
	@Autowired
	private UsersDao userDao;
	
	public UserSubscription findByUserId(String id) {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT s.id, s.version, u.id ")
				.append("FROM user_subscription s ")
				.append("INNER JOIN users u ON s.user_id = u.id ")
				.append("WHERE u.id = :id ")
				.append("LIMIT 1");
		
		Object result = createNativeQuery(sqlBuilder.toString())
				.setParameter("id", id)
				.getSingleResult();
		
		UserSubscription response = new UserSubscription();
		if(result != null) {
			Object[] objArr = (Object[]) result;
			response.setId(objArr[0].toString());
			response.setVersion(Integer.valueOf(objArr[1].toString()));
			
			Users user = userDao.getById(objArr[2].toString());
			response.setUser(user);
		}
		
		return response;
	}
}
