package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.UserPolling;

@Repository
public class UserPollingDao extends AbstractJpaDao<UserPolling> {

	public Boolean getByIdUser(String id, String pollingid) {
		Boolean response = false;
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT up.id userPollingId ")
				.append("FROM user_polling up ")
				.append("JOIN polling_details pd ON pd.id = up.polling_details_id ")
				.append("JOIN polling p ON p.id = pd.polling_id ")
				.append("WHERE up.user_id = :userid ")
				.append("AND p.id = :pollingid ");
		try {
			Object result = createNativeQuery(sqlBuilder.toString())
					.setParameter("userid", id)
					.setParameter("pollingid", pollingid)
					.getSingleResult();

			if (result != null) {
				response = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

}
