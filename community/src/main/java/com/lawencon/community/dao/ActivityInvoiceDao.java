package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.ThreadCategoryType;
import com.lawencon.community.model.ActivityInvoice;

@Repository
public class ActivityInvoiceDao extends AbstractJpaDao<ActivityInvoice>{
	
	public Long countAllInvoicePending(){
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(id) pending_count ")
				.append("FROM activity_invoice")
				.append("WHERE is_approved = false");
		
		Long response = 0l;
		
		Object result = createNativeQuery(sqlBuilder.toString())
				.getSingleResult();
		
		if(result != null) {
			response = Long.valueOf(result.toString());
		}
		
		
		return response;
	}
	
	public Long countAllInvoicePendingByActivityType(String code){
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(ai.id) pending_count ")
				.append("FROM activity_invoice as ai ")
				.append("INNER JOIN activity as a ON ai.activity_id = a.id ")	
				.append("INNER JOIN activity_type as at ON a.activity_type_id = at.id ")
				.append("WHERE ai.is_approved IS NULL ")
				.append("AND at.type_name = :code");
		
		Long response = 0l;
		
		Object result = createNativeQuery(sqlBuilder.toString())
				.setParameter("code",code)
				.getSingleResult();
		
		if(result != null) {
			response = Long.valueOf(result.toString());
		}
		
		return response;
	}
	
	public List<ActivityInvoice> getByActivity(String id){
		List<ActivityInvoice> response = new ArrayList<ActivityInvoice>();
		StringBuilder sqlBuilder = new StringBuilder()
			.append("SELECT id ")
			.append("FROM activity_invoice ")
			.append("WHERE activity_id = :id");
		
		try {
			List<?> result = createNativeQuery(sqlBuilder.toString())
				.setParameter("id",id)
				.getResultList();
			
			if(result != null) {
				result.forEach(obj -> {
					String activityInvoiceId = (String) obj;
					ActivityInvoice data = new ActivityInvoice();
					
					data.setId(activityInvoiceId);
					response.add(data);
				});
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
		
	}
	
}
