package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.UserRole;
import com.lawencon.community.model.Users;

@Repository
public class UsersDao extends AbstractJpaDao<Users> {
	
	public Users findByUsernameAndPassword(String username) {
		Users response = null;
		
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT u.full_name, u.username, r.role_code, u.user_password, u.id ")
				.append("FROM users u ")
				.append("INNER JOIN user_role r ON u.role_id = r.id ")
				.append("WHERE username = :username");
		
		try {			
			Object result = createNativeQuery(sqlBuilder.toString())
					.setParameter("username", username)
					.getSingleResult();
			
			if(result != null) {
				Object[] objArr = (Object[]) result;
				
				response = new Users();
				UserRole role = new UserRole();
				role.setRoleCode(username);
				response.setFullName(objArr[0].toString());
				response.setUsername(objArr[1].toString());
				role.setRoleCode(objArr[2].toString());
				response.setUserPassword(objArr[3].toString());
				response.setId(objArr[4].toString());
				
				response.setRole(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public Long countAllByToday() {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(id) users_count ")
				.append("FROM users ")
				.append("WHERE DATE(created_at) = DATE(NOW())");
		
		Long response = 0l;
		
		Object result = createNativeQuery(sqlBuilder.toString())
				.getSingleResult();
		
		if(result != null) {
			response = Long.valueOf(result.toString());
		}
		
		
		return response;
	}
	
	public String findByRoleCode(String roleCode) {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT u.id ")
				.append("FROM users u ")
				.append("INNER JOIN user_role r ON u.role_id = r.id ")
				.append("WHERE r.role_code = :roleCode");
		
		Object result = createNativeQuery(sqlBuilder.toString())
				.setParameter("roleCode", roleCode)
				.getSingleResult();
		
		String response = "";
		if(result != null) {
			response = result.toString();
		}
		
		return response;
	}
}
