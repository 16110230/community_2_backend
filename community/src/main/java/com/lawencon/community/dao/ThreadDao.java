package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.ThreadCategoryType;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Thread;
import com.lawencon.community.model.ThreadCategory;
import com.lawencon.community.model.Users;

@Repository
public class ThreadDao extends AbstractJpaDao<Thread> {

	public long countAllNewThreadToday() {
		StringBuilder sqlBuilder = new StringBuilder().append("SELECT COUNT(id) new_thread_today ")
				.append("FROM thread").append("WHERE DATE(created_at) = DATE(NOW())");

		Long response = 0l;

		Object result = createNativeQuery(sqlBuilder.toString()).getSingleResult();

		if (result != null) {
			response = Long.valueOf(result.toString());
		}

		return response;
	}

	public List<Thread> getThreadArticle() {
		List<Thread> response = new ArrayList<Thread>();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT t.id, t.thread_title, t.thread_content, t.thread_category_id, ")
				.append("tc.category_name , t.file_id, t.user_id, u.username, t.created_at  ").append("FROM thread t ")
				.append("JOIN thread_category tc ON tc.id = t.thread_category_id ")
				.append("JOIN users u ON u.id = t.user_id ")
				.append("WHERE tc.category_name = :category ")
				.append("ORDER BY t.created_at DESC ")
				.append("LIMIT 5 ");

		try {
			List<?> result = createNativeQuery(sqlBuilder.toString())
					.setParameter("category", ThreadCategoryType.ART.getCode())
					.getResultList();

			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					Thread data = new Thread();
					ThreadCategory threadCat = new ThreadCategory();
					Users user = new Users();
					File file = new File();

					data.setId(objArr[0].toString());
					data.setThreadTitle(objArr[1].toString());
					data.setThreadContent(objArr[2].toString());
					threadCat.setId(objArr[3].toString());
					data.setThreadCategory(threadCat);
					file.setId(objArr[5].toString());
					data.setFile(file);
					user.setId(objArr[6].toString());
					data.setUser(user);
					data.setCreatedAt(((Timestamp) objArr[8]).toLocalDateTime());
					response.add(data);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}
