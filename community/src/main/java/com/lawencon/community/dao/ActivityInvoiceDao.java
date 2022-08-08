package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.ThreadCategoryType;
import com.lawencon.community.model.ActivityInvoice;
import com.lawencon.community.model.File;
import com.lawencon.community.pojo.activityInvoice.PojoActivityInvoice;
import com.lawencon.community.pojo.activityInvoice.ShowActivityInvoice;

@Repository
public class ActivityInvoiceDao extends AbstractJpaDao<ActivityInvoice> {

	public Long countAllInvoicePending() {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(id) pending_count ")
				.append("FROM activity_invoice")
				.append("WHERE is_approved = false");

		Long response = 0l;

		Object result = createNativeQuery(sqlBuilder.toString())
				.getSingleResult();

		if (result != null) {
			response = Long.valueOf(result.toString());
		}

		return response;
	}

	public Long countAllInvoicePendingByActivityType(String code) {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(ai.id) pending_count ")
				.append("FROM activity_invoice as ai ")
				.append("INNER JOIN activity as a ON ai.activity_id = a.id ")
				.append("INNER JOIN activity_type as at ON a.activity_type_id = at.id ")
				.append("WHERE ai.is_approved IS NULL ")
				.append("AND at.type_name = :code");

		Long response = 0l;

		Object result = createNativeQuery(sqlBuilder.toString())
				.setParameter("code", code)
				.getSingleResult();

		if (result != null) {
			response = Long.valueOf(result.toString());
		}

		return response;
	}

	public ShowActivityInvoice getAllByType(Integer startPage, Integer maxPage, String type) {
		List<PojoActivityInvoice> res = new ArrayList<>();
		ShowActivityInvoice response = new ShowActivityInvoice();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT ai.id, ai.invoice_code, ai.created_at, ai.file_id, ai.is_approved, u.full_name, a.fee ")
				.append("FROM activity_invoice as ai ")
				.append("INNER JOIN users as u ON u.id = ai.user_id ")
				.append("INNER JOIN activity as a ON a.id = ai.activity_id ")
				.append("INNER JOIN activity_type as at ON at.id = a.activity_type_id ")
				.append("WHERE a.activity_type_id = :type AND ai.is_approved IS NOT NULL ");

		try {
			Integer size = createNativeQuery(sqlBuilder.toString())
					.setParameter("type", type)
					.getResultList().size();
			response.setCountData(size);

			List<?> result = createNativeQuery(sqlBuilder.toString())
					.setParameter("type", type)
					.setFirstResult(startPage)
					.setMaxResults(maxPage)
					.getResultList();

			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoActivityInvoice data = new PojoActivityInvoice();

					data.setId(objArr[0].toString());
					data.setInvoiceCode(objArr[1].toString());
					data.setOrderDate(((Timestamp) objArr[2]).toLocalDateTime());
					if (objArr[3].toString() != null) {
						data.setFile(objArr[3].toString());
					}
					data.setIsApproved(Boolean.valueOf(objArr[4].toString()));
					data.setUserName(objArr[5].toString());
					data.setAmount(Integer.valueOf(objArr[6].toString()));

					res.add(data);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setData(res);
		return response;
	}

	public List<ActivityInvoice> getByActivity(String id) {
		List<ActivityInvoice> response = new ArrayList<ActivityInvoice>();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT id ")
				.append("FROM activity_invoice ")
				.append("WHERE activity_id = :id");

		try {
			List<?> result = createNativeQuery(sqlBuilder.toString())
					.setParameter("id", id)
					.getResultList();

			if (result != null) {
				result.forEach(obj -> {
					String activityInvoiceId = (String) obj;
					ActivityInvoice data = new ActivityInvoice();

					data.setId(activityInvoiceId);
					response.add(data);
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;

	}

	public boolean deleteByActivityId(String id) {
		StringBuilder sqlBuilder = new StringBuilder()
				.append("DELETE FROM activity_invoice ")
				.append("WHERE activity_id = :activityId");

		int result = createNativeQuery(sqlBuilder.toString())
				.setParameter("activityId", id)
				.executeUpdate();

		return result > 0;
	}

	public ShowActivityInvoice getAllByUserId(Integer startPage, Integer maxPage, String userId) {
		List<PojoActivityInvoice> res = new ArrayList<>();
		ShowActivityInvoice response = new ShowActivityInvoice();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT ai.id, ai.created_at, ai.invoice_code , ai.is_approved, aty.type_name, a.fee, a.file_id ")
				.append("FROM activity_invoice ai ")
				.append("INNER JOIN users u ON u.id = ai.user_id ")
				.append("INNER JOIN activity a ON a.id = ai.activity_id ")
				.append("INNER JOIN activity_type aty ON aty.id = a.activity_type_id ")
				.append("WHERE u.id = :userId ");

		try {
			Integer size = createNativeQuery(sqlBuilder.toString())
					.setParameter("userId", userId)
					.getResultList().size();
			response.setCountData(size);

			List<?> result = createNativeQuery(sqlBuilder.toString())
					.setParameter("userId", userId)
					.setFirstResult(startPage)
					.setMaxResults(maxPage)
					.getResultList();

			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoActivityInvoice data = new PojoActivityInvoice();

					data.setId(objArr[0].toString());
					data.setOrderDate(((Timestamp) objArr[1]).toLocalDateTime());
					data.setInvoiceCode(objArr[2].toString());
					data.setIsApproved(Boolean.valueOf(objArr[3].toString()));
					data.setActivityType(objArr[4].toString());
					data.setAmount(Integer.valueOf(objArr[5].toString()));
					if (objArr[6].toString() != null) {
						data.setFile(objArr[6].toString());
					}

					res.add(data);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setData(res);
		return response;
	}

}
