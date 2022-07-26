package com.lawencon.community.dao;

import java.sql.Timestamp;

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
				.append("SELECT s.id as sub_id, s.version, u.id as user_id, s.created_at, s.created_by, s.is_active ")
				.append("FROM user_subscription s ")
				.append("INNER JOIN users u ON s.user_id = u.id ")
				.append("WHERE u.id = :id ")
				.append("LIMIT 1");
		UserSubscription response = null;
		try {			
			Object result = createNativeQuery(sqlBuilder.toString())
					.setParameter("id", id)
					.getSingleResult();
			
			if(result != null) {
				response = new UserSubscription();
				Object[] objArr = (Object[]) result;
				response.setId(objArr[0].toString());
				response.setVersion(Integer.valueOf(objArr[1].toString()));
				
				Users user = userDao.getById(objArr[2].toString());
			
				response.setCreatedAt(((Timestamp)objArr[3]).toLocalDateTime());
				response.setCreatedBy(objArr[4].toString());
				response.setIsActive(Boolean.valueOf(objArr[5].toString()));
				response.setUser(user);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
