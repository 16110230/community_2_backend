package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.ActivityType;

@Repository
public class ActivityTypeDao extends AbstractJpaDao<ActivityType> {

	public String getByCode(String code) {
		String response = null;
		
		StringBuilder sqlBuilder = new StringBuilder().append("SELECT * FROM activity_type ")				
				.append("WHERE type_name = :code");

		try {	
			Object result = createNativeQuery(sqlBuilder.toString())	
					.setParameter("code",code)
					.getSingleResult();
			
			if (result != null) {
				Object[] objArr = (Object[]) result;
				response = objArr[0].toString();			
			}

		}catch (Exception e) {
			e.printStackTrace();
		}	
		
		return response;
	}
}
