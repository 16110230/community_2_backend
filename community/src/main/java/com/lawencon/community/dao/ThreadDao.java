package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lawencon.base.AbstractJpaDao;
import com.lawencon.community.constant.ThreadActivityType;
import com.lawencon.community.constant.ThreadCategoryType;
import com.lawencon.community.model.File;
import com.lawencon.community.model.Polling;
import com.lawencon.community.model.PollingDetails;
import com.lawencon.community.model.Thread;
import com.lawencon.community.model.ThreadCategory;
import com.lawencon.community.model.Users;
import com.lawencon.community.pojo.polling.PojoPolling;
import com.lawencon.community.pojo.polling.PojoPollingDetails;
import com.lawencon.community.pojo.polling.ShowPollingMain;
import com.lawencon.community.pojo.thread.PojoThread;
import com.lawencon.community.pojo.thread.ShowThreads;
import com.lawencon.model.SearchQuery;

@Repository
public class ThreadDao extends AbstractJpaDao<Thread> {
	
	@Autowired
	private ThreadCategoryDao threadCatDao;
	
	@Autowired
	private PollingDao pollingDao;
	
	@Autowired
	private PollingDetailsDao detailsDao;

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
				.append("JOIN users u ON u.id = t.user_id ").append("WHERE tc.category_name = :category ")
				.append("ORDER BY t.created_at DESC ").append("LIMIT 3 ");

		try {
			List<?> result = createNativeQuery(sqlBuilder.toString())
					.setParameter("category", ThreadCategoryType.ART.getCode()).getResultList();

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
					
					if (objArr[5] != null) {
						file.setId(objArr[5].toString());						
						data.setFile(file);
					}
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

	public List<PojoThread> getThreadForUser(String userId) {
		List<PojoThread> response = new ArrayList<PojoThread>();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT t.id as threadId , t.thread_title, t.thread_content, ")
				.append("tc.category_name ,t.file_id, u.username, t.created_at, ")
				.append("( SELECT ta.id from thread_activity ta JOIN thread_activity_category tac ON tac.id = ta.thread_activity_category_id ")
				.append("where user_id  = :userid AND thread_id = t.id  AND tac.thread_activity_code = :like) AS isLike, ")
				.append("( SELECT ta.id from thread_activity ta JOIN thread_activity_category tac ON tac.id = ta.thread_activity_category_id ")
				.append("where user_id  = :userid2 AND thread_id = t.id  AND tac.thread_activity_code = :bookmark) AS isBookmark, ")
				.append("( SELECT COUNT(ta2.id) AS countLike FROM thread_activity ta2 JOIN thread_activity_category tac2 ON tac2.id = ta2.thread_activity_category_id  ")
				.append("WHERE tac2.thread_activity_code  = :like2 AND ta2.thread_id = t.id ) AS countLike, ")
				.append("( SELECT COUNT(ta3.id) AS countBook FROM thread_activity ta3 JOIN thread_activity_category tac2 ON tac2.id = ta3.thread_activity_category_id ")
				.append("WHERE tac2.thread_activity_code  = :bookmark2 AND ta3.thread_id = t.id ) AS countBook, ")
				.append("( SELECT COUNT(td.id) AS countComment FROM thread_details td WHERE td.thread_id = t.id)AS countComment ")
				.append("FROM thread t ").append("JOIN thread_category tc ON tc.id = t.thread_category_id ")
				.append("JOIN users u ON u.id = t.user_id ").append("WHERE tc.category_code != :category ");

		try {
			List<?> result = createNativeQuery(sqlBuilder.toString()).setParameter("userid", userId)
					.setParameter("like", ThreadActivityType.LIKE.name()).setParameter("userid2", userId)
					.setParameter("bookmark", ThreadActivityType.BOOKMARK.name())
					.setParameter("like2", ThreadActivityType.LIKE.name())
					.setParameter("bookmark2", ThreadActivityType.BOOKMARK.name())
					.setParameter("category", ThreadCategoryType.ART.name()).getResultList();

			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoThread data = new PojoThread();

					data.setId(objArr[0].toString());
					data.setThreadTitle(objArr[1].toString());
					data.setThreadContent(objArr[2].toString());
					data.setThreadCategoryName(objArr[3].toString());
					data.setFile(objArr[4].toString());
					data.setUserName(objArr[5].toString());
					data.setCreatedAt(((Timestamp) objArr[6]).toLocalDateTime());
					if (objArr[7] != null) {
						data.setIsLike(true);
					} else {
						data.setIsLike(false);
					}
					if (objArr[8] != null) {
						data.setIsBookmark(true);
					} else {
						data.setIsBookmark(false);
					}
					data.setCountLike(Integer.valueOf(objArr[9].toString()));
					data.setCountBookmark(Integer.valueOf(objArr[10].toString()));
					data.setCountComment(Integer.valueOf(objArr[11].toString()));
					response.add(data);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public Boolean isLike(String userId, String threadId) {
		Boolean returnValue = false;
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT ta.id from thread_activity ta ")
				.append("JOIN thread_activity_category tac ON tac.id = ta.thread_activity_category_id ")
				.append("where user_id  = :userId AND thread_id = :threadId  AND tac.thread_activity_code = :like ");
		
		try {
			Object result = createNativeQuery(sqlBuilder.toString())
					.setParameter("userId", userId)
					.setParameter("threadId", threadId)
					.setParameter("like", ThreadActivityType.LIKE.name())
					.getSingleResult();
			if (result != null) {
				returnValue = true;
			}else {
				returnValue = false;
			}			
		} catch (Exception e) {
			returnValue = false;
		}
		return returnValue;
	}
	
	public Boolean isBookmark(String userId, String threadId) {
		Boolean returnValue = false;
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT ta.id from thread_activity ta ")
				.append("JOIN thread_activity_category tac ON tac.id = ta.thread_activity_category_id ")
				.append("where user_id  = :userId AND thread_id = :threadId  AND tac.thread_activity_code = :bookmark ");
		
		try {
			Object result = createNativeQuery(sqlBuilder.toString())
					.setParameter("userId", userId)
					.setParameter("threadId", threadId)
					.setParameter("bookmark", ThreadActivityType.BOOKMARK.name())
					.getSingleResult();
			
			if (result != null) {
				returnValue = true;
			}else {
				returnValue = false;
			}
		} catch (Exception e) {
			returnValue = false;
		}
		return returnValue;
	}
	
	public Integer countLike(String threadId) {
		Integer response = 0;
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(ta.id) AS countLike FROM thread_activity ta ")
				.append("JOIN thread_activity_category tac ON tac.id = ta.thread_activity_category_id ")
				.append("WHERE tac.thread_activity_code  = :like AND ta.thread_id = :threadId ");
		
		Object result = createNativeQuery(sqlBuilder.toString())
						.setParameter("like", ThreadActivityType.LIKE.name())
						.setParameter("threadId", threadId)
						.getSingleResult();

		if (result != null) {
			response = Integer.valueOf(result.toString());
		}
		
		return response;
	}
	
	public Integer countBookmark(String threadId) {
		Integer response = 0;
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(ta.id) AS countLike FROM thread_activity ta ")
				.append("JOIN thread_activity_category tac ON tac.id = ta.thread_activity_category_id ")
				.append("WHERE tac.thread_activity_code  = :like AND ta.thread_id = :threadId ");
		
		Object result = createNativeQuery(sqlBuilder.toString())
						.setParameter("like", ThreadActivityType.BOOKMARK.name())
						.setParameter("threadId", threadId)
						.getSingleResult();

		if (result != null) {
			response = Integer.valueOf(result.toString());
		}
		
		return response;
	}
	
	public Integer countComment(String threadId) {
		Integer response = 0;
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT COUNT(td.id) AS countComment FROM thread_details td WHERE td.thread_id = :threadId ");
		
		Object result = createNativeQuery(sqlBuilder.toString())
						.setParameter("threadId", threadId)
						.getSingleResult();

		if (result != null) {
			response = Integer.valueOf(result.toString());
		}
		
		return response;
	}
	
	public List<PojoThread> getNewestThread() {
		List<PojoThread> response = new ArrayList<PojoThread>();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT t.id,t.thread_title,tc.category_name ")
				.append("FROM thread as t ")
				.append("INNER JOIN thread_category as tc ON t.thread_category_id = tc.id ")
				.append("ORDER BY t.created_at DESC ")
				.append("LIMIT 5");

		try {
			List<?> result = createNativeQuery(sqlBuilder.toString())
					.getResultList();

			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoThread data = new PojoThread();

					data.setId(objArr[0].toString());
					data.setThreadTitle(objArr[1].toString());
					data.setThreadCategoryName(objArr[2].toString());				

					response.add(data);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public ShowThreads getThreadArticle(Integer startPage,Integer maxPage) {
		List<PojoThread> res = new ArrayList<PojoThread>();
		ShowThreads response = new ShowThreads();
		StringBuilder sqlBuilder = new StringBuilder()
				.append("SELECT t.id, t.thread_title, t.thread_content, t.thread_category_id, ")
				.append("tc.category_name , t.file_id, t.user_id, u.username, t.created_at  ")
				.append("FROM thread t ")
				.append("JOIN thread_category tc ON tc.id = t.thread_category_id ")
				.append("JOIN users u ON u.id = t.user_id ")
				.append("WHERE tc.category_name = :category ")
				.append("ORDER BY t.created_at DESC ");

		try {
			Integer size = createNativeQuery(sqlBuilder.toString())
					.setParameter("category", ThreadCategoryType.ART.getCode())
					.getResultList().size();
			response.setCountData(size);
			
			List<?> result = createNativeQuery(sqlBuilder.toString())
					.setParameter("category", ThreadCategoryType.ART.getCode())
					.setFirstResult(startPage)
					.setMaxResults(maxPage)
					.getResultList();
			
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoThread data = new PojoThread();
					ThreadCategory threadCat = new ThreadCategory();
					Users user = new Users();
					File file = new File();

					data.setId(objArr[0].toString());
					data.setThreadTitle(objArr[1].toString());
					data.setThreadContent(objArr[2].toString());
					data.setThreadcategory(objArr[3].toString());
					data.setThreadCategoryName(objArr[4].toString());
					if(objArr[5] != null) {
						data.setFile(objArr[5].toString());						
					}
					data.setUser(objArr[6].toString());
					data.setUserName(objArr[7].toString());
					data.setCreatedAt(((Timestamp) objArr[8]).toLocalDateTime());
					
					res.add(data);
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setData(res);

		return response;
	}
	
	public SearchQuery<PojoThread> findAllById(String user, String threadCatCode, Integer startPage, Integer maxPage) throws Exception {
		SearchQuery<PojoThread> response = new SearchQuery<PojoThread>();
		List<PojoThread> listPojo = new ArrayList<PojoThread>();
		
		StringBuilder sql = new StringBuilder()
			.append("SELECT t.id, t.thread_title, t.thread_content, t.file_id, t.thread_category_id, ")
			.append("t.created_at, t.is_active, t.version, t.user_id ")
			.append("FROM thread t ")
			.append("INNER JOIN users u ON u.id = t.user_id ")
			.append("INNER JOIN file f ON f.id = t.file_id ")
			.append("INNER JOIN thread_category tg ON tg.id = t.thread_category_id ")
			.append("WHERE u.id = :id ")
			.append("AND tg.category_code != :code ")
			.append("ORDER BY t.created_at DESC");
		
		try {			
			List<?> result = createNativeQuery(sql.toString())
					.setParameter("id", user)
					.setParameter("code", threadCatCode)
					.setFirstResult(startPage)
					.setMaxResults(maxPage)
					.getResultList();
			
			if(result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoThread data = new PojoThread();
					ThreadCategory threadCategory = threadCatDao.getById(objArr[4].toString());
					
					if(threadCategory.getCategoryCode().equals(ThreadCategoryType.POL.name())) {
						
						Polling checkPoll = pollingDao.getByThreadId(objArr[0].toString());
						if(checkPoll != null) {							
							try {
								ShowPollingMain polling = new ShowPollingMain();
								List<PojoPollingDetails> pojoDetails = new ArrayList<PojoPollingDetails>();
								PojoPolling pojoPoll = new PojoPolling();
								
								pojoPoll.setId(checkPoll.getId());
								pojoPoll.setIsPolling(true);
								polling.setHeader(pojoPoll);
								
								List<PollingDetails> pollDetails = detailsDao.findAllByPolling(checkPoll.getId());
								pollDetails.forEach(value -> {
									PojoPollingDetails detail = new PojoPollingDetails();
									detail.setId(value.getId());
									detail.setPolling(value.getPolling().getId());
									detail.setPollingDetailsName(value.getPollingDetailsName());
									
									pojoDetails.add(detail);
								});
								
								polling.setDetails(pojoDetails);
								data.setPolling(polling);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					
					data.setId(objArr[0].toString());
					data.setThreadTitle(objArr[1].toString());
					data.setThreadContent(objArr[2].toString());
					if(objArr[3] != null) {
						data.setFile(objArr[3].toString());
					}
					data.setThreadcategory(objArr[4].toString());
					data.setCreatedAt(((Timestamp) objArr[5]).toLocalDateTime());
					data.setIsActive(Boolean.valueOf(objArr[6].toString()));
					data.setVersion(Integer.valueOf(objArr[7].toString()));
					data.setUser(objArr[8].toString());
					
					data.setCountComment(countComment(objArr[0].toString()));
					data.setIsBookmark(isBookmark(objArr[8].toString(), objArr[0].toString()));
					data.setIsLike(isLike(objArr[8].toString(), objArr[0].toString()));
					data.setCountLike(countLike(objArr[0].toString()));
					data.setThreadCategoryName(threadCatCode);
					
					listPojo.add(data);
				});
				
				response.setData(listPojo);
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		return response;
	}
	
	public ShowThreads getByUserAndBookmark(String userId, Integer startPage, Integer maxPage){
		List<PojoThread> res = new ArrayList<PojoThread>();
		ShowThreads response = new ShowThreads();
		StringBuilder sqlBuilder = new StringBuilder()
			.append("SELECT ta.id, ta.thread_id, ta.is_Active ")
			.append("FROM thread_activity as ta ")
			.append("INNER JOIN thread_activity_category as tac ON ta.thread_activity_category_id = tac.id ")
			.append("WHERE tac.thread_activity_code = :threadActivityTypeCode ")
			.append("AND ta.user_id = :userId ");
		
		try {
			Integer size = createNativeQuery(sqlBuilder.toString())
				.setParameter("userId", userId)
				.setParameter("threadActivityTypeCode", ThreadActivityType.BOOKMARK.getCode())
				.getResultList().size();
			response.setCountData(size);
			
			List<?> result = createNativeQuery(sqlBuilder.toString())
				.setParameter("userId", userId)
				.setParameter("threadActivityTypeCode", ThreadActivityType.BOOKMARK.getCode())
				.setFirstResult(startPage)
				.setMaxResults(maxPage)
				.getResultList();
			
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoThread data = new PojoThread();
					
					data.setId(objArr[1].toString());
					
					res.add(data);
				});
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		response.setData(res);
		return response;
			
	}
	
	public ShowThreads getByUserAndLike(String userId, Integer startPage, Integer maxPage){
		List<PojoThread> res = new ArrayList<PojoThread>();
		ShowThreads response = new ShowThreads();
		StringBuilder sqlBuilder = new StringBuilder()
			.append("SELECT ta.id, ta.thread_id, ta.is_Active ")
			.append("FROM thread_activity as ta ")
			.append("INNER JOIN thread_activity_category as tac ON ta.thread_activity_category_id = tac.id ")
			.append("WHERE tac.thread_activity_code = :threadActivityTypeCode ")
			.append("AND ta.user_id = :userId ");
		
		try {
			Integer size = createNativeQuery(sqlBuilder.toString())
				.setParameter("userId", userId)
				.setParameter("threadActivityTypeCode", ThreadActivityType.LIKE.getCode())
				.getResultList().size();
			response.setCountData(size);
			
			List<?> result = createNativeQuery(sqlBuilder.toString())
				.setParameter("userId", userId)
				.setParameter("threadActivityTypeCode", ThreadActivityType.LIKE.getCode())
				.setFirstResult(startPage)
				.setMaxResults(maxPage)
				.getResultList();
			
			if (result != null) {
				result.forEach(obj -> {
					Object[] objArr = (Object[]) obj;
					PojoThread data = new PojoThread();
					
					data.setId(objArr[1].toString());
					
					res.add(data);
				});
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setData(res);
		return response;
			
	}

}
