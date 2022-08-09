package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.model.PollingDetails;
import com.lawencon.community.model.UserPolling;

@Repository
public class UserPollingDao extends AbstractJpaDao<UserPolling> {

	public Boolean getByIdUser(String id, String pollingid) {
		Boolean response = false;
		StringBuilder sqlBuilder = new StringBuilder().append("SELECT up.id userPollingId ")
				.append("FROM user_polling up ").append("JOIN polling_details pd ON pd.id = up.polling_details_id ")
				.append("JOIN polling p ON p.id = pd.polling_id ").append("WHERE up.user_id = :userid ")
				.append("AND p.id = :pollingid ");
		try {
			Object result = createNativeQuery(sqlBuilder.toString()).setParameter("userid", id)
					.setParameter("pollingid", pollingid).getSingleResult();

			if (result != null) {
				response = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public UserPolling getByPollingDetailId(String id) {
		UserPolling userPolling = null;

		StringBuilder sqlBuilder = new StringBuilder().append("SELECT up.id, up.polling_details_id ")
				.append("FROM user_polling up ").append("WHERE up.polling_details_id = :pollingDetailId ");

		try {
			Object result = createNativeQuery(sqlBuilder.toString()).setParameter("pollingDetailId", id)
					.getSingleResult();

			if (result != null) {
				userPolling = new UserPolling();
				Object[] objArr = (Object[]) result;
				PollingDetails pollingDetail = new PollingDetails();

				userPolling.setId(objArr[0].toString());

				pollingDetail.setId(objArr[1].toString());
				userPolling.setPollingDetails(pollingDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userPolling;
	}

	public Boolean deleteByPollingDetail(List<PollingDetails> polDetail) throws Exception {

		StringBuilder sqlBuilder = new StringBuilder().append("DELETE FROM user_polling ")
				.append("WHERE polling_details_id IN (:pollingDetailId) ");

		Integer result = createNativeQuery(sqlBuilder.toString()).setParameter("pollingDetailId", polDetail)
				.executeUpdate();

		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public Integer getAllByPollingDetail(String id) {
		Integer response = 0;
		
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(up.polling_details_id) as count_polling_detail ")
				.append("FROM user_polling up ")
				.append("WHERE up.polling_details_id = :pollingDetailId ")
				.append("GROUP BY up.polling_details_id ");

		try {
			Object result = createNativeQuery(sqlBuilder.toString())
					.setParameter("pollingDetailId", id)
					.getSingleResult();

			if (result != null) {
				response = Integer.valueOf(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
