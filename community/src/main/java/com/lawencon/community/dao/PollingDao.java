package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.Thread;

@Repository
public class PollingDao extends AbstractJpaDao<Polling>{

	public Polling getByThreadId(String id) {
		Polling polling = null;
		
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT p.id, p.thread_id ")
				.append("FROM polling p ")
				.append("WHERE p.thread_id = :threadId ");
		
		try {
			Object result = createNativeQuery(sqlBuilder.toString())
					.setParameter("threadId", id)
					.getSingleResult();
			
			if (result != null) {
				polling = new Polling();
				Object[] objArr = (Object[]) result;
				Thread thread = new Thread();
				
				polling.setId(objArr[0].toString());
				
				thread.setId(objArr[1].toString());
				polling.setThread(thread);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return polling;
	}
}
