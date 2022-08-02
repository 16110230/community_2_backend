package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.Thread;
import com.lawencon.community.model.ThreadDetails;
import com.lawencon.community.model.Users;

@Repository
public class ThreadDetailsDao extends AbstractJpaDao<ThreadDetails> {
	public List<ThreadDetails> findByThreadId(String id) {

		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT t.id threadId, u.id userId, u.full_name, td.thread_desc, td.id threadDetailId, td.created_at ")
				.append("FROM thread_details td ")
				.append("INNER JOIN thread t ON t.id = td.thread_id ")
				.append("INNER JOIN users u ON u.id = td.user_id  ")
				.append("WHERE t.id = :id");

		List<ThreadDetails> response = new ArrayList<ThreadDetails>();
		try {
			List<?> result = createNativeQuery(sqlBuilder.toString()).setParameter("id", id).getResultList();
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					ThreadDetails detail = new ThreadDetails();
					Thread header = new Thread();
					Users user = new Users();

					detail.setId(objArr[4].toString());
					header.setId(objArr[0].toString());
					user.setId(objArr[1].toString());
					user.setFullName(objArr[2].toString());
					detail.setThreadDesc(objArr[3].toString());
					detail.setCreatedAt(((Timestamp) objArr[5]).toLocalDateTime());

					detail.setThread(header);
					detail.setUser(user);

					response.add(detail);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
	public boolean deleteByThreadId(String id) {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("DELETE FROM thread_details td ")
				.append("WHERE td.thread_id = :threadId");
		
		int result = createNativeQuery(sqlBuilder.toString())
				.setParameter("threadId", id)
				.executeUpdate();
		
		return result > 0;
	}
}
