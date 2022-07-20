package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
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
	
}
