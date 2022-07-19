package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.UserRole;
import com.lawencon.community.model.Users;

@Repository
public class UsersDao extends AbstractJpaDao<Users> {
	
	public Users findByUsernameAndPassword(String username, String password) {
		Users response = new Users();
		
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT u.fullName, u.username, r.role_code ")
				.append("FROM users u ")
				.append("INNER JOIN user_role r ON u.role_id = r.id ")
				.append("WHERE username = :username AND u.user_password = :password");
		
		try {			
			Object result = createNativeQuery(sqlBuilder.toString()).getSingleResult();
			if(result != null) {
				Object[] objArr = (Object[]) result;
				
				Users user = new Users();
				UserRole role = new UserRole();
				user.setFullName(objArr[0].toString());
				user.setUsername(objArr[1].toString());
				
				user.setRole(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
