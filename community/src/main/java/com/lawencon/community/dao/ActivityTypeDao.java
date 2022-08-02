package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.ActivityType;

@Repository
public class ActivityTypeDao extends AbstractJpaDao<ActivityType> {

	public String getByCode(String code) {
		String response = "";
		
		StringBuilder sqlBuilder = new StringBuilder().append("SELECT id FROM activity_type ")				
				.append("WHERE type_name = :code");
		
		try {	
			Object result = createNativeQuery(sqlBuilder.toString())	
					.setParameter("code",code)
					.getSingleResult();
			
			if (result != null) {
				response = String.valueOf(result.toString());
			}

		}catch (Exception e) {
			e.printStackTrace();
		}	
		
		return response;
	}
}
