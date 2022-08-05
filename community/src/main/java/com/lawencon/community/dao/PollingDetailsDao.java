package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.PollingDetails;

@Repository
public class PollingDetailsDao extends AbstractJpaDao<PollingDetails>{
	public List<PollingDetails> findAllByPolling(String id) throws Exception {
		List<PollingDetails> response = new ArrayList<PollingDetails>();
		
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT d.id, d.polling_id, d.polling_details_name, d.is_active, d.version ")
				.append("FROM polling_details d ")
				.append("INNER JOIN polling p ON p.id = d.polling_id ")
				.append("WHERE p.id = :id");
		
		try {			
			List<?> result = createNativeQuery(sqlBuilder.toString())
					.setParameter("id", id)
					.getResultList();
			
			if(result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PollingDetails data = new PollingDetails();
					Polling polling = new Polling();
					
					data.setId(objArr[0].toString());
					polling.setId(objArr[1].toString());
					data.setPolling(polling);
					if(objArr[2] != null) {
						data.setPollingDetailsName(objArr[2].toString());						
					}
					data.setIsActive(Boolean.valueOf(objArr[3].toString()));
					data.setVersion(Integer.valueOf(objArr[4].toString()));
					
					response.add(data);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
