package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.UserRole;

@Repository
public class UserRoleDao extends AbstractJpaDao<UserRole>{
	public UserRole getIdByRoleCode(String roleCode) {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT id, role_name, role_code ")
				.append("FROM user_role ")
				.append("WHERE role_code = :roleCode");
		
		Object result = createNativeQuery(sqlBuilder.toString())
				.setParameter("roleCode", roleCode)
				.getSingleResult();
		
		UserRole response = new UserRole();
		
		if(result != null) {
			Object[] objArr = (Object[]) result;
			response.setId(objArr[0].toString());
			response.setRoleName(objArr[1].toString());
			response.setRoleCode(objArr[2].toString());
		}
		
		return response;
	}
}
